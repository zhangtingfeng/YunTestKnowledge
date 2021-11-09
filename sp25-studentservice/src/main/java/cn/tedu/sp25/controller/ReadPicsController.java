package cn.tedu.sp25.controller;


import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp25.base.BaseController;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.text.ParseException;


@RestController
@RequestMapping("/TestFeign")
public class ReadPicsController extends BaseController {


    //设置user-service服务名，feign根据服务名去eureka注册中心查找注册地址
    @FeignClient(value = "teacher-service")
    public interface UserFeign {

        @GetMapping("/admin/readPics")
        public JsonResult findByUserName(@PathVariable("userName") String userName);

        //服务消费者写法，用到了POST  http://localhost:48028/student-service/TestFeign/readPics  该地址测试
        @RequestMapping(value = "teacher-service/admin/readPics", method = RequestMethod.POST)
        public JsonResult readPics(@RequestBody JsonResult JsonResultbase64);

    }


    //导入了api模块依赖，这里就可以直接注入该接口
    @Autowired
    private UserFeign userFeign;



/*
// fegin接口定义注意点
1.请求路径要和服务方的一致(/user/{userName})
            2.请求参数要和服务方的一致(@PathVariable("userName") String userName)
3.返回值要和服务方的一致(User)
4.和方法名称无关
————————————————
    版权声明：本文为CSDN博主「菜鸟传说」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/weixin_43948783/article/details/110289369

    http://localhost:48028/student-service/TestFeign/readPics
    */

    @RequestMapping(value = "/readPics", method = RequestMethod.POST)
    public JsonResult readPics(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        try {
//直接使用userFeign调用其方法
            JsonResult ffff111;
            // JsonResult ffff = userFeign.findByUserName("tttt");
            String strMD5Key = cn.tedu.sp01.util.Md5Util.parseStrToMd5L32(JsonResultbase64.toString());
            String strredisTemplate = (String) redisServiceImpl.getKey("_IMAGE_readPics_" + strMD5Key);
            if (strredisTemplate == null) {
                ffff111 = userFeign.readPics(JsonResultbase64);
                //redisServiceImpl.delKey("_IMAGE_readPics_" + strMD5Key);
                // redisService.delete(vtoken + "_IMAGE_CODE");
                // JSONObject.
                // String strSave=JSONArray.toJSONString(ffff111);
                // redisServiceImpl.setStr("_IMAGE_readPics_" + strMD5Key , strSave, TimeUnit.SECONDS.toSeconds(60));//600秒过期

            } else {
                JsonResult jsonObject = JSON.parseObject(strredisTemplate, JsonResult.class);// JSONObject.fromObject(strredisTemplate);
                ffff111 = JsonResult.ok("降低数据库消耗").data(jsonObject).msg("缓存读取成功");
                ;
            }


            return ffff111;

            /* feign 测试通过 ribbon  eureka
            * http://localhost:48001
            *headRouter  student-service/TestFeign/readPics
            {
    "code": 0,
    "msg": null,
    "data": {
        "base64": "KKACiiigAooooA/9k=",
        "filename": "DrawingAndColoring-2021-03-06_18-07-39.jpg",
        "account":"testpostman",
        "excludeuploadfileIDList":["2021-03-20-12_39_10_659_ij2b,2021-03-20-12_39_20_376_QVPA","2021-03-20"]
    }
}
*
*
*
*  */


            // } else
            // return JsonResult.err("验证失败");
        } catch (Exception eee) {
            String strErr = debugLogUtil.send("tools-service", "uploadpost报错", eee);
            return JsonResult.err(strErr);
        }
//;
        // return JsonResult.err("验证失败");
    }

}
