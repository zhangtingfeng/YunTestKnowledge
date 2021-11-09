package cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import cn.tedu.sp0ag4studio.core.orm.xibatis.common.beans.Probe;
import cn.tedu.sp0ag4studio.core.orm.xibatis.common.beans.ProbeFactory;
import cn.tedu.sp0ag4studio.core.orm.xibatis.common.jdbc.exception.NestedSQLException;
import cn.tedu.sp0ag4studio.core.orm.xibatis.common.util.PaginatedList;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.client.SqlMapException;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.client.event.RowHandler;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.cache.CacheKey;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.cache.CacheModel;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.exchange.DataExchangeFactory;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.execution.BatchException;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.execution.SqlExecutor;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.mapping.result.ResultMap;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.mapping.result.ResultObjectFactory;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.mapping.statement.InsertStatement;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.mapping.statement.MappedStatement;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.mapping.statement.PaginatedDataList;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.mapping.statement.SelectKeyStatement;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.scope.SessionScope;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.scope.StatementScope;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction.Transaction;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction.TransactionException;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction.TransactionManager;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction.TransactionState;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction.user.UserProvidedTransaction;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.type.TypeHandlerFactory;

/**
 * The workhorse that really runs the SQL
 */
public class SqlMapExecutorDelegate {

    private static final Probe PROBE = ProbeFactory.getProbe();

    private boolean lazyLoadingEnabled;
    private boolean cacheModelsEnabled;
    private boolean enhancementEnabled;
    private boolean useColumnLabel = true;
    private boolean forceMultipleResultSetSupport;

    private TransactionManager txManager;

    private HashMap mappedStatements;
    private HashMap cacheModels;
    private HashMap resultMaps;
    private HashMap parameterMaps;

    protected SqlExecutor sqlExecutor;
    private TypeHandlerFactory typeHandlerFactory;
    private DataExchangeFactory dataExchangeFactory;

    private ResultObjectFactory resultObjectFactory;
    private boolean statementCacheEnabled;

    /**
     * Default constructor
     */
    public SqlMapExecutorDelegate() {
        mappedStatements = new HashMap();
        cacheModels = new HashMap();
        resultMaps = new HashMap();
        parameterMaps = new HashMap();

        sqlExecutor = new SqlExecutor();
        typeHandlerFactory = new TypeHandlerFactory();
        dataExchangeFactory = new DataExchangeFactory(typeHandlerFactory);
    }

    /**
     * DO NOT DEPEND ON THIS. Here to avoid breaking spring integration.
     *
     * @deprecated
     */
    public int getMaxTransactions() {
        return -1;
    }

    /**
     * Getter for the DataExchangeFactory
     *
     * @return - the DataExchangeFactory
     */
    public DataExchangeFactory getDataExchangeFactory() {
        return dataExchangeFactory;
    }

    /**
     * Getter for the TypeHandlerFactory
     *
     * @return - the TypeHandlerFactory
     */
    public TypeHandlerFactory getTypeHandlerFactory() {
        return typeHandlerFactory;
    }

    /**
     * Getter for the status of lazy loading
     *
     * @return - the status
     */
    public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    /**
     * Turn on or off lazy loading
     *
     * @param lazyLoadingEnabled - the new state of caching
     */
    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }

    /**
     * Getter for the status of caching
     *
     * @return - the status
     */
    public boolean isCacheModelsEnabled() {
        return cacheModelsEnabled;
    }

    /**
     * Turn on or off caching
     *
     * @param cacheModelsEnabled - the new state of caching
     */
    public void setCacheModelsEnabled(boolean cacheModelsEnabled) {
        this.cacheModelsEnabled = cacheModelsEnabled;
    }

    /**
     * Getter for the status of CGLib enhancements
     *
     * @return - the status
     */
    public boolean isEnhancementEnabled() {
        return enhancementEnabled;
    }

    /**
     * Turn on or off CGLib enhancements
     *
     * @param enhancementEnabled - the new state
     */
    public void setEnhancementEnabled(boolean enhancementEnabled) {
        this.enhancementEnabled = enhancementEnabled;
    }

    public boolean isUseColumnLabel() {
        return useColumnLabel;
    }

    public void setUseColumnLabel(boolean useColumnLabel) {
        this.useColumnLabel = useColumnLabel;
    }

    /**
     * Getter for the transaction manager
     *
     * @return - the transaction manager
     */
    public TransactionManager getTxManager() {
        return txManager;
    }

    /**
     * Setter for the transaction manager
     *
     * @param txManager - the transaction manager
     */
    public void setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
    }

    /**
     * Add a mapped statement
     *
     * @param ms - the mapped statement to add
     */
    public void addMappedStatement(MappedStatement ms) {
        if (mappedStatements.containsKey(ms.getId())) {
            throw new SqlMapException("There is already a statement named " + ms.getId() + " in this SqlMap.");
        }
        ms.setBaseCacheKey(hashCode());
        mappedStatements.put(ms.getId(), ms);
    }

    /**
     * Get an iterator of the mapped statements
     *
     * @return - the iterator
     */
    public Iterator getMappedStatementNames() {
        return mappedStatements.keySet().iterator();
    }

    /**
     * Get a mappedstatement by its ID
     *
     * @param id - the statement ID
     * @return - the mapped statement
     */
    public MappedStatement getMappedStatement(String id) {
        MappedStatement ms = (MappedStatement) mappedStatements.get(id);
        if (ms == null) {
            throw new SqlMapException("There is no statement named " + id + " in this SqlMap.");
        }
        return ms;
    }

    /**
     * Add a cache model
     *
     * @param model - the model to add
     */
    public void addCacheModel(CacheModel model) {
        cacheModels.put(model.getId(), model);
    }

    /**
     * Get an iterator of the cache models
     *
     * @return - the cache models
     */
    public Iterator getCacheModelNames() {
        return cacheModels.keySet().iterator();
    }

    /**
     * Get a cache model by ID
     *
     * @param id - the ID
     * @return - the cache model
     */
    public CacheModel getCacheModel(String id) {
        CacheModel model = (CacheModel) cacheModels.get(id);
        if (model == null) {
            throw new SqlMapException("There is no cache model named " + id + " in this SqlMap.");
        }
        return model;
    }

    /**
     * Add a result map
     *
     * @param map - the result map to add
     */
    public void addResultMap(ResultMap map) {
        resultMaps.put(map.getId(), map);
    }

    /**
     * Get an iterator of the result maps
     *
     * @return - the result maps
     */
    public Iterator getResultMapNames() {
        return resultMaps.keySet().iterator();
    }

    /**
     * Get a result map by ID
     *
     * @param id - the ID
     * @return - the result map
     */
    public ResultMap getResultMap(String id) {
        ResultMap map = (ResultMap) resultMaps.get(id);
        if (map == null) {
            throw new SqlMapException("There is no result map named " + id + " in this SqlMap.");
        }
        return map;
    }

    /**
     * Add a parameter map
     *
     * @param map - the map to add
     */
    public void addParameterMap(ParameterMap map) {
        parameterMaps.put(map.getId(), map);
    }

    /**
     * Get an iterator of all of the parameter maps
     *
     * @return - the parameter maps
     */
    public Iterator getParameterMapNames() {
        return parameterMaps.keySet().iterator();
    }

    /**
     * Get a parameter map by ID
     *
     * @param id - the ID
     * @return - the parameter map
     */
    public ParameterMap getParameterMap(String id) {
        ParameterMap map = (ParameterMap) parameterMaps.get(id);
        if (map == null) {
            throw new SqlMapException("There is no parameter map named " + id + " in this SqlMap.");
        }
        return map;
    }

    /**
     * Flush all of the data caches
     */
    public void flushDataCache() {
        Iterator models = cacheModels.values().iterator();
        while (models.hasNext()) {
            ((CacheModel) models.next()).flush();
        }
    }

    /**
     * Flush a single cache by ID
     *
     * @param id - the ID
     */
    public void flushDataCache(String id) {
        CacheModel model = getCacheModel(id);
        if (model != null) {
            model.flush();
        }
    }

    // -- Basic Methods

    /**
     * Call an insert statement by ID
     *
     * @param sessionScope - the session
     * @param id           - the statement ID
     * @param param        - the parameter object
     * @return - the generated key (or null)
     * @throws SQLException - if the insert fails
     */
    public Object insert(SessionScope sessionScope, String id, Object param) throws SQLException {
        Object generatedKey = null;

        MappedStatement ms = getMappedStatement(id);
        Transaction trans = getTransaction(sessionScope);
        boolean autoStart = trans == null;

        try {
            trans = autoStartTransaction(sessionScope, autoStart, trans);

            SelectKeyStatement selectKeyStatement = null;
            if (ms instanceof InsertStatement) {
                selectKeyStatement = ((InsertStatement) ms).getSelectKeyStatement();
            }

            // Here we get the old value for the key property. We'll want it
            // later if for some reason the
            // insert fails.
            Object oldKeyValue = null;
            String keyProperty = null;
            boolean resetKeyValueOnFailure = false;
            if (selectKeyStatement != null && !selectKeyStatement.isRunAfterSQL()) {
                keyProperty = selectKeyStatement.getKeyProperty();
                oldKeyValue = PROBE.getObject(param, keyProperty);
                generatedKey = executeSelectKey(sessionScope, trans, ms, param);
                resetKeyValueOnFailure = true;
            }

            StatementScope statementScope = beginStatementScope(sessionScope, ms);
            try {
                ms.executeUpdate(statementScope, trans, param);
            } catch (SQLException e) {
                // uh-oh, the insert failed, so if we set the reset flag
                // earlier, we'll put the old value
                // back...
                if (resetKeyValueOnFailure)
                    PROBE.setObject(param, keyProperty, oldKeyValue);
                // ...and still throw the exception.
                throw e;
            } finally {
                endStatementScope(statementScope);
            }

            if (selectKeyStatement != null && selectKeyStatement.isRunAfterSQL()) {
                generatedKey = executeSelectKey(sessionScope, trans, ms, param);
            }

            autoCommitTransaction(sessionScope, autoStart);
        } finally {
            autoEndTransaction(sessionScope, autoStart);
        }

        return generatedKey;
    }

    private Object executeSelectKey(SessionScope sessionScope, Transaction trans, MappedStatement ms, Object param)
            throws SQLException {
        Object generatedKey = null;
        StatementScope statementScope;
        InsertStatement insert = (InsertStatement) ms;
        SelectKeyStatement selectKeyStatement = insert.getSelectKeyStatement();
        if (selectKeyStatement != null) {
            statementScope = beginStatementScope(sessionScope, selectKeyStatement);
            try {
                generatedKey = selectKeyStatement.executeQueryForObject(statementScope, trans, param, null);
                String keyProp = selectKeyStatement.getKeyProperty();
                if (keyProp != null) {
                    PROBE.setObject(param, keyProp, generatedKey);
                }
            } finally {
                endStatementScope(statementScope);
            }
        }
        return generatedKey;
    }

    /**
     * Execute an update statement
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param param        - the parameter object
     * @return - the number of rows updated
     * @throws SQLException - if the update fails
     */
    public int update(SessionScope sessionScope, String id, Object param) throws SQLException {
        int rows = 0;

        MappedStatement ms = getMappedStatement(id);
        Transaction trans = getTransaction(sessionScope);
        boolean autoStart = trans == null;

        try {
            trans = autoStartTransaction(sessionScope, autoStart, trans);

            StatementScope statementScope = beginStatementScope(sessionScope, ms);
            try {
                rows = ms.executeUpdate(statementScope, trans, param);
            } finally {
                endStatementScope(statementScope);
            }

            autoCommitTransaction(sessionScope, autoStart);
        } finally {
            autoEndTransaction(sessionScope, autoStart);
        }

        return rows;
    }

    /**
     * Execute a delete statement
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param param        - the parameter object
     * @return - the number of rows deleted
     * @throws SQLException - if the delete fails
     */
    public int delete(SessionScope sessionScope, String id, Object param) throws SQLException {
        return update(sessionScope, id, param);
    }

    /**
     * Execute a select for a single object
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param paramObject  - the parameter object
     * @return - the result of the query
     * @throws SQLException - if the query fails
     */
    public Object queryForObject(SessionScope sessionScope, String id, Object paramObject) throws SQLException {
        return queryForObject(sessionScope, id, paramObject, null);
    }

    /**
     * Execute a select for a single object
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param paramObject  - the parameter object
     * @param resultObject - the result object (if not supplied or null, a new object
     *                     will be created)
     * @return - the result of the query
     * @throws SQLException - if the query fails
     */
    public Object queryForObject(SessionScope sessionScope, String id, Object paramObject, Object resultObject)
            throws SQLException {
        Object object = null;

        MappedStatement ms = getMappedStatement(id);
        Transaction trans = getTransaction(sessionScope);
        boolean autoStart = trans == null;

        try {
            trans = autoStartTransaction(sessionScope, autoStart, trans);

            StatementScope statementScope = beginStatementScope(sessionScope, ms);
            try {
                object = ms.executeQueryForObject(statementScope, trans, paramObject, resultObject);
            } finally {
                endStatementScope(statementScope);
            }

            autoCommitTransaction(sessionScope, autoStart);
        } finally {
            autoEndTransaction(sessionScope, autoStart);
        }

        return object;
    }

    /**
     * Execute a query for a list
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param paramObject  - the parameter object
     * @return - the data list
     * @throws SQLException - if the query fails
     */
    public List queryForList(SessionScope sessionScope, String id, Object paramObject) throws SQLException {
        return queryForList(sessionScope, id, paramObject, SqlExecutor.NO_SKIPPED_RESULTS,
                SqlExecutor.NO_MAXIMUM_RESULTS);
    }

    /**
     * Execute a query for a list
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param paramObject  - the parameter object
     * @param skip         - the number of rows to skip
     * @param max          - the maximum number of rows to return
     * @return - the data list
     * @throws SQLException - if the query fails
     */
    public List queryForList(SessionScope sessionScope, String id, Object paramObject, int skip, int max)
            throws SQLException {
        List list = null;

        MappedStatement ms = getMappedStatement(id);
        Transaction trans = getTransaction(sessionScope);
        boolean autoStart = trans == null;

        try {
            trans = autoStartTransaction(sessionScope, autoStart, trans);

            StatementScope statementScope = beginStatementScope(sessionScope, ms);
            try {
                list = ms.executeQueryForList(statementScope, trans, paramObject, skip, max);
            } finally {
                endStatementScope(statementScope);
            }

            autoCommitTransaction(sessionScope, autoStart);
        } finally {
            autoEndTransaction(sessionScope, autoStart);
        }

        return list;
    }

    /**
     * Execute a query with a row handler. The row handler is called once per
     * row in the query results.
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param paramObject  - the parameter object
     * @param rowHandler   - the row handler
     * @throws SQLException - if the query fails
     */
    public void queryWithRowHandler(SessionScope sessionScope, String id, Object paramObject, RowHandler rowHandler)
            throws SQLException {

        MappedStatement ms = getMappedStatement(id);
        Transaction trans = getTransaction(sessionScope);
        boolean autoStart = trans == null;

        try {
            trans = autoStartTransaction(sessionScope, autoStart, trans);

            StatementScope statementScope = beginStatementScope(sessionScope, ms);
            try {
                ms.executeQueryWithRowHandler(statementScope, trans, paramObject, rowHandler);
            } finally {
                endStatementScope(statementScope);
            }

            autoCommitTransaction(sessionScope, autoStart);
        } finally {
            autoEndTransaction(sessionScope, autoStart);
        }

    }

    /**
     * Execute a query and return a paginated list
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param paramObject  - the parameter object
     * @param pageSize     - the page size
     * @return - the data list
     * @throws SQLException - if the query fails
     * @deprecated All paginated list features have been deprecated
     */
    public PaginatedList queryForPaginatedList(SessionScope sessionScope, String id, Object paramObject, int pageSize)
            throws SQLException {
        return new PaginatedDataList(sessionScope.getSqlMapExecutor(), id, paramObject, pageSize);
    }

    /**
     * Execute a query for a map. The map has the table key as the key, and the
     * results as the map data
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param paramObject  - the parameter object
     * @param keyProp      - the key property (from the results for the map)
     * @return - the Map
     * @throws SQLException - if the query fails
     */
    public Map queryForMap(SessionScope sessionScope, String id, Object paramObject, String keyProp)
            throws SQLException {
        return queryForMap(sessionScope, id, paramObject, keyProp, null);
    }

    /**
     * Execute a query for a map. The map has the table key as the key, and a
     * property from the results as the map data
     *
     * @param sessionScope - the session scope
     * @param id           - the statement ID
     * @param paramObject  - the parameter object
     * @param keyProp      - the property for the map key
     * @param valueProp    - the property for the map data
     * @return - the Map
     * @throws SQLException - if the query fails
     */
    public Map queryForMap(SessionScope sessionScope, String id, Object paramObject, String keyProp, String valueProp)
            throws SQLException {
        Map map = new HashMap();

        List list = queryForList(sessionScope, id, paramObject);

        for (int i = 0, n = list.size(); i < n; i++) {
            Object object = list.get(i);
            Object key = PROBE.getObject(object, keyProp);
            Object value = null;
            if (valueProp == null) {
                value = object;
            } else {
                value = PROBE.getObject(object, valueProp);
            }
            map.put(key, value);
        }

        return map;
    }

    // -- Transaction Control Methods

    /**
     * Start a transaction on the session
     *
     * @param sessionScope - the session
     * @throws SQLException - if the transaction could not be started
     */
    public void startTransaction(SessionScope sessionScope) throws SQLException {
        try {
            txManager.begin(sessionScope);
        } catch (TransactionException e) {
            throw new NestedSQLException("Could not start transaction.  Cause: " + e, e);
        }
    }

    /**
     * Start a transaction on the session with the specified isolation level.
     *
     * @param sessionScope - the session
     * @throws SQLException - if the transaction could not be started
     */
    public void startTransaction(SessionScope sessionScope, int transactionIsolation) throws SQLException {
        try {
            txManager.begin(sessionScope, transactionIsolation);
        } catch (TransactionException e) {
            throw new NestedSQLException("Could not start transaction.  Cause: " + e, e);
        }
    }

    /**
     * Commit the transaction on a session
     *
     * @param sessionScope - the session
     * @throws SQLException - if the transaction could not be committed
     */
    public void commitTransaction(SessionScope sessionScope) throws SQLException {
        try {
            // Auto batch execution
            if (sessionScope.isInBatch()) {
                executeBatch(sessionScope);
            }
            sqlExecutor.cleanup(sessionScope);
            txManager.commit(sessionScope);
        } catch (TransactionException e) {
            throw new NestedSQLException("Could not commit transaction.  Cause: " + e, e);
        }
    }

    /**
     * End the transaction on a session
     *
     * @param sessionScope - the session
     * @throws SQLException - if the transaction could not be ended
     */
    public void endTransaction(SessionScope sessionScope) throws SQLException {
        try {
            try {
                sqlExecutor.cleanup(sessionScope);
            } finally {
                txManager.end(sessionScope);
            }
        } catch (TransactionException e) {
            throw new NestedSQLException("Error while ending transaction.  Cause: " + e, e);
        }
    }

    /**
     * Start a batch for a session
     *
     * @param sessionScope - the session
     */
    public void startBatch(SessionScope sessionScope) {
        sessionScope.setInBatch(true);
    }

    /**
     * Execute a batch for a session
     *
     * @param sessionScope - the session
     * @return - the number of rows impacted by the batch
     * @throws SQLException - if the batch fails
     */
    public int executeBatch(SessionScope sessionScope) throws SQLException {
        sessionScope.setInBatch(false);
        return sqlExecutor.executeBatch(sessionScope);
    }

    /**
     * Execute a batch for a session
     *
     * @param sessionScope - the session
     * @return - a List of BatchResult objects (may be null if no batch has been
     * initiated). There will be one BatchResult object in the list for
     * each sub-batch executed
     * @throws SQLException   if a database access error occurs, or the drive does not
     *                        support batch statements
     * @throws BatchException if the driver throws BatchUpdateException
     */
    public List executeBatchDetailed(SessionScope sessionScope) throws SQLException, BatchException {
        sessionScope.setInBatch(false);
        return sqlExecutor.executeBatchDetailed(sessionScope);
    }

    /**
     * Use a user-provided transaction for a session
     *
     * @param sessionScope   - the session scope
     * @param userConnection - the user supplied connection
     */
    public void setUserProvidedTransaction(SessionScope sessionScope, Connection userConnection) {
        if (sessionScope.getTransactionState() == TransactionState.STATE_USER_PROVIDED) {
            sessionScope.recallTransactionState();
        }
        if (userConnection != null) {
            Connection conn = userConnection;
            sessionScope.saveTransactionState();
            sessionScope.setTransaction(new UserProvidedTransaction(conn));
            sessionScope.setTransactionState(TransactionState.STATE_USER_PROVIDED);
        } else {
            sessionScope.setTransaction(null);
            sessionScope.closePreparedStatements();
            sessionScope.cleanup();
        }
    }

    /**
     * Get the DataSource for the session
     *
     * @return - the DataSource
     */
    public DataSource getDataSource() {
        DataSource ds = null;
        if (txManager != null) {
            ds = txManager.getConfig().getDataSource();
        }
        return ds;
    }

    /**
     * Getter for the SqlExecutor
     *
     * @return the SqlExecutor
     */
    public SqlExecutor getSqlExecutor() {
        return sqlExecutor;
    }

    /**
     * Get a transaction for the session
     *
     * @param sessionScope - the session
     * @return - the transaction
     */
    public Transaction getTransaction(SessionScope sessionScope) {
        return sessionScope.getTransaction();
    }

    // -- Protected Methods

    protected void autoEndTransaction(SessionScope sessionScope, boolean autoStart) throws SQLException {
        if (autoStart) {
            sessionScope.getSqlMapTxMgr().endTransaction();
        }
    }

    protected void autoCommitTransaction(SessionScope sessionScope, boolean autoStart) throws SQLException {
        if (autoStart) {
            sessionScope.getSqlMapTxMgr().commitTransaction();
        }
    }

    protected Transaction autoStartTransaction(SessionScope sessionScope, boolean autoStart, Transaction trans)
            throws SQLException {
        Transaction transaction = trans;
        if (autoStart) {
            sessionScope.getSqlMapTxMgr().startTransaction();
            transaction = getTransaction(sessionScope);
        }
        return transaction;
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public int hashCode() {
        CacheKey key = new CacheKey();
        if (txManager != null) {
            key.update(txManager);
            if (txManager.getConfig().getDataSource() != null) {
                key.update(txManager.getConfig().getDataSource());
            }
        }
        key.update(System.identityHashCode(this));
        return key.hashCode();
    }

    protected StatementScope beginStatementScope(SessionScope sessionScope, MappedStatement mappedStatement) {
        StatementScope statementScope = new StatementScope(sessionScope);
        sessionScope.incrementRequestStackDepth();
        mappedStatement.initRequest(statementScope);
        return statementScope;
    }

    protected void endStatementScope(StatementScope statementScope) {
        statementScope.getSession().decrementRequestStackDepth();
    }

    protected SessionScope beginSessionScope() {
        return new SessionScope();
    }

    protected void endSessionScope(SessionScope sessionScope) {
        sessionScope.cleanup();
    }

    public ResultObjectFactory getResultObjectFactory() {
        return resultObjectFactory;
    }

    public void setResultObjectFactory(ResultObjectFactory resultObjectFactory) {
        this.resultObjectFactory = resultObjectFactory;
    }

    public boolean isStatementCacheEnabled() {
        return statementCacheEnabled;
    }

    public void setStatementCacheEnabled(boolean statementCacheEnabled) {
        this.statementCacheEnabled = statementCacheEnabled;
    }

    public boolean isForceMultipleResultSetSupport() {
        return forceMultipleResultSetSupport;
    }

    public void setForceMultipleResultSetSupport(boolean forceMultipleResultSetSupport) {
        this.forceMultipleResultSetSupport = forceMultipleResultSetSupport;
    }
}
