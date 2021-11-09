package cn.tedu.sp0ag4studio.system.common.util.idgenerator;

import cn.tedu.sp0ag4studio.common.dao.Dao;
import cn.tedu.sp0ag4studio.common.util.SpringBeanLoader;
import cn.tedu.sp0ag4studio.core.id.SequenceStorer;
import cn.tedu.sp0ag4studio.core.id.StoreSequenceException;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;

/**
 * ID数据库逻辑存储器
 *
 * @author OSWorks-XC
 * @since 2010-03-21
 */
public class DBSequenceStorer implements SequenceStorer {

    private Dao g4Dao = (Dao) SpringBeanLoader.getSpringBean("g4Dao");

    /**
     * 返回当前最大序列号
     */
    public long load(String pIdColumnName) throws StoreSequenceException {
        Dto dto = new BaseDto();
        dto.put("fieldname", pIdColumnName);
        dto = (BaseDto) g4Dao.queryForObject("IdGenerator.getEaSequenceByFieldName", dto);
        Long maxvalue = dto.getAsLong("maxid");
        return maxvalue.longValue();
    }

    /**
     * 写入当前生成的最大序列号值
     */
    public void updateMaxValueByFieldName(long pMaxId, String pIdColumnName) throws StoreSequenceException {
        Dto dto = new BaseDto();
        dto.put("maxid", String.valueOf(pMaxId));
        dto.put("fieldname", pIdColumnName);
        g4Dao.update("IdGenerator.updateMaxValueByFieldName", dto);
    }
}
