package cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction.jdbc;

import java.sql.SQLException;
import java.util.Properties;

import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction.BaseTransactionConfig;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction.Transaction;
import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.transaction.TransactionException;

public class JdbcTransactionConfig extends BaseTransactionConfig {

    public Transaction newTransaction(int transactionIsolation) throws SQLException, TransactionException {
        return new JdbcTransaction(dataSource, transactionIsolation);
    }

    public void setProperties(Properties props) throws SQLException, TransactionException {
    }

}
