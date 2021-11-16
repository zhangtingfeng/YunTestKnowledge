package cn.eggsoft.sp10;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
//import org.springframework.cloud.openfeign.EnableFeignClients;


//@EnableFeignClients() //开启FeignClient客户端服务注解，如果引导类位置下有该包名，则不需要添加路径，如果没有，则需要手动添加路径


//@MapperScan({"org.springframework.data.mongodb.core.MongoTemplate","cn.tuoke.eggsoft.sp10"})//#Consider defining a bean of type '' in your configuration.
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
public class sp10MongodbApplication {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
       /* try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdata?serverTimezone=UTC&autoReconnect=true&user=root&password=000257&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&characterEncoding=utf-8&useSSL=true");
            Statement s = conn.createStatement();
            System.out.println(conn.isClosed());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        SpringApplication.run(sp10MongodbApplication.class, args);
    }

}
