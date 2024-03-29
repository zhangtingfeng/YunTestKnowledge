package cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.type;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.client.extensions.ParameterSetter;

/**
 * A ParameterSetter implementation
 */
public class ParameterSetterImpl implements ParameterSetter {

    private PreparedStatement ps;
    private int index;

    /**
     * Creates an instance for a PreparedStatement and column index
     *
     * @param statement   - the PreparedStatement
     * @param columnIndex - the column index
     */
    public ParameterSetterImpl(PreparedStatement statement, int columnIndex) {
        this.ps = statement;
        this.index = columnIndex;
    }

    public void setArray(Array x) throws SQLException {
        ps.setArray(index, x);
    }

    public void setAsciiStream(InputStream x, int length) throws SQLException {
        ps.setAsciiStream(index, x, length);
    }

    public void setBigDecimal(BigDecimal x) throws SQLException {
        ps.setBigDecimal(index, x);
    }

    public void setBinaryStream(InputStream x, int length) throws SQLException {
        ps.setBinaryStream(index, x, length);
    }

    public void setBlob(Blob x) throws SQLException {
        ps.setBlob(index, x);
    }

    public void setBoolean(boolean x) throws SQLException {
        ps.setBoolean(index, x);
    }

    public void setByte(byte x) throws SQLException {
        ps.setByte(index, x);
    }

    public void setBytes(byte x[]) throws SQLException {
        ps.setBytes(index, x);
    }

    public void setCharacterStream(Reader reader, int length) throws SQLException {
        ps.setCharacterStream(index, reader, length);
    }

    public void setClob(Clob x) throws SQLException {
        ps.setClob(index, x);
    }

    public void setDate(Date x) throws SQLException {
        ps.setDate(index, x);
    }

    public void setDate(Date x, Calendar cal) throws SQLException {
        ps.setDate(index, x, cal);
    }

    public void setDouble(double x) throws SQLException {
        ps.setDouble(index, x);
    }

    public void setFloat(float x) throws SQLException {
        ps.setFloat(index, x);
    }

    public void setInt(int x) throws SQLException {
        ps.setInt(index, x);
    }

    public void setLong(long x) throws SQLException {
        ps.setLong(index, x);
    }

    public void setNull(int sqlType) throws SQLException {
        ps.setNull(index, sqlType);
    }

    public void setNull(int sqlType, String typeName) throws SQLException {
        ps.setNull(index, sqlType, typeName);
    }

    public void setObject(Object x) throws SQLException {
        ps.setObject(index, x);
    }

    public void setObject(Object x, int targetSqlType) throws SQLException {
        ps.setObject(index, x, targetSqlType);
    }

    public void setObject(Object x, int targetSqlType, int scale) throws SQLException {
        ps.setObject(index, x, scale);
    }

    public void setRef(Ref x) throws SQLException {
        ps.setRef(index, x);
    }

    public void setShort(short x) throws SQLException {
        ps.setShort(index, x);
    }

    public void setString(String x) throws SQLException {
        ps.setString(index, x);
    }

    public void setTime(Time x) throws SQLException {
        ps.setTime(index, x);
    }

    public void setTime(Time x, Calendar cal) throws SQLException {
        ps.setTime(index, x, cal);
    }

    public void setTimestamp(Timestamp x) throws SQLException {
        ps.setTimestamp(index, x);
    }

    public void setTimestamp(Timestamp x, Calendar cal) throws SQLException {
        ps.setTimestamp(index, x, cal);
    }

    public void setURL(URL x) throws SQLException {
        ps.setURL(index, x);
    }

    public PreparedStatement getPreparedStatement() {
        return ps;
    }

    public int getParameterIndex() {
        return index;
    }
}
