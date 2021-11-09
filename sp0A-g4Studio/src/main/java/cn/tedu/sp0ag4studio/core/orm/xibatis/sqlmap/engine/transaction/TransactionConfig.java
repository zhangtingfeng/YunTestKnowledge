package cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public interface TransactionConfig {

    Transaction newTransaction(int transactionIsolation) throws SQLException, TransactionException;

    DataSource getDataSource();

    void setDataSource(DataSource ds);

    /**
     * This should not be used and is here purely to avoid spring integration
     * from breaking
     *
     * @return -1
     * @deprecated
     */
    int getMaximumConcurrentTransactions();

    /**
     * This should not be used. It does nothing and is here purely to prevent
     * Spring integration from breaking
     *
     * @param maximumConcurrentTransactions
     * @deprecated
     */
    void setMaximumConcurrentTransactions(int maximumConcurrentTransactions);

    boolean isForceCommit();

    void setForceCommit(boolean forceCommit);

    /**
     * This method should call setProperties. It is here simply to ease
     * transition
     *
     * @param props - Properties
     * @deprecated
     */
    void initialize(Properties props) throws SQLException, TransactionException;

    void setProperties(Properties props) throws SQLException, TransactionException;

}
