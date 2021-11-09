package cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.datasource;

import java.util.Map;

import javax.sql.DataSource;


/**
 * DataSourceFactory implementation for the iBATIS SimpleDataSource
 */
public class SimpleDataSourceFactory implements DataSourceFactory {

    private DataSource dataSource;

    public void initialize(Map map) {
        dataSource = null;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

}
