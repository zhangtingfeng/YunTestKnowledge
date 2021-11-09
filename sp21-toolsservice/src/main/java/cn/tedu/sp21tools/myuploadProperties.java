package cn.tedu.sp21tools;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "myupload")
public class myuploadProperties {

    private String serverIMGUpload;

    private String img_url;
}