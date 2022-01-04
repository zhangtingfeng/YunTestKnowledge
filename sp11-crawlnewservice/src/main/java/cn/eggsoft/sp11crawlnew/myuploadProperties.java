package cn.eggsoft.sp11crawlnew;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class myuploadProperties {

    @Value("${myupload.netRootpath}")
    private String netRootpath;
    public String getNetRootpath() {
        return netRootpath;
    }

    @Value("${myupload.rawlMorning_url}")
    private String rawlMorning_url;
    public String getRawlMorning_url() {
        return rawlMorning_url;
    }

    @Value("${myupload.weeksPic_url}")
    private String weeksPic_url;
    public String getWeeksPic_url() {
        return weeksPic_url;
    }

    @Value("${myupload.netUrl}")
    private String netUrl;
    public String getNetUrl() {
        return netUrl;
    }


}