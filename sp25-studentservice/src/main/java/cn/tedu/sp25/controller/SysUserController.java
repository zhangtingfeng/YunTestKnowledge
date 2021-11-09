package cn.tedu.sp25.controller;

import cn.tedu.sp01.util.GraphicHelper;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp25.base.BaseController;
import com.alibaba.fastjson.JSONArray;
import cn.tedu.sp0ag4studio.common.util.WebUtils;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/SysUser")
public class SysUserController extends BaseController {


    @Value("${server.port}")
    private int port;


    @PostMapping("/getVtoken")
    @ResponseBody
    public JsonResult<?> getItems() throws IOException {
////http://localhost:48028/SysUser/getVtoken  "code":200,"msg":null,"data":"c801b2a3-021f-4615-b22b-17fc6d9d4efe"}
        try {
            String strrandomUUID = UUID.randomUUID().toString();

            //dddd.put("UUK",strrandomUUID);
            return JsonResult.ok().data(strrandomUUID);
        } catch (Exception e) {
            e.printStackTrace();

            //debugLogUtil.send("QY", "程序报错", e.getMessage());
            return JsonResult.err();
        }
        //List<Item> items = itemService.getItems(orderId);

    }


    /**
     * 登录
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/login")
    public JsonResult<?> login(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        Dto dto = WebUtils.getParamAsDto(JsonResultbase64.datetoMap());

        /// debugLog.send("QY","dto="+request.getRequestURI(),dto);

        // JsonResult<?> result = new JsonResult();
        try {
            String strgetData = JsonUtil.serialize(JsonResultbase64.getData());
            // String straccount=JsonUtil.getString(strgetData,"account");
            // String strpassword= DigestUtils.md5DigestAsHex(JsonUtil.getString(strgetData,"password").getBytes());
            String strverifyCode = JsonUtil.getString(strgetData, "verifyCode");
            String strvtoken = JsonResultbase64.getToken();
            String strredisTemplate = redisServiceImpl.getKey("_IMAGE_CODE" + strvtoken).toString();
            if (!(strredisTemplate == null ? "" : strredisTemplate.toString()).equalsIgnoreCase(strverifyCode)) {
                return JsonResult.err("验证码错误");
            }

            Dto member = (BaseDto) bizService.queryForObject(
                    "sysUser.loginByAccount", dto);
            //  debugLog.send("QY","member="+request.getRequestURI(),member);
            if (member != null && member.getAsLong("id") != null) {
                if (member.getAsString("status").equals("0")) {
                    throw new Exception("用户已离职，帐号失效");
                }
                String token = UUID.randomUUID().toString();
                String loginChannel = dto.getAsString("loginChannel");
                //Dto sysConfig = CommonUtil.getSysConfig();
                ///   redisService.setValue(token, JSONArray.toJSONString(member),
                //        sysConfig.getAsLong(loginChannel));
                redisServiceImpl.setStr(strvtoken, JSONArray.toJSONString(member),
                        180000L);
                Dto chatMap = new BaseDto();
                chatMap.put("userId", member.get("id"));
                chatMap.put("username", member.get("username"));
                chatMap.put("portraitUri", member.get("pic"));
                chatMap.put("chat_token", member.get("token"));
                chatMap.put("rytoken", member.get("rytoken"));
                member.put("token", token);
                member.put("chatUser", chatMap);
                member.put("isAuth", true);
                member.put("result", "图形验证码正确");


                //查詢商鋪信息
                /// String shop_id = member.getAsString("shop_id");
                //  member.put("login_shop_id",shop_id);
                redisServiceImpl.delKey("_Login_CODE" + strvtoken);
                redisServiceImpl.setStr("_Login_CODE" + strvtoken, chatMap.toJson(), TimeUnit.SECONDS.toSeconds(60 * 60 * 24));//1天过期

                //result.setData(member);
                return JsonResult.ok("登陆成功").data(member);


                // result.setData(member);
            } else {
                return JsonResult.err("用户名或密码错误");
                // throw new Exception("用户名或密码错误。");
            }
        } catch (Exception e) {
            String strErr = debugLogUtil.send("登陆", "程序报错", e);
            log.info(strErr);
            e.printStackTrace();
            return JsonResult.err(strErr);
        }
        // return result;
    }

    /**
     * 生成图形验证码
     * http://localhost:48028/studentservice/SysUser/getLoginCode/8888
     *
     * @param rquest
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/getLoginCode/{vtoken}")
    public JsonResult<?> createAuthCode(@PathVariable String vtoken) throws IOException, ParseException {
        // redisTemplate.delete("ddddd");

        Map<String, Object> map = new HashMap<String, Object>();


        try {
            StringBuilder xBase64 = new StringBuilder();

            String verifyCode = GraphicHelper.createImge(130, 36, "png", xBase64);
            // 图片验证码存放到缓存60秒
            redisServiceImpl.delKey("_IMAGE_CODE" + vtoken);
            // redisService.delete(vtoken + "_IMAGE_CODE");
            redisServiceImpl.setStr("_IMAGE_CODE" + vtoken, verifyCode, TimeUnit.SECONDS.toSeconds(600));//600秒过期
            //  debugLogUtil.send("QY","放入Redis成功",verifyCode+"    "+vtoken + "_IMAGE_CODE");
            // map.keySet("ImageCode",verifyCode);
            // map.put("ImageCode", verifyCode);
            map.put("ImageBase64", xBase64);
            return JsonResult.ok("生成验证码成功").data(map);

        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            String strErr = debugLogUtil.send("QY", "程序报错", e);
            e.printStackTrace();
            return JsonResult.err(strErr);
            //result = reduceErr("生成验证码失败");
        }
    }
}
