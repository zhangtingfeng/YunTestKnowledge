package cn.tedu.sp06;


import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp06.config.RedisServiceImpl;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;


@Service
public class ComService {

    @Autowired
    private RestTemplate restTemplate;

    //protected cn.tedu.sp0ag4studio.common.util.WebUtils webUtils;
    @HystrixCommand(fallbackMethod = "addServiceFallbackget", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")
    })
    public JsonResult<?> getService(String strHeadpath) throws IOException, ParseException {
        JsonResult<?> jsonResult = JsonResult.err();
        try {
            jsonResult = restTemplate.getForObject("http://" + strHeadpath, JsonResult.class);
            debugLogUtil.send("ribbonGetService", "get返回数据" + strHeadpath, jsonResult);

            return jsonResult;

        } catch (Exception ee) {
            debugLogUtil.send("ribbongetGetService", "返回数据错误" + strHeadpath, jsonResult);
            debugLogUtil.send("ribbongetGetService", "返回数据错误" + strHeadpath, ee);

            return JsonResult.err().msg("Get门户服务中断" + strHeadpath).code(800).data(strHeadpath);
        }

        //return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
    }


    @HystrixCommand(fallbackMethod = "addServiceFallbackpost", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")
    })
    public JsonResult<?> postervice(String strHeadpath, Object items) throws IOException, ParseException {
        JsonResult<?> jsonResult = JsonResult.err();
        try {

            debugLogUtil.send("ribboPostService", "post收到请求数据" + strHeadpath, items);
            jsonResult = restTemplate.postForObject("http://" + strHeadpath, (items), JsonResult.class);
            debugLogUtil.send("ribboPostService", "post返回处理数据" + strHeadpath, jsonResult);


            return jsonResult;

        } catch (Exception ee) {
            debugLogUtil.send("ribbonPostService", "返回数据错误" + strHeadpath, jsonResult);
            debugLogUtil.send("ribbonPostService", "返回数据错误" + strHeadpath, ee);

            return JsonResult.err().msg("post门户服务中断" + strHeadpath).code(800).data(strHeadpath);
        }
        // debugLogUtil.send("ribbonpostervice", "正常返回数据"+strHeadpath, jsonResult);

        //  return jsonResult;
        // return rt.getForObject("http://" + strHeadpath, JsonResult.class);
        //return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
    }


    public JsonResult<?> addServiceFallbackpost(String strHeadpath, Object items) throws IOException, ParseException {

        debugLogUtil.send("ribbonpostpost服务post熔断", strHeadpath, items);

        return JsonResult.err().msg("post服务熔断" + strHeadpath).code(800).data(items);
    }


    public JsonResult<?> addServiceFallbackget(String strHeadpath) throws IOException, ParseException {
        debugLogUtil.send("ribbonget服务get熔断", strHeadpath, strHeadpath);

        return JsonResult.err().msg("get服务熔断" + strHeadpath).code(800);
    }


    private String ifValidVisitUrl(String strVisitURL) {
        String StrExclude1 = "student-service/SysUser/getVtoken";
        String StrExclude2 = "student-service/SysUser/getLoginCode";
        String StrExclude3 = "student-service/SysUser/login";
        boolean status0 = StringUtils.isEmpty(strVisitURL);
        boolean status1 = strVisitURL.contains(StrExclude1);
        boolean status2 = strVisitURL.contains(StrExclude2);
        boolean status3 = strVisitURL.contains(StrExclude3);
        String StrReturnError = "";
        if (status0) {
            StrReturnError = "Headpath is null";
        } else if (status1 || status2 || status3) {
            StrReturnError = "";
        } else {
            StrReturnError = "";
        }
        return StrReturnError;
    }

}
