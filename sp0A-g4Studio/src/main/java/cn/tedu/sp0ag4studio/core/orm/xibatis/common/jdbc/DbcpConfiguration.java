package cn.tedu.sp0ag4studio.core.orm.xibatis.common.jdbc;

import java.util.Map;

import javax.sql.DataSource;


import cn.tedu.sp0ag4studio.core.orm.xibatis.common.beans.Probe;
import cn.tedu.sp0ag4studio.core.orm.xibatis.common.beans.ProbeFactory;

/**
 * Wrapper class to simplify use of DBCP
 */
public class DbcpConfiguration {

    private static final Probe PROBE = ProbeFactory.getProbe();
    private static final String ADD_DRIVER_PROPS_PREFIX = "Driver.";
    private static final int ADD_DRIVER_PROPS_PREFIX_LENGTH = ADD_DRIVER_PROPS_PREFIX.length();
    private DataSource dataSource;

    /**
     * Constructor to supply a map of properties
     *
     * @param properties - the map of configuration properties
     */
    public DbcpConfiguration(Map properties) {
        try {


        } catch (Exception e) {
            throw new RuntimeException("Error initializing DbcpDataSourceFactory.  Cause: " + e, e);
        }
    }

    /**
     * Getter for DataSource
     *
     * @return The DataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }


    private Object convertValue(Object object, String propertyName, String value) {
        Object convertedValue = value;
        Class targetType = PROBE.getPropertyTypeForSetter(object, propertyName);
        if (targetType == Integer.class || targetType == int.class) {
            convertedValue = Integer.valueOf(value);
        } else if (targetType == Long.class || targetType == long.class) {
            convertedValue = Long.valueOf(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            convertedValue = Boolean.valueOf(value);
        }
        return convertedValue;
    }


    private boolean notEmpty(String s) {
        return s != null && s.length() > 0;
    }

}
