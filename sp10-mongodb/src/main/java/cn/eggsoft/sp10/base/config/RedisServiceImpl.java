package cn.eggsoft.sp10.base.config;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl {
    @Autowired

    private StringRedisTemplate stringRedisTemplate;


    public void setStr(String key, String value) {
        setStr(key, value, null);
    }

    public void setStr(String key, Object value, Long time) {
        if (value == null) {
            return;
        }
        if (value instanceof String) {
            String obj = (String) value;
            stringRedisTemplate.opsForValue().set(key, obj);
        } else if (value instanceof List) {
            List obj = (List) value;
            stringRedisTemplate.opsForList().leftPushAll(key, obj);
        } else if (value instanceof Map) {
            Map obj = (Map) value;
            stringRedisTemplate.opsForHash().putAll(key, obj);
        }
        if (time != null)
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public Object getKey(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void delKey(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 返回对象泛型
     *
     * @param key
     * @param cla
     * @return
     */
    public <T> T getObject(final String key, Class<?> cla) {
        if (StringUtils.isNotEmpty(key)) {
            Object objectvalue = this.getKey(key);


            try {
                if (objectvalue == null) {
                    return null;
                }
                String value = objectvalue.toString();
                if (!StringUtils.isNotEmpty(value)) {
                    return null;
                }

                @SuppressWarnings("unchecked")
                T t = (T) JSON.parseObject(value, cla);
                return t;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 返回对象list 泛型
     *
     * @param key
     * @param cla
     * @return
     */
    public List<?> getList(final String key, Class<?> cla) {
        String value = this.getKey(key).toString();
        if (!StringUtils.isNotEmpty(value)) {
            return null;
        }
        try {
            List<?> tlist = JSON.parseArray(value, cla);
            return tlist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除多个
     *
     * @param keys
     */
    public void delete(List<String> keys) {
        delete(keys);
    }


}
