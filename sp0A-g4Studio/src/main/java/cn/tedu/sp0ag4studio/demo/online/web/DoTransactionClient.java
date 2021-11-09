package cn.tedu.sp0ag4studio.demo.online.web;

import cn.tedu.sp0ag4studio.common.util.SpringBeanLoader;
import cn.tedu.sp0ag4studio.common.web.BaseAction;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.demo.online.service.DemoService;

public class DoTransactionClient {

    /**
     * 演示事务控制
     *
     * @author OSWorks-XC
     * @see BaseAction
     * @since 2011-2-30
     */
    public static void main(String[] args) {
        DemoService demoService = (DemoService) SpringBeanLoader.getSpringBean("demoService");
        Dto outDto = demoService.doTransactionTest();
        System.out.println("返回值:\n" + outDto);
    }

}
