package cn.tedu.sp24.controller.learnNews;


import cn.tedu.sp01.util.*;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import cn.tedu.sp24.base.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.*;


@RestController
@RequestMapping("/dayNews")
public class DayNewsController extends BaseController {
    private String StrAPIKey = "71a5a4c0fcd67418251b96540a5c9e81";

///https://www.tianapi.com/gethttp/117  好像不稳定啊
    /**
     http://localhost:48024/teacher-service/dayNews/SpGet
     {
     "code": 200,
     "msg": "查询queryPage",
     "loginChannel": null,
     "token": null,
     "sign": null,
     "data": {
     "id":6
     }
     }
     *
     * @param
     * @return
     */
    /**
     * 查询对象
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/SpGet")
    public JsonResult<?> SpGet() throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto paramReturn = new BaseDto();
        JsonResult<?> result = JsonResult.ok();
        try {
            //Dto infoinvest_visit_path = new BaseDto();
            //Dto paramReturn = new BaseDto();

            ///中国老黄历
            String Strlunar = "http://api.tianapi.com/txapi/lunar/index?key=" + StrAPIKey;
            String strlunar = CommonUtil.HttpClientGet(Strlunar);
            Dto DTOlunar = JSONUtil2.parseJSON2Dto(strlunar);
            List<Dto> Dtolunarnewslist = DTOlunar.getAsList("newslist");
            Dto dtolunarnews = JSONUtil2.parseJSON2Dto(JsonUtil.serialize(Dtolunarnewslist.get(0)));
            String strTitle = dtolunarnews.getAsString("gregoriandate") +//"2021-10-19",
                    "(" + getWeek(new Date()) + ")" +
                    dtolunarnews.getAsString("tiangandizhiyear") +//辛丑
                    dtolunarnews.getAsString("shengxiao") + "年" +//牛
                    dtolunarnews.getAsString("tiangandizhimonth") + "月" +//牛
                    dtolunarnews.getAsString("tiangandizhiday") + "日" +//牛
                    dtolunarnews.getAsString("lubarmonth") +///"九月",
                    dtolunarnews.getAsString("lunarday") +//"十四",
                    "  " + dtolunarnews.getAsString("lunar_festival") +//,
                    "  " + dtolunarnews.getAsString("festival");
            paramReturn.put("lunar", strTitle);
            paramReturn.put("sunar", dtolunarnews.getAsString("gregoriandate"));
            ///早安
            String Strzaoan = "http://api.tianapi.com/txapi/zaoan/index?key=" + StrAPIKey;
            String strzaoan = CommonUtil.HttpClientGet(Strzaoan);
            paramReturn.put("zaoan", strzaoan);

            //Dto DTOzaoan = myJSONUtil2.parseJSON2Dto(strzaoan);
            //List<Dto> Dtozaoannewslist = DTOzaoan.getAsList("newslist");


///朋友圈文案
            String StrpyqwenanURL = "http://api.tianapi.com/txapi/pyqwenan/index?key=" + StrAPIKey;
            String strpyqwenan = CommonUtil.HttpClientGet(StrpyqwenanURL);
            paramReturn.put("pyqwenan", strpyqwenan);

            //Dto DTOpyqwenan = myJSONUtil2.parseJSON2Dto(strpyqwenan);
            //List<Dto> Dtopyqwenannewslist = DTOpyqwenan.getAsList("newslist");
//经典台词
            String Strdialogue = "http://api.tianapi.com/txapi/dialogue/index?key=" + StrAPIKey;
            String strdialogue = CommonUtil.HttpClientGet(Strdialogue);
            paramReturn.put("dialogue", strdialogue);
            //Dto DTOdialogue = myJSONUtil2.parseJSON2Dto(strdialogue);
            //List<Dto> Dtodialoguenewslist = DTOdialogue.getAsList("newslist");
//古籍名句
            String Strgjmj = "http://api.tianapi.com/txapi/gjmj/index?key=" + StrAPIKey;
            String strgjmj = CommonUtil.HttpClientGet(Strgjmj);
            paramReturn.put("gjmj", strgjmj);
            //Dto DTOgjmj = myJSONUtil2.parseJSON2Dto(strgjmj);
            //List<Dto> Dtogjmjlist = DTOgjmj.getAsList("newslist");
//雷人笑话
            String Strjoke = "http://api.tianapi.com/txapi/joke/index?key=" + StrAPIKey;
            String strjoke = CommonUtil.HttpClientGet(Strjoke);
            paramReturn.put("joke", strjoke);
            ///Dto DTOjoke = myJSONUtil2.parseJSON2Dto(strjoke);
            //List<Dto> Dtojokenewslist = DTOjoke.getAsList("newslist");
///励志古言
            String Strlzmy = "http://api.tianapi.com/txapi/lzmy/index?key=" + StrAPIKey;
            String strlzmy = CommonUtil.HttpClientGet(Strlzmy);
            paramReturn.put("lzmy", strlzmy);
            //Dto DTOlzmy = myJSONUtil2.parseJSON2Dto(strlzmy);
            //List<Dto> Dtolzmynewslist = DTOlzmy.getAsList("newslist");


            //String strContent = strTitle + "/n";

            //Dto dtopyqwenan =myJSONUtil2.parseJSON2Dto(myJsonUtil.serialize(Dtopyqwenannewslist.get(0)));
            //strContent+=dtopyqwenan.getAsString("content")+"/n";
            //   strContent += "[读报时间]：/n";


            // Dto dtodialogue = myJSONUtil2.parseJSON2Dto(myJsonUtil.serialize(Dtodialoguenewslist.get(0)));
            // strContent += "[经典台词]：" + dtodialogue.getAsString("dialogue") + "/n";

            //         Dto dtojoke = myJSONUtil2.parseJSON2Dto(myJsonUtil.serialize(Dtojokenewslist.get(0)));
            //        strContent += "[笑话]：{" + dtojoke.getAsString("title") + "}" + dtojoke.getAsString("content") + "/n";

            //      Dto dtolzmy = myJSONUtil2.parseJSON2Dto(myJsonUtil.serialize(Dtolzmynewslist.get(0)));
            //     strContent += "[意林]：{" + dtolzmy.getAsString("saying") + "}" + dtolzmy.getAsString("transl") + "/n";
            //String Strdownload365News = "http://127.0.0.1:48016/sp11crawlnew-service/getNews/download365News";
            //String strdownload365Newsjoke = CommonUtil.HttpClientPostSSL(Strdownload365News,"{}");

            JsonResult<?> jsonResult = templateEureka.postForObject(String.format("http://%s/%s/getNews/download365News", "sp11crawlnew-service", "sp11crawlnew-service"), null, JsonResult.class);


           // Dto dtolzmy365News = JSONUtil2.parseJSON2Dto(strdownload365Newsjoke);
            if (200 == jsonResult.getCode()) {
               // Dto ddDtod = (Dto) jsonResult.getData();
                Dto ddDtod =webUtils.getParamAsDto(jsonResult.datetoMap());
                        //JSONUtil2.parseJSON2Dto( JsonUtil.serialize(dtolzmy365News.get("data")));
               //dtolzmy365News.get("data");

                paramReturn.put("bulletinNewsList", JSONUtil2.parseJSON2ListDto(ddDtod.getAsString("bulletinNewsList")));
                paramReturn.put("NewsAdsPicUrl", ddDtod.getAsString("NewsAdsPicUrl"));
                paramReturn.put("NewsWeeksPicUrl", ddDtod.getAsString("NewsWeeksPicUrl"));

            } else {
                Dto paramqueryList = new BaseDto();
                paramqueryList.put("Type", "bulletinNews");
                //paramqueryList.put("Attachsql","UNIX_TIMESTAMP(create_time) between unix_timestamp(CAST((CAST(SYSDATE()AS DATE) - INTERVAL 1 DAY)AS DATETIME)) and unix_timestamp()");
                // paramqueryList.put("Attachsql","UNIX_TIMESTAMP(create_time) between unix_timestamp(CAST((CAST(SYSDATE()AS DATE) + INTERVAL 6 HOUR-INTERVAL 1 DAY)AS DATETIME)) and unix_timestamp(CAST((CAST(SYSDATE()AS DATE) + INTERVAL 6 HOUR)AS DATETIME))");
                paramqueryList.put("Attachsql", "UNIX_TIMESTAMP(create_time) between unix_timestamp(now()-INTERVAL 24 HOUR) and unix_timestamp(now())");
                List paramList = bizService.queryForList("learn_resource.queryList", paramqueryList);
                if (1201 == jsonResult.getCode() || 1202 == jsonResult.getCode()) {
                    Dto ddDtod =webUtils.getParamAsDto(jsonResult.datetoMap());
                    paramReturn.put("NewsWeeksPicUrl", ddDtod.getAsString("NewsWeeksPicUrl"));

                }
                paramReturn.put("bulletinNewsList", paramList);
            }


            // param.put("Content", strbulletin);

            Dto saveDtoList = new BaseDto();
            saveDtoList.put("Title", strTitle);
            saveDtoList.put("Content", JsonUtil.serialize(paramReturn));
            bizService.saveInfo("learn_news.saveInfo", saveDtoList);

            mongoServiceImpl.savelog("sp24-teacherservice", "SpGet", paramReturn);

            result = JsonResult.ok(paramReturn);
            //result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }


    //根据日期取得星期几

    private String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    @ResponseBody
    @RequestMapping(value = "/SaveDiskSpGet")
    public JsonResult<?> SaveDiskSpGet() throws Exception {
        ///查看“每日简报”接口全部参数或接口文档

        JsonResult<?> result = JsonResult.ok();
        try {
            String StrbulletinURL = "http://api.tianapi.com/bulletin/index?key=" + StrAPIKey;
            String strbulletin = CommonUtil.HttpClientGet(StrbulletinURL);
            //Dto DTObulletin = myJSONUtil2.parseJSON2Dto(strbulletin);


            List<Dto> Dtobulletinnewslist = JSONUtil2.parseJSON2ListDto(JsonUtil.serialize(JSONUtil2.parseJSON2Dto(strbulletin).getAsList("newslist")));
            int nIndex = 0;
            for (Dto curDto : Dtobulletinnewslist) {
                nIndex++;


                String strTitle = curDto.getAsString("title");
                String strMtime = curDto.getAsString("mtime");


                Dto paramExsit = new BaseDto();
                paramExsit.put("KeyName", strTitle + strMtime);
                paramExsit.put("Type", "bulletinNews");
                Dto totalCount = (BaseDto) bizService.queryForDto("learn_resource.queryListCount", paramExsit);
                if (null != totalCount && totalCount.size() > 0 && totalCount.getAsInteger("total") > 0) {

                } else {
                    Dto param = new BaseDto();
                    param.put("tableName", "learn_resource");
                    param.put("KeyName", strTitle + strMtime);
                    param.put("Type", "bulletinNews");
                    param.put("Content", JsonUtil.serialize(curDto));
                    bizService.save(param);
                }


                //bizService.update(param);
                //strContent += nIndex + "\\" + curDto.getAsString("title") + ":" + curDto.getAsString("digest");
            }

            result = JsonResult.ok(Dtobulletinnewslist);
            //result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/geocoderV1/{JsonResultbase64}")
    public JsonResult<?> geocoderV1(@PathVariable String JsonResultbase64) throws Exception {
        ///查看“每日简报”接口全部参数或接口文档
        Dto dto = JSONUtil2.parseJSON2Dto(URLDecoder.decode(JsonResultbase64));
        //Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            String StrbulletinURL = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + dto.getAsString("longitude") + "," + dto.getAsString("latitude") + "&key=PFCBZ-FUOCF-ROCJ3-NB4UD-QDJRS-YSBN4&get_poi=1";
            String strbulletin = CommonUtil.HttpClientGet(StrbulletinURL);
            Dto dtolunarnews = JSONUtil2.parseJSON2Dto(strbulletin);

            result = JsonResult.ok(dtolunarnews);
            //result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/tianqi/{JsonResultbase64}")
    public JsonResult<?> tianqi(@PathVariable String JsonResultbase64) throws Exception {
        ///查看“每日简报”接口全部参数或接口文档
        Dto dto = JSONUtil2.parseJSON2Dto(URLDecoder.decode(JsonResultbase64));
        String strCity = java.net.URLDecoder.decode(dto.getAsString("city"), "UTF-8");
        //Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            String StrbulletinURL = "http://api.tianapi.com/tianqi/index?key=" + StrAPIKey + "&city=" + strCity;
            String strbulletin = CommonUtil.HttpClientGet(StrbulletinURL);

            Dto dtolunarnews = JSONUtil2.parseJSON2Dto(strbulletin);

            result = JsonResult.ok(dtolunarnews);
            //result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/tianqishiju/{JsonResultbase64}")
    public JsonResult<?> tianqishiju(@PathVariable String JsonResultbase64) throws Exception {
        ///查看“每日简报”接口全部参数或接口文档
        Dto dto = JSONUtil2.parseJSON2Dto(URLDecoder.decode(JsonResultbase64));
        String strweather = java.net.URLDecoder.decode(dto.getAsString("weather"), "UTF-8");

        ArrayList<String> sitestqtype = new ArrayList<String>();

        Dto dtotianqishiju = new BaseDto();
        dtotianqishiju.put("风", 1);
        dtotianqishiju.put("云", 2);
        dtotianqishiju.put("雨", 3);
        dtotianqishiju.put("雪", 4);
        dtotianqishiju.put("霜", 5);
        dtotianqishiju.put("露", 6);
        dtotianqishiju.put("雾", 7);
        dtotianqishiju.put("雷", 8);
        dtotianqishiju.put("晴", 9);
        dtotianqishiju.put("阴", 10);
        // JDK8的迭代方式 返回字段中tqtype表示适合的天气类型，1=风、2=云、3=雨、4=雪、5=霜、6=露 、7=雾、8=雷、9=晴、10=阴。
        dtotianqishiju.forEach((key, value) -> {
            if (strweather.contains(key.toString())) {
                sitestqtype.add(value.toString());
            }
            //System.out.println(key + "：" + value);
        });
        Random r = new Random();
        Integer intintNum = r.nextInt((sitestqtype.size() - 1 - 0) + 1) + 0;


        //Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            String StrbulletinURL = "http://api.tianapi.com/tianqishiju/index?key=" + StrAPIKey + "&tqtype=" + sitestqtype.get(intintNum);
            String strbulletin = CommonUtil.HttpClientGet(StrbulletinURL);
            Dto dtolunarnews = JSONUtil2.parseJSON2Dto(strbulletin);

            result = JsonResult.ok(dtolunarnews);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }

}
