package cn.tedu.sp25;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients() //开启FeignClient客户端服务注解，如果引导类位置下有该包名，则不需要添加路径，如果没有，则需要手动添加路径
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
public class Sp25StudentserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp25StudentserviceApplication.class, args);
    }


}

