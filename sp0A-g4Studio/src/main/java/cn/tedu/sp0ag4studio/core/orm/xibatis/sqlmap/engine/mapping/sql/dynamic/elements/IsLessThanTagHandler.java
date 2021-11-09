package cn.tedu.sp0ag4studio.core.orm.xibatis.sqlmap.engine.mapping.sql.dynamic.elements;

public class IsLessThanTagHandler extends ConditionalTagHandler {

    public boolean isCondition(SqlTagContext ctx, SqlTag tag, Object parameterObject) {
        long x = compare(ctx, tag, parameterObject);
        return x < 0 && x != ConditionalTagHandler.NOT_COMPARABLE;
    }

}
