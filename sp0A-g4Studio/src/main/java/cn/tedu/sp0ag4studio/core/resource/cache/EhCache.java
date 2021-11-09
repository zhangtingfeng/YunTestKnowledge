package cn.tedu.sp0ag4studio.core.resource.cache;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.tedu.sp0ag4studio.core.resource.Cache;
import cn.tedu.sp0ag4studio.core.resource.CacheException;

/**
 * EhCache
 *
 * @author HuangYunHui|XiongChun
 * @since 2009-10-13
 */
public class EhCache implements Cache {


    private final Log logger = LogFactory.getLog(this.getClass());

    private static final String G4_RESOURCE_GROUP = "g4ResourceCache";

    /**
     * Builds a Cache.
     * <p>
     * Even though this method provides properties, they are not used.
     * Properties for EHCache are specified in the ehcache.xml file.
     * Configuration will be read from ehcache.xml for a cache declaration where
     * the name attribute matches the name parameter in this builder.
     *
     * @param name       the name of the cache. Must match a cache configured in
     *                   ehcache.xml
     * @param properties not used
     * @return a newly built cache will be built and initialised
     * @throws CacheException inter alia, if a cache of the same name already exists
     */
    public void init() throws CacheException {


    }

    public void put(Object key, Object pValue) throws CacheException {

    }

    public Object get(Object key) throws CacheException {
        return null;
    }

    public void remove(Object key) throws CacheException {

    }

    public void clear() throws CacheException {

    }

    public void destroy() throws CacheException {

    }

}
