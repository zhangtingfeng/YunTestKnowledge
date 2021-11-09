package cn.tedu.sp0ag4studio.demo.local.rpc.httpinvoker.server;

import cn.tedu.sp0ag4studio.core.metatype.BaseDomain;
import cn.tedu.sp0ag4studio.core.metatype.Dto;

/**
 * Httpinvoker接口
 *
 * @author OSWorks-XC
 * @see BaseDomain
 * @since 2010-10-13
 */
public interface HelloWorldService {
    /**
     * sayHello
     *
     * @param text
     * @return
     */
    public String sayHello(String text);

    /**
     * 查询一条结算明细测试数据
     *
     * @param jsbh
     * @return XML字符串
     */
    public Dto queryBalanceInfo(String jsbh);

}
