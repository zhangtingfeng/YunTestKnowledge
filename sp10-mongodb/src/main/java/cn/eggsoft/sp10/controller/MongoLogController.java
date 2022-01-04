package cn.eggsoft.sp10.controller;

import cn.eggsoft.sp10.base.BaseController;
import cn.eggsoft.sp10.tuoke.LogContent;
import cn.eggsoft.sp10.tuoke.MongoDbService;
import cn.eggsoft.sp10.tuoke.PageHelper;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;

import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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

            //List<LogContent> books = new ArrayList<LogContent>();
            //Date MongoDate = CommonUtil.getMongoDate(new Date());
            Date MongoDate = new Date();
            LogContent b1 = new LogContent();
            b1.setTitle(Title);
            b1.setSubtitle(Subtitle);
            b1.setContent(Content);
            b1.setUpdatetime(MongoDate);
            ///books.add(b1);

            ThreadSave T1 = new ThreadSave("Thread-1", mongoDbService, b1);
            T1.start();

            //bookLogDao.insert(books);
            return JsonResult.ok("查询queryPage").data(b1);
        } catch (Exception e) {
            //String strErr = mongoServiceImpl.savelog(("queryPage", "程序queryPage报错", e);
            //log.info(strErr);
            e.printStackTrace();
            return JsonResult.err("Error");
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
            //avelog(debugLogUtil.GetNeedsendOBJ(("mongosearch", "search", JsonResultbase64));

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

            LogContent argLogContent = new LogContent();
            argLogContent.setContent(Content);
            argLogContent.setTitle(Title);
            argLogContent.setSubtitle(Subtitle);
            argLogContent.setStartTime(startTime);
            argLogContent.setEndTime(endTime);


            int limit = dto.getAsInteger("limit");
            int start = dto.getAsInteger("start");


            /*

            db.createUser(
     {
       user:"admin1",
       pwd:"a123qwe",
       roles:[{role:"readWrite",db:"testdb1"}]
     }
  )
             */

            PageHelper list2 = mongoDbService.getMongoBySearch(argLogContent, start, limit);
            //计算总数

            retDto.put("rows", list2.getList());
            retDto.put("total", list2.getTotal());
            retDto.put("getPageSize", list2.getList().size());
            // System.out.println("作者名包含'克'字的书籍：" + books);
            return JsonResult.ok("查询queryPage").data(retDto);
        } catch (Exception e) {

            //mongoServiceImpl.savelog(("search", "程序MongoLog报错", errrr));
            //log.info(strErr);
            e.printStackTrace();
            return JsonResult.err(e.getLocalizedMessage());
        }
    }


}


class ThreadSave extends Thread {
    private Thread t;
    private String threadName;
    private MongoDbService pmongoDbService;
    private LogContent pb1;


    ThreadSave(String name, MongoDbService argmongoDbService, LogContent argb1) {
        threadName = name;
        pmongoDbService = argmongoDbService;
        pb1 = argb1;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        try {

            System.out.println("Running " + threadName);
            pmongoDbService.save(pb1);
            System.out.println("Thread " + threadName + " exiting.");
        } catch (Exception e) {

            //mongoServiceImpl.savelog(("search", "程序MongoLog报错", errrr));
            //log.info(strErr);
            e.printStackTrace();

        }
    }
/*
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }*/
}