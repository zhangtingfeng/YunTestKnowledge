package cn.tedu.sp01.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.FutureCallback;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;


public class debugLogUtil {
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
    public static void send(String Logs_Subject, String Logs_SubSubject, String Logs_Content) throws ParseException, IOException, JSONException {
        String url = "http://productlog.eggsoft.cn/Api/LogsEveryDay/Write_Log";
        JSONObject jsonObject = new JSONObject();
        //string
        jsonObject.put("Logs_Subject", Logs_Subject);
        //int
        jsonObject.put("Logs_SubSubject", Logs_SubSubject);
        //boolean
        jsonObject.put("Logs_Content", Logs_Content);


        String encoding = "utf-8";

        String body = "";

        //创建httpclient对象
        final CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        final HttpPost httpPost = new HttpPost(url);

        //装填参数
        StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
        System.out.println("请求地址：" + url);
//        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

       /* //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost, (HttpContext) new Back());
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
*/
        ///使用原生的CompletableFuture实现异步操作，加上对lambda的支持，可以说实现异步任务已经发挥到了极致。
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println("===task start===");
                try {
                    CloseableHttpResponse response = client.execute(httpPost);
                    //释放链接
                    response.close();
                    //Thread.sleep(5000);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("===task finish===");
                return 3;
            }
        }, executor);
        //future.thenAccept(e -> System.out.println(e));
        //System.out.println("main函数执行结束");


        //return body;
    }

    public static String send(String Logs_Subject, String Logs_SubSubject, Object Logs_Content) throws ParseException, IOException, JSONException {
        // 先将java对象转换为json对象，在将json对象转换为json字符串
        //JSONObject json = JSONObject.toJSON(Logs_Content);//将java对象转换为json对象
        //String str = json.toString();//将json对象转换为字符串
        //net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        String jpushObjec = JsonUtil.toString(Logs_Content);

        //String jsonString = com.alibaba.fastjson.JSON.toJSONString(Logs_Content);
        send(Logs_Subject, Logs_SubSubject, jpushObjec);
        return jpushObjec;
    }


    static class Back implements FutureCallback<HttpResponse> {

        private long start = System.currentTimeMillis();

        Back() {
        }

        public void completed(HttpResponse httpResponse) {
            ;
            // System.out.println("cost is:" + (System.currentTimeMillis() - start) + ":" + EntityUtils.toString(httpResponse.getEntity()));
        }

        public void failed(Exception e) {
            e.printStackTrace();
            System.err.println(" cost is:" + (System.currentTimeMillis() - start) + ":" + e);
        }

        public void cancelled() {

        }

        @Override
        public void onSuccess(HttpResponse result) {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    }
}
