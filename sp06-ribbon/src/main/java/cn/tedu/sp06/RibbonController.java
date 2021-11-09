package cn.tedu.sp06;


import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp06.config.RedisServiceImpl;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
@Configuration
class ApplicationContextConfig {
    @Bean
    @LoadBalanced    ///使用负载均衡的能力
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
*/

@RestController
@Slf4j
public class RibbonController {
    //@LoadBalanced    ///使用负载均衡的能力
    @Autowired
    private RestTemplate rt;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    protected RedisServiceImpl redisServiceImpl;


    @Autowired
    private ComService computeService;

    ///http://localhost:3001/item-service/354t3t34t2
    @GetMapping
    public JsonResult<?> anyget() throws IOException, ParseException {
        try {
/// ////http://localhost:3001/  headRouter=item-service/item/getItems/999
            String strHeadpath = request.getHeader("headRouter");
            String strparams = request.getHeader("params");

            debugLogUtil.send("RibbonController", "anyget收到请求" + strHeadpath, strHeadpath);

            Map<String, String> dddddheadRouter = getHeadersInfo();
            ///以后再做吧
            // strHeadpath= dddddheadRouter.("headRouter");
            String[] str = strHeadpath.split("/");
            strHeadpath = str[0] + "/" + strHeadpath;

            JsonResult<?> anypostJsonResult=null;

            String strredisSeconds = request.getHeader("redisSeconds");
            //int dddd=int
            //boolean dddd= StringUtils.isEmpty(strredisSeconds);
            if (strredisSeconds!=null && !strredisSeconds.equals("[object Undefined]") && Long.parseLong(strredisSeconds)>0) {
                //Dto inDto = webUtils.getParamAsDto(items);
                String strMD5Key = "getService" +cn.tedu.sp01.util.Md5Util.parseStrToMd5L32(strHeadpath);
                String strredisTemplate = (String) redisServiceImpl.getKey( strMD5Key);
                if (strredisTemplate == null) {
                    anypostJsonResult=computeService.getService(strHeadpath);//  rt.getForObject("http://" + strHeadpath, JsonResult.class);
                    if (anypostJsonResult.getCode()==200){
                        Long longSecond=Long.parseLong(strredisSeconds);
                        redisServiceImpl.setStr(strMD5Key,JsonUtil.serialize(anypostJsonResult),longSecond);
                    }
                }
                else{
                    anypostJsonResult=JsonUtil.deserialize(strredisTemplate,JsonResult.class);
                }
            }
            else{
                anypostJsonResult=computeService.getService(strHeadpath);//  rt.getForObject("http://" + strHeadpath, JsonResult.class);
            }





            // return strHeadpath;
            //post http://localhost:3001/   headRouter= item-service/item/decreaseNumber/999
            //int dddd=  1/0;
            //用 rt 调用远程服务，发送post请求
            //商品数据放在协议体中，向远程服务提交
            return anypostJsonResult;
        } catch (Exception eee) {
            debugLogUtil.send("RibbonController", "RibbonController报错", eee);

            return JsonResult.err().data(eee.toString()).msg(eee.getMessage()).code(1201);
        }
    }


    @PostMapping
    public JsonResult<?> anypost(@RequestBody Object items) throws IOException, ParseException {
        try {

            JsonResult<?> anypostJsonResult=null;


            String strHeadpath = request.getHeader("headRouter");
            debugLogUtil.send("RibbonController", "anypost收到请求" + strHeadpath, items);
            String[] str = strHeadpath.split("/");
            strHeadpath = str[0] + "/" + strHeadpath;

            String strredisSeconds = request.getHeader("redisSeconds");

            //int dddd=int
            //boolean dddd= StringUtils.isEmpty(strredisSeconds);
            if (strredisSeconds!=null && !strredisSeconds.equals("[object Undefined]") && Long.parseLong(strredisSeconds)>0) {
                //Dto inDto = webUtils.getParamAsDto(items);
                String strMD5Key = "postService" +cn.tedu.sp01.util.Md5Util.parseStrToMd5L32(strHeadpath + JsonUtil.toString(items));
                String strredisTemplate = (String) redisServiceImpl.getKey( strMD5Key);
                if (strredisTemplate == null) {
                    anypostJsonResult=computeService.postervice(strHeadpath, items);//  rt.getForObject("http://" + strHeadpath, JsonResult.class);
                    if (anypostJsonResult.getCode()==200){
                        Long longSecond=Long.parseLong(strredisSeconds);
                        redisServiceImpl.setStr(strMD5Key,JsonUtil.serialize(anypostJsonResult),longSecond);
                    }
                }
                else{
                    anypostJsonResult=JsonUtil.deserialize(strredisTemplate,JsonResult.class);
                }
            }
            else{
                anypostJsonResult=computeService.postervice(strHeadpath, items);//  rt.getForObject("http://" + strHeadpath, JsonResult.class);
            }





            // return strHeadpath;
            //post http://localhost:3001/   headRouter= item-service/item/decreaseNumber/999
            //int dddd=  1/0;
            //用 rt 调用远程服务，发送post请求
            //商品数据放在协议体中，向远程服务提交
            return anypostJsonResult;


            //   return rt.postForObject("http://" + strHeadpath, (items), JsonResult.class);
        } catch (Exception eee) {
            debugLogUtil.send("RibbonController", "RibbonController报错", eee);
            return JsonResult.err().data(eee.toString()).msg(eee.getMessage()).code(1201);
        }

    }
/*
    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId) {
        return rt.getForObject("http://user-service/{1}", JsonResult.class, userId);
    }

    @GetMapping("/user-service/{userId}/score")
    public JsonResult<?> addScore(@PathVariable Integer userId, Integer score) {
        return rt.getForObject("http://user-service/{1}/score?score={2}", JsonResult.class, userId, score);
    }

    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId) {
        return rt.getForObject("http://order-service/{1}",JsonResult.class,orderId);
    }

    @GetMapping("/order-service/")
    public JsonResult addOrder() {
        return rt.getForObject("http://order-service/",JsonResult.class);
    }

*/


    //...

    //get request headers
    private Map<String, String> getHeadersInfo() {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
