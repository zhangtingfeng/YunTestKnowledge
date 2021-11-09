package cn.tedu.sp25.controller;

import cn.tedu.sp01.util.GraphicHelper;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import cn.tedu.sp25.base.BaseController;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/getDic")
public class Dic extends BaseController {

    /**
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/dictype/{type}")
    public JsonResult<?> getDic(@PathVariable String type) throws IOException, ParseException {


        JsonResult<?> result = JsonResult.ok();
        try {
            Dto dto = new BaseDto();
            dto.put("type", type);

            List<Dto> list = bizService.queryList("sysConfig.queryInitList", dto);
            result = JsonResult.ok(list);

            return result;

        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            String strErr = debugLogUtil.send("QY", "程序报错", e);
            e.printStackTrace();
            return JsonResult.err(strErr);
            //result = reduceErr("生成验证码失败");
        }
    }
}