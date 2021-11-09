package cn.tedu.sp21tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
public class Sp21ToolsserviceApplication {


    /*
    * 手动指定 application.yml
通过 --spring.config.location 参数可以指定配置文件的位置。
java -jar /abc/xxx.jar --spring.config.location=/abc/application.yml

    *
    *
    * */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Sp21ToolsserviceApplication.class, args);

        Binder binder = Binder.get(context.getEnvironment());

        // 绑定简单配置
        myuploadProperties foo = binder.bind("myupload", Bindable.of(myuploadProperties.class)).get();
        System.out.println(foo.getServerIMGUpload());
        FileUtils.IMG_PATH = foo.getServerIMGUpload();
        System.out.println(foo.getImg_url());
        FileUtils.img_url = foo.getImg_url();
    }

}
