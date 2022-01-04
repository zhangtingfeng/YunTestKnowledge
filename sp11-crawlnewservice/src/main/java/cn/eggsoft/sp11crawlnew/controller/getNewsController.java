package cn.eggsoft.sp11crawlnew.controller;


import cn.eggsoft.sp11crawlnew.base.BaseController;
import cn.eggsoft.sp11crawlnew.myuploadProperties;
import cn.tedu.sp01.util.*;

import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/getNews")
public class getNewsController extends BaseController {
    @Autowired
    private myuploadProperties fouploadPropertieso;

    @ResponseBody
    @PostMapping(value = "/download365News")
    public JsonResult<?> download365News() throws IOException, ParseException {
        JsonResult<?> result = new JsonResult<>();

        int intWeek=getWeek(new Date());
        String strPath=fouploadPropertieso.getNetRootpath()+File.separator+fouploadPropertieso.getWeeksPic_url()+ File.separator+intWeek;
        ArrayList<String> ddPubFilesdd= FileOperate.getPubFiles(strPath);
        int ran2 = (int) (Math.random()*(ddPubFilesdd.size()-0)+0);

        try {

            mongoServiceImpl.savelog("sp11-crawlnew", "download365News_Start", "download365News");




            ArrayList<String> numbers = new ArrayList<>();

            String strcreditcontent = CommonUtil.HttpClientGetSSL("https://www.163.com/dy/media/T1603594732083.html", null);

            // 使用Jsoup解析网页内容
            Document document = Jsoup.parse(strcreditcontent);
            // 获取文档标题
            String title = document.title();
            System.out.println(title);

            //check if today
            Element mastheadDay = document.select("p.media_article_date").first();
            String strTitleDay = mastheadDay.text();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTitleDay = null;
            // 注意格式需要与上面一致，不然会出现异常
            dateTitleDay = sdf.parse(strTitleDay);
            long diff = (new Date()).getTime() - dateTitleDay.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days == 0) {
                Element masthead = document.select("a.media_article_img").first();
                String strHref = masthead.attr("href");//通过属性名字来获取
                strcreditcontent = CommonUtil.HttpClientGetSSL(strHref, null);
                document = Jsoup.parse(strcreditcontent);

                //Element masthead = document.select("a.media_article_img").first();

                Element mastheadpost_body = document.select("div.post_body").first();

                Element mastheadDayNew=mastheadpost_body.select("p").get(1);

                String strDayNew = mastheadDayNew.html();
                String[] strList = strDayNew.split("<br>");
                if (strList.length<15){
                    mastheadDayNew=mastheadpost_body.select("p").get(2);
                    strDayNew = mastheadDayNew.html();
                    strList = strDayNew.split("<br>");
                    if (strList.length<15){
                        mastheadDayNew=mastheadpost_body.select("p").get(3);
                        strDayNew = mastheadDayNew.html();
                        strList = strDayNew.split("<br>");
                        if (strList.length<15){
                            mastheadDayNew=mastheadpost_body.select("p").get(0);
                            strDayNew = mastheadDayNew.html();
                            strList = strDayNew.split("<br>");
                        }
                    }
                }
                ArrayList<String> ddddArrayList = new ArrayList<String>();
                for (int i = 2; i < strList.length - 1; i++) {
                    if (strList[i].contains("【微语】") || strList[i].contains("来源：")) continue;;
                    String[] strEachList = strList[i].split("、");
                    String strAdd = "";
                    for (int j = 1; j < strEachList.length; j++) {
                        strAdd = strAdd.concat(strEachList[j]).concat("、");
                    }
                    String strAddList = strAdd.substring(0, strAdd.length() - 2);
                    ddddArrayList.add(strAddList);
                }

                ArrayList<Dto> numbersDtoList = new ArrayList<>();
                Collections.shuffle(ddddArrayList);
                for (int i = 0; i < ddddArrayList.size(); i++) {
                    Dto curDto=new BaseDto();
                    curDto.put("title",ddddArrayList.get(i));
                    numbersDtoList.add(curDto);
                }


                Element masimg=mastheadpost_body.select("p.f_center").get(2);
                Element img=masimg.select("img[src]").first();
                String src = img.attr("src");//获取到src的值
                String strPicPath=saveURL(src);









                Dto ResultDto=new BaseDto();
                ResultDto.put("bulletinNewsList",JsonUtil.serialize(numbersDtoList));
                ResultDto.put("NewsWeeksPicUrl",ddPubFilesdd.get(ran2).replace("\\","/").replace(fouploadPropertieso.getNetRootpath(),fouploadPropertieso.getNetUrl()));
             //  ResultDto.put("NewsAdsPicUrl",strPicPath);
                ResultDto.put("NewsAdsPicUrl",fouploadPropertieso.getNetUrl()+"/"+fouploadPropertieso.getRawlMorning_url()+"/"+strPicPath);

             //   result = JsonResult.ok(ResultDto);


               // Dto ResultDtoObj=new BaseDto();
              //  ResultDtoObj.put("data",JsonUtil.serialize(ResultDto));
                mongoServiceImpl.savelog("sp11-crawlnew", "download365News", ResultDto);

                result = JsonResult.ok(ResultDto);
            } else {
                mongoServiceImpl.savelog("downloadExcel", "download365News", "No this time");

                Dto ResultDto=new BaseDto();
                ResultDto.put("NewsWeeksPicUrl",ddPubFilesdd.get(ran2).replace("\\","/").replace(fouploadPropertieso.getNetRootpath(),fouploadPropertieso.getNetUrl()));
                result = JsonResult.ok(ResultDto).code(1201);
               // result = JsonResult.err("No this time");
            }


            /*
            getElementById(String id)：通过id来获取
　　getElementsByTag(String tagName)：通过标签名字来获取
　　getElementsByClass(String className)：通过类名来获取
　　getElementsByAttribute(String key)：通过属性名字来获取
　　getElementsByAttributeValue(String key, String value)：通过指定的属性名字，属性值来获取
　　getAllElements()：获取所有元素

File input = new File("/tmp/input.html");
Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

Elements links = doc.select("a[href]"); //带有href属性的a元素
Elements pngs = doc.select("img[src$=.png]");
  //扩展名为.png的图片

Element masthead = doc.select("div.masthead").first();
  //class等于masthead的div标签

Elements resultLinks = doc.select("h3.r > a"); //在h3元素之后的a元素


◇通过类似于css或jQuery的选择器来查找元素

使用的是Element类的下记方法：

public Elements select(String cssQuery)

作者：数据萌新
链接：https://www.jianshu.com/p/fd5caaaa950d
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


            // 创建HttpClient对象
            HttpClient hClient = new DefaultHttpClient();
            CloseableHttpClient client = HttpClients.createDefault();

            // 设置响应时间，设置传智源码时间，设置代理服务器(不使用本机的IP爬取，以防止被服务器识别从而IP加入黑名单)
            hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000)
                    .setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

            // 爬虫URL大部分都是get请求，创建get请求对象
            HttpGet hget = new HttpGet("https://www.163.com/dy/media/T1603594732083.html");
            // 向传智播客官方网站发送请求，获取网页源码
            HttpResponse response = hClient.execute(hget);
            // EntityUtils工具类把网页实体转换成字符串
            String content = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(content);





            Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
            //List<Dto> readResultList = bizService.queryForList("goods_imports.queryList", dto);

            Dto returndto =new BaseDto();
            returndto.put("downexcel",dto);*/


        } catch (Exception e) {
           String strErr= mongoServiceImpl.savelog("downloadExcel", "Exception=", e);
            e.printStackTrace();
          //  result = JsonResult.err(strErr);

            Dto ResultDto=new BaseDto();
            ResultDto.put("NewsWeeksPicUrl",ddPubFilesdd.get(ran2).replace("\\","/").replace(fouploadPropertieso.getNetRootpath(),fouploadPropertieso.getNetUrl()));
            result = JsonResult.ok(ResultDto).code(1202);


        }
        return result;
    }


    private int getWeek(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }
    private String getWeeksURL(String url) throws Exception {
        // url="https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1031%2Fd6f900bbj00r1tbnf0025d000u001hcp.jpg&thumbnail=650x2147483647&quality=80&type=jpg";
        URL url1 = new URL(url);
        if("https".equalsIgnoreCase(url1.getProtocol())){
            SslUtils.ignoreSsl();
        }
        URLConnection uc = url1.openConnection();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss-SSS");
        String date = sdf.format(new Date());

        InputStream inputStream = uc.getInputStream();

        String strJpg=".jpg";
        if (url.contains("jpg") || url.contains("jpeg"))
        {
        }
        else if (url.contains("png"))
        {
            strJpg=".png";
        }
        FileOutputStream out = new FileOutputStream(fouploadPropertieso.getNetRootpath()+"/"+fouploadPropertieso.getRawlMorning_url()+"/"+date+strJpg);
        int j = 0;
        while ((j = inputStream.read()) != -1) {
            out.write(j);
        }
        inputStream.close();
        return  date+strJpg;
    }
    private String saveURL(String url) throws Exception {
        //url="https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1031%2Fd6f900bbj00r1tbnf0025d000u001hcp.jpg&thumbnail=650x2147483647&quality=80&type=jpg";
        URL url1 = new URL(url);
        if("https".equalsIgnoreCase(url1.getProtocol())){
            SslUtils.ignoreSsl();
        }
        URLConnection uc = url1.openConnection();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss-SSS");
        String date = sdf.format(new Date());

        InputStream inputStream = uc.getInputStream();

        String strJpg=".jpg";
        if (url.contains("jpg") || url.contains("jpeg"))
        {
        }
        else if (url.contains("png"))
        {
            strJpg=".png";
        }
        FileOutputStream out = new FileOutputStream(fouploadPropertieso.getNetRootpath()+"/"+fouploadPropertieso.getRawlMorning_url()+"/"+date+strJpg);
        int j = 0;
        while ((j = inputStream.read()) != -1) {
            out.write(j);
        }
        inputStream.close();
        return  date+strJpg;
    }
/* Java随机打乱(shuffle)数组
    public <T> void swap(T[] a, int i, int j){
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static Random rand = new Random();
    public <T> void shuffle(T[] arr) {
        int length = arr.length;
        for ( int i = length; i > 0; i-- ){
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }*/
}
