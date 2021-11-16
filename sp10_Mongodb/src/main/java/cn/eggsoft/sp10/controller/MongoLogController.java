package cn.eggsoft.sp10.controller;

import cn.eggsoft.sp10.base.BaseController;
import cn.eggsoft.sp10.tuoke.LogContent;
import cn.eggsoft.sp10.tuoke.MongoDbService;
import cn.eggsoft.sp10.tuoke.PageHelper;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;
import cn.tedu.sp01.util.debugLogUtil;

import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
///http://localhost:18039/Mongodb-service/Hello/save

@RestController
@RequestMapping("/MongoLog")
public class MongoLogController extends BaseController {


    @Autowired
    private MongoDbService mongoDbService;


    /*
    *http://localhost:18039/Mongodb-service/MongoLog/save
    * {
    "loginChannel": 401,
        "token": "11111111",
        "loginChannel": null,
        "sign": "null",
        "data": {
            "Title": "invest_quiz",
            "Subtitle":"queryList",
            "Content":1,
            "start":2,
            "end":30
}
}
    * */
    @ResponseBody
    @PostMapping("/save")
    public JsonResult<?> save(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        try {
            String strgetData = JsonUtil.serialize(JsonResultbase64.getData());
            String strtoken = JsonResultbase64.getToken();
            Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
            Dto retDto = new BaseDto();

            String Title = dto.getAsString("Title");
            String Subtitle = dto.getAsString("Subtitle");
            String Content = dto.getAsString("Content");

            List<LogContent> books = new ArrayList<LogContent>();
            //Date MongoDate = CommonUtil.getMongoDate(new Date());
            Date MongoDate = new Date();
            LogContent b1 = new LogContent();
            b1.setTitle(Title);
            b1.setSubtitle(Subtitle);
            b1.setContent(Content);
            b1.setUpdatetime(MongoDate);
            books.add(b1);

            mongoDbService.save(b1);

/*
            LogContent b2 = new LogContent();
            b2.setTitle("克苏鲁神话");
            b2.setSubtitle("H.P.洛夫克拉夫特 ");
            b2.setUpdatetime(MongoDate);
            books.add(b2);
*/
            //bookLogDao.insert(books);
            return JsonResult.ok("查询queryPage").data(books);
        } catch (Exception e) {
            //String strErr = debugLogUtil.send("queryPage", "程序queryPage报错", e);
            //log.info(strErr);
            e.printStackTrace();
            return JsonResult.err(e.getLocalizedMessage());
        }
    }


/*
http://localhost:18039/Mongodb-service/MongoLog/search

{
    "timestamp": 1629702980826,
    "status": 405,
    "error": "Method Not Allowed",
    "message": "",
    "data": {
        "Title":"ribbo",
        "Subtitle":"",
        "content":"",
        "LessThanTime":"1970-01-20 04:41:53",
        "GreaterThanTime":"2970-01-20 04:41:53",
        "limit":30,
        "start":2

    }
}el

*
* */

    @ResponseBody
    @PostMapping("/search")
    public JsonResult<?> search(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        try {
            debugLogUtil.send("mongosearch", "search", JsonResultbase64);

            String strgetData = JsonUtil.serialize(JsonResultbase64.getData());
            String strtoken = JsonResultbase64.getToken();
            Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
            Dto retDto = new BaseDto();

            String Title = dto.getAsString("Title");
            String Subtitle = dto.getAsString("Subtitle");
            String Content = dto.getAsString("Content");
            //Date TimestampstartTime=dto.getAsTimestamp("startTime");
            Date startTime = dto.getAsDate("startTime");
            Date endTime = dto.getAsDate("endTime");

            LogContent argLogContent=new LogContent();
            argLogContent.setContent(Content);
            argLogContent.setTitle(Title);
            argLogContent.setSubtitle(Subtitle);
            argLogContent.setStartTime(startTime);
            argLogContent.setEndTime(endTime);


            int limit = dto.getAsInteger("limit");
            int start = dto.getAsInteger("start");


            PageHelper list2= mongoDbService.getMongoBySearch(argLogContent,start,limit);
            //计算总数

            retDto.put("rows",list2.getList());
            retDto.put("total",list2.getTotal());
            retDto.put("getPageSize",list2.getList().size());
           // System.out.println("作者名包含'克'字的书籍：" + books);
            return JsonResult.ok("查询queryPage").data(retDto);
        } catch (Exception e) {
            String errrr=e.toString();
            debugLogUtil.send("search", "程序MongoLog报错", errrr);
            //log.info(strErr);
            //e.printStackTrace();
            return JsonResult.err(errrr);
        }
    }
}
