package cn.eggsoft.sp34pricereport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class myuploadProperties {

    @Value("${myupload.serverIMGUpload}")
    private String serverIMGUpload;

    public String getServerIMGUpload() {
        return serverIMGUpload;
    }

    @Value("${myupload.img_url}")
    private String img_url;

    public String getImg_url() {
        return img_url;
    }
}