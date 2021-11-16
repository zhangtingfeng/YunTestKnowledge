package cn.eggsoft.sp10.controller;

import cn.eggsoft.sp10.tuoke.LogContent;
import cn.tedu.sp01.util.CommonUtil;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

///http://localhost:18039/Mongodb-service/Hello/save

@RestController
@RequestMapping("/MongoHello")
public class MongoHelloController {




    @RequestMapping("/save")
    public void save() throws IOException, ParseException {
        List<LogContent> books = new ArrayList<LogContent>();
        Date MongoDate=CommonUtil.getMongoDate(new Date());
        LogContent b1 = new LogContent();
        b1.setTitle("克苏鲁神话");
        b1.setSubtitle("H.P.洛夫克拉夫特 ");
        b1.setUpdatetime(MongoDate);
        books.add(b1);

        LogContent b2 = new LogContent();
        b2.setTitle("克苏鲁神话");
        b2.setSubtitle("H.P.洛夫克拉夫特 ");
        b2.setUpdatetime(MongoDate);
        books.add(b2);

        Dto retDto = new BaseDto();
        retDto.put("Data",books);
        debugLogUtil.send("queryPage", "程序queryPage报错",books );
       // bookDao.insert(books);
        return;
    }




    @RequestMapping("/search")
    public void search(){
        //List<LogContent> books = bookDao.findBytitleLike("克");


       // System.out.println("作者名包含'克'字的书籍：" + books);

    }
}
