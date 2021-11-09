package cn.tedu.sp25.controller;


import cn.tedu.sp01.util.CommonUtil;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import cn.tedu.sp25.base.BaseController;
import cn.tedu.sp25.controller.QuizStatics.Count_QuizID_32_25_____;
import cn.tedu.sp25.controller.QuizStatics.Count_QuizID_4_5_;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wxSP")
public class wxSPController extends BaseController {
    private static final String SPPageOpenidUrl = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String SPAppid = "wxbc586cfa552bfaa0";
    private static final String SPSecret = "63c3a7c9a7f8d95b68bcf17c38449912";
    public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //微信公众号获取用户信息
    public final static String GetPageUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    //微信小程序获取用户信息
    public final static String GetPageUserInfoUrl_XCX = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


    /**
     * 查询对象
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/toLogin")
    public JsonResult<?> toLogin(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {

            String code = dto.getAsString("code");
            //请求参数
            String params = "appid=" + SPAppid + "&secret=" + SPSecret + "&js_code=" + code + "&grant_type=authorization_code";
            // 发送请求
            String urlNameString = SPPageOpenidUrl + "?" + params;
            String sr = CommonUtil.HttpClientGet(urlNameString);

            //logger.info("获取到的用户openid信息:"+sr);
            JSONObject jsonObject = JSONObject.parseObject(sr);
            Object wxOpenId = jsonObject.get("openid");
            Object wxSessionKey = jsonObject.get("session_key");
            if (wxOpenId == null || wxSessionKey == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("cmd", "login");
                map.put("status", "failed");
                map.put("msg", "未获取到openid,code无效。");
                // return JSON.toJSONString(map);
            }

            Dto dtoOpenid = new BaseDto();
            dtoOpenid.put("sp_openid", wxOpenId.toString());
////https://blog.csdn.net/puyinggong/article/details/100138629  休息 不想搞了
            Dto member = (BaseDto) bizService.queryForObject(
                    "invest_user.getInfo", dtoOpenid);
            if (member != null) {
                //查询到用户信息，更新登录 时间，并返回信息
                result = JsonResult.ok(member);
            } else {
                dto.put("sp_openid", wxOpenId.toString());


                bizService.saveInfo("invest_user.saveInfo", dto);
                member = (BaseDto) bizService.queryForObject(
                        "invest_user.getInfo", dtoOpenid);
                result = JsonResult.ok(member);
            }
            //  Dto info = (BaseDto) bizService.queryForDto(dto.getAsString("t") + "." + sr, dto);

            //result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }


    /**
     * 查询对象
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryQuizInfo")
    public JsonResult<?> queryInfo(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
////////////////////  访问路径跟踪  start
            Dto infoinvest_visit_path = new BaseDto();

            infoinvest_visit_path.put("visitUserID", dto.getAsString("visitUserID"));
            infoinvest_visit_path.put("WhichFuntionID", dto.getAsString("id"));
            infoinvest_visit_path.put("FuntionType", "invest_quiz");
            bizService.saveInfo("invest_visit_path.saveInfo", infoinvest_visit_path);
////////////////////  访问路径跟踪  end


            /*Dto member = redisService.getObject(dto.getAsString("token"), BaseDto.class);
            if(!StringUtils.isNotEmpty(dto.getAsString("loginCode"))){
                if(null == member){
                    result.setCode(StatusConstant.CODE_4000);
                    result.setMsg("请登录");
                    return result;
                }
            }*/
            String sql = dto.getAsString("sql");
            if (!StringUtils.isNotBlank(sql)) {
                sql = "getInfo";
            }

            Dto info = (BaseDto) bizService.queryForDto(dto.getAsString("t") + "." + sql, dto);


            ////检查有没有 创建过又没有使用过的组
            Dto infoinvest_quiz_user_group = new BaseDto();
            infoinvest_quiz_user_group.put("CreateUserID", dto.getAsString("visitUserID"));
            infoinvest_quiz_user_group.put("quizID", dto.getAsString("id"));
            Dto resultinfoinvest_quiz_user_group = (BaseDto) bizService.queryForDto("invest_quiz_user_group.queryCreateNotUser", infoinvest_quiz_user_group);

            //resultinfoinvest_quiz_user_group.getAsInteger("exsitGroupCount");
            if (resultinfoinvest_quiz_user_group != null && resultinfoinvest_quiz_user_group.containsKey("exsitGroupCount") && resultinfoinvest_quiz_user_group.getAsInteger("exsitGroupCount") == 0) {
                info.put("exsitGroupCount", resultinfoinvest_quiz_user_group.getAsInteger("exsitGroupCount"));
                info.put("GroupTitle", resultinfoinvest_quiz_user_group.getAsString("GroupTitle"));
                info.put("groupid", resultinfoinvest_quiz_user_group.getAsInteger("id"));
                info.put("privateInfo", resultinfoinvest_quiz_user_group.getAsString("privateInfo"));
            }
            result = JsonResult.ok(info);
            //result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/loadTestRecordAllOrOnly")
    public JsonResult<?> loadTestRecordAllOrOnly(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {

            Dto Dtoinvest_quiz_user_group = new BaseDto();
            Dtoinvest_quiz_user_group.put("id", dto.getAsString("groupID"));
            Dto info = (BaseDto) bizService.queryForDto("invest_quiz_user_group.getInfo", Dtoinvest_quiz_user_group);
            String strprivateInfo = info.getAsString("privateInfo");


            if (strprivateInfo.equals("All")){
                dto.remove("investUserID");////这个组的可以互相看  remove limit
                List paramList = bizService.queryForList("invest_quiz_user.queryList", dto);
                result = JsonResult.ok(paramList);
            }
            else   if (strprivateInfo.equals("Only")){
                if (info.getAsInteger("CreateUserID")==dto.getAsInteger("investUserID")){
                    dto.remove("investUserID");////这个组的可以互相看   组长看所有的
                    List paramList = bizService.queryForList("invest_quiz_user.queryList", dto);
                    result = JsonResult.ok(paramList);
                }
                else{
                    ///表明正常执行限制条件investUserID 只看自己的
                    List paramList = bizService.queryForList("invest_quiz_user.queryList", dto);
                    result = JsonResult.ok(paramList);
                }
            }


            //result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }
}