package cn.eggsoft.sp11crawlnew.controller;

import cn.eggsoft.sp11crawlnew.base.BaseController;
import cn.tedu.sp01.util.CommonUtil;
import cn.tedu.sp01.util.JsonResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
public class TimeNewsScheduleConfig extends BaseController {
    //3.添加定时任务  https://cron.qqe2.com/
    @Scheduled(cron = "0 0 0/4 * * ?")
    public void configureTasks() throws Exception {
      // new DayNewsController().SaveDiskSpGet(null);
       // String Strlunar = "http://127.0.0.1:48024/teacher-service/dayNews/SaveDiskSpGet";
       // CommonUtil.HttpClientGet(Strlunar);

        JsonResult<?> jsonResult =templateEureka.getForObject(String.format("http://%s/%s/dayNews/SaveDiskSpGet", "teacher-service", "teacher-service"),  JsonResult.class);

        mongoServiceImpl.savelog("edu","执行定时任务 0 0 0/1 * * ?",jsonResult);

        System.err.println("执行定时任务1: " + LocalDateTime.now());
    }

///"0 15 10 ? * *" 每天上午6:55触发
    @Scheduled(cron = "0 35 7 ? * *")
    public void configureTasks17() throws Exception {
        // new DayNewsController().SaveDiskSpGet(null);
       // String Strlunar = "http://127.0.0.1:48024/teacher-service/dayNews/SpGet";
       // CommonUtil.HttpClientGet(Strlunar);

        JsonResult<?> jsonResult =templateEureka.getForObject(String.format("http://%s/%s/dayNews/SpGet", "teacher-service", "teacher-service"),  JsonResult.class);
        mongoServiceImpl.savelog("edu","执行定时任务2 0 12 22 ? * *",jsonResult);
        System.err.println("执行定时任务2: " + LocalDateTime.now());
    }
}