package cn.tedu.sp0ag4studio.core.resource.support;

import cn.tedu.sp0ag4studio.core.resource.ResourceException;
import cn.tedu.sp0ag4studio.core.resource.ResourceHandler;

/**
 * HandlerMapping
 *
 * @author HuangYunHui|XiongChun
 * @since 2010-2-5
 */
public interface HandlerMapping {

    public ResourceHandler mapping(String pName) throws ResourceException;

}
