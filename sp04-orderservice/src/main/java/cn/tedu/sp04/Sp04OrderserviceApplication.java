package cn.tedu.sp04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sp04OrderserviceApplication {
    ///(1)根据orderId，获取订单：http://localhost:8201/123abc ，测试结果如下：
    public static void main(String[] args) {
        SpringApplication.run(Sp04OrderserviceApplication.class, args);
    }
    //   保存订单：http://localhost:8201/
}
