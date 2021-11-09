package cn.tedu.sp0ag4studio.demo.local.rpc.webservice.client;

import cn.tedu.sp0ag4studio.common.util.SpringBeanLoader;
import cn.tedu.sp0ag4studio.demo.local.rpc.webservice.HelloWorldService;

public class RunWebServiceClient {

    public static void main(String[] args) {
        sayHello();
        queryBalanceInfo();
    }

    private static void sayHello() {
        HelloWorldService helloWorldService = (HelloWorldService) SpringBeanLoader.getSpringBean("client_webservice");
        String outString = helloWorldService.sayHello("Lily!");
        System.out.println(outString);
    }

    private static void queryBalanceInfo() {
        HelloWorldService helloWorldService = (HelloWorldService) SpringBeanLoader.getSpringBean("client_webservice");
        String outString = helloWorldService.queryBalanceInfo("BJLK1000000003900");
        System.out.println(outString);
    }
}
