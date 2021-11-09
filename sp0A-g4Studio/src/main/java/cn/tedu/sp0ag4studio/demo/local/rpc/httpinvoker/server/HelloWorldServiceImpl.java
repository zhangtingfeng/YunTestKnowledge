package cn.tedu.sp0ag4studio.demo.local.rpc.httpinvoker.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.tedu.sp0ag4studio.common.dao.Reader;
import cn.tedu.sp0ag4studio.common.util.SpringBeanLoader;
import cn.tedu.sp0ag4studio.core.metatype.BaseDomain;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;

/**
 * Httpinvoker实现类
 *
 * @author OSWorks-XC
 * @see BaseDomain
 * @since 2010-10-13
 */
public class HelloWorldServiceImpl implements HelloWorldService {

    Log log = LogFactory.getLog(HelloWorldServiceImpl.class);

    /**
     * sayHello
     */
    public String sayHello(String text) {
        return "Hello," + text;
    }

    /**
     * 查询一条结算明细测试数据
     *
     * @param jsbh
     * @return Dto
     */
    public Dto queryBalanceInfo(String jsbh) {
        Reader reader = (Reader) SpringBeanLoader.getSpringBean("g4Reader");
        Dto inDto = new BaseDto("sxh", jsbh);
        Dto outDto = (BaseDto) reader.queryForObject("Demo.queryBalanceInfo", inDto);
        return outDto;
    }

}
