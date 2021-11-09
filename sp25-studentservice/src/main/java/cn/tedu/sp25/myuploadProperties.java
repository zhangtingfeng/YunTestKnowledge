package cn.tedu.sp25;

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

    @Value("${myupload.NewsPager_url}")
    private String NewsPager_url;

    public String getNewsPager_url() {
        return NewsPager_url;
    }
}