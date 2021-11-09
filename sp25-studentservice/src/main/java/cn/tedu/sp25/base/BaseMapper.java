package cn.tedu.sp25.base;


import cn.tedu.sp0ag4studio.core.metatype.Dto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class BaseMapper {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    public List queryList(String statementName, Object parameterObject) {
        try {
            return sqlSessionTemplate.selectList(statementName, parameterObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List queryList(String statementName) {
        try {
            return sqlSessionTemplate.selectList(statementName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object queryForObject(String statementName, Object parameterObject) {
        return sqlSessionTemplate.selectOne(statementName, parameterObject);
    }

    public Object queryForDto(String statementName, Object parameterObject) {
        return sqlSessionTemplate.selectOne(statementName, parameterObject);
    }

    public List queryForList(String statementName, Object parameterObject) {
        return sqlSessionTemplate.selectList(statementName, parameterObject);
    }

    public List queryForList(String statementName) {
        return sqlSessionTemplate.selectList(statementName);
    }

    public List queryForPage(String statementName, Object parameterObject) {
        return sqlSessionTemplate.selectList(statementName, parameterObject);
    }

    public int update(Dto pDto) {
        return sqlSessionTemplate.update(pDto.getAsString("tableName") + ".updateInfo", pDto);
    }

    public int updateInfo(String statementName, Object parameterObject) {
        return sqlSessionTemplate.update(statementName, parameterObject);
    }

    public int save(Dto pDto) {
        return sqlSessionTemplate.insert(pDto.getAsString("tableName") + ".saveInfo", pDto);
    }

    public int saveInfo(String statementName, Object parameterObject) {
        return sqlSessionTemplate.insert(statementName, parameterObject);
    }

    public int delete(String statementName, Object parameterObject) {
        return sqlSessionTemplate.delete(statementName, parameterObject);
    }

    public int delete(Dto pDto) {
        return sqlSessionTemplate.delete(pDto.getAsString("tableName") + "." + pDto.getAsString("method"), pDto);
    }

    public List queryForPageCenter(String statementName, Dto qDto) throws SQLException {
        Integer pageIndex = qDto.getAsInteger("start");
        Integer size = qDto.getAsInteger("limit");
        if (null != pageIndex) {
            Integer start = (pageIndex - 1) * size;
            qDto.put("start", start);
        } else {
            qDto.put("start", 0);
        }
        if (null != size) {
            qDto.put("end", size);
        } else {
            qDto.put("end", 999999);
        }
        List<Dto> list = sqlSessionTemplate.selectList(statementName, qDto);
        return list;
    }
}
