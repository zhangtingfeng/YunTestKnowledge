package cn.tedu.sp24.base.config;


import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;


@Service
public class MongoServiceImpl {
    @Autowired
    protected RestTemplate templateEureka;


    /**
     * 发送post请求
     *
     * @param Logs_Subject    路径
     * @param Logs_SubSubject 参数(json类型)
     * @param Logs_Content    编码格式
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public JSONObject savelog(String Logs_Subject, String Logs_SubSubject, String Logs_Content) throws ParseException, IOException, JSONException {

        JSONObject jsonObject = new JSONObject();

        JSONObject jsondataObject = new JSONObject();


        //string
        jsondataObject.put("Title", Logs_Subject);
        //int
        jsondataObject.put("Subtitle", Logs_SubSubject);
        //boolean
        jsondataObject.put("Content", Logs_Content);

        jsonObject.put("data", jsondataObject);

        savelogAction(jsonObject);
        return jsonObject;

        //return body;
    }

    public JSONObject savelog(String Logs_Subject, String Logs_SubSubject, Object Logs_Content) throws ParseException, IOException, JSONException {
        // 先将java对象转换为json对象，在将json对象转换为json字符串
        //JSONObject json = JSONObject.toJSON(Logs_Content);//将java对象转换为json对象
        //String str = json.toString();//将json对象转换为字符串
        //net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        String jpushObjec = JsonUtil.serialize(Logs_Content);

        //String jsonString = com.alibaba.fastjson.JSON.toJSONString(Logs_Content);
        return savelog(Logs_Subject, Logs_SubSubject, jpushObjec);
        // return jpushObjec;
    }


    private void savelogAction(Object ObjectITEM) {
        ThreadSaveLog T1 = new ThreadSaveLog("Thread-1", ObjectITEM,templateEureka);
        T1.start();


    }

}


class ThreadSaveLog extends Thread {
    private Thread t;
    private String threadName;
    private Object objectITEM;
    private RestTemplate templateEureka;


    ThreadSaveLog(String name, Object ObjectITEM,RestTemplate argtemplateEureka) {
        threadName = name;
        objectITEM = ObjectITEM;
        templateEureka=argtemplateEureka;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);
        templateEureka.postForObject(String.format("http://%s/%s/MongoLog/save", "mongodb-service", "mongodb-service"), (objectITEM), JsonResult.class);

        System.out.println("Thread " + threadName + " exiting.");
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