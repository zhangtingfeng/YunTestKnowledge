package cn.tedu.sp01.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author admin
 * @date 2017年7月16日23:40:24
 */

@Slf4j
public class CommonUtil {

    /**
     * 去除小数点后的0
     */
    public static String rvZeroAndDot(String s) {
        if (s.isEmpty()) {
            return null;
        }
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 返回当前时间与月底相差的毫秒
     */

    public static Long millisecondDifference() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        Date lastDay = cale.getTime();
        int i = differentDays(new Date(), lastDay);
        if (i == 0) {
            i = i + 1;
        }
//        Long  l = Long.valueOf(i);
        BigDecimal multiply = new BigDecimal(i).multiply(new BigDecimal(86400));
        return Long.valueOf(String.valueOf(multiply));
//        Long y = l*86400000L;
//        return y;
    }

    /**
     * 获取当前时间的时间戳
     */
    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis();
        return String.valueOf(time);
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    //闰年
                    timeDistance += 366;
                } else {
                    //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    public static String getCarrierNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        return "CY" + date + (int) ((Math.random() * 9.0D + 1.0D) * 10000.0D) + "0001";
    }


    // map生成xml
    public static String mapToxml(Map<String, String> map) {
        String xml = "<xml>";
        for (String item : map.keySet()) {
            xml += "<" + item + ">";
            xml += map.get(item);
            xml += "</" + item + ">";
        }
        xml += "</xml>";
        return xml;
    }

    /**
     * 统一下单设置签名的方式
     */
    public static Map<String, Object> setSign(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        // 参数名ASCII码从小到大排序（字典序）；
        if (null != map.get("appid")) {
            sb.append("appid=");
            sb.append(map.get("appid"));
        }
        if (null != map.get("attach")) {
            sb.append("&attach=");
            sb.append(map.get("attach"));
        }
        if (null != map.get("body")) {
            sb.append("&body=");
            sb.append(map.get("body"));
        }
        if (null != map.get("detail")) {
            sb.append("&detail=");
            sb.append(map.get("detail"));
        }
        if (null != map.get("device_info")) {
            sb.append("&device_info=");
            sb.append(map.get("device_info"));
        }
        if (null != map.get("mch_id")) {
            sb.append("&mch_id=");
            sb.append(map.get("mch_id"));
        }
        if (null != map.get("nonce_str")) {
            sb.append("&nonce_str=");
            sb.append(map.get("nonce_str"));
        }
        if (null != map.get("notify_url")) {
            sb.append("&notify_url=");
            sb.append(map.get("notify_url"));
        }
        if (null != map.get("openid")) {
            sb.append("&openid=");
            sb.append(map.get("openid"));
        }
        if (null != map.get("out_trade_no")) {
            sb.append("&out_trade_no=");
            sb.append(map.get("out_trade_no"));
        }
        if (null != map.get("spbill_create_ip")) {
            sb.append("&spbill_create_ip=");
            sb.append(map.get("spbill_create_ip"));
        }
        if (null != map.get("total_fee")) {
            sb.append("&total_fee=");
            sb.append(map.get("total_fee"));
        }
        if (null != map.get("trade_type")) {
            sb.append("&trade_type=");
            sb.append(map.get("trade_type"));
        }
        if (null != map.get("sub_mch_id")) {
            sb.append("&sub_mch_id=");
            sb.append(map.get("sub_mch_id"));
        }
        if (null != map.get("sub_openid")) {
            sb.append("&sub_openid=");
            sb.append(map.get("sub_openid"));
        }
        System.out.println("begin......" + sb.toString());
        String sign = Md5Util.parseStrToMd5U32(sb.toString()).toUpperCase();
        map.put("sign", sign);
        System.out.println("end........" + sign);
        return map;
    }

    /**
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<
     *
     * @param paramsMap  要排序的Map对象
     * @param urlEncode  是否需要URLENCODE
     * @param keyToLower 是否需要将Key转换为全小写 true:key转化成小写，false:不转化
     * @return
     */
    public static Map<String, String> formatUrlMap(Map<String, String> paramsMap, boolean urlEncode, boolean keyToLower) {

        String buff = "";
        Map<String, String> tmpMap = paramsMap;

        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());

            //对所有传入参数按照字段名的ASCII码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            //构造URL 键值对的格式
            StringBuffer buf = new StringBuffer();
            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotEmpty(item.getKey())) {
                    String key = item.getKey();
                    String value = item.getValue();
                    buf.append(key + "=" + value);
                    buf.append("&");
                }
            }
            buff = buf.toString() + "key=addsflijtssaerexwersdscfsewsdssx";
            System.out.println(buff);
            String sign = Md5Util.parseStrToMd5U32(buff.toString()).toUpperCase();
            tmpMap.put("sign", sign);
            System.out.println("end........" + sign);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return tmpMap;
    }

    public static Map<String, String> formatUrlMap2(Map<String, String> paramsMap, boolean urlEncode, boolean keyToLower) {

        String buff = "";
        Map<String, String> tmpMap = paramsMap;

        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());

            //对所有传入参数按照字段名的ASCII码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            //构造URL 键值对的格式
            StringBuffer buf = new StringBuffer();
            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotEmpty(item.getKey())) {
                    String key = item.getKey();
                    String value = item.getValue();
                    buf.append(key + "=" + value);
                    buf.append("&");
                }
            }
            buff = buf.toString() + "key=addsflijtssaerexwersdscfsewsdssx";
            System.out.println(buff);
            String sign = Md5Util.parseStrToMd5U32(buff.toString()).toUpperCase();
            tmpMap.put("paySign", sign);
            System.out.println("end........" + sign);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return tmpMap;
    }


    /**
     * 统一下单设置签名的方式
     */
    public static Map<String, Object> OpenSign(Map<String, Object> reqBean) {
        StringBuilder sb = new StringBuilder();
        // 参数名ASCII码从小到大排序（字典序）；
        if (null != reqBean.get("appid")) {
            sb.append("appid=");
            sb.append(reqBean.get("appid"));
        }
        if (null != reqBean.get("timeStamp")) {
            sb.append("timeStamp=");
            sb.append(reqBean.get("timeStamp"));
        }
        if (null != reqBean.get("nonceStr")) {
            sb.append("nonceStr=");
            sb.append(reqBean.get("nonceStr"));
        }
        if (null != reqBean.get("package")) {
            sb.append("package=");
            sb.append(reqBean.get("package"));
        }
        if (null != reqBean.get("signType")) {
            sb.append("signType=");
            sb.append(reqBean.get("signType"));
        }
        System.out.println("begin......" + sb.toString());
        String sign = Md5Util.parseStrToMd5U32(sb.toString()).toUpperCase();
        reqBean.put("paySign", sign);
        System.out.println("end........" + sign);
        return reqBean;
    }


    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }


    /**
     * 加密微信手机号
     *
     * @param sSrc
     * @param encodingFormat
     * @param sKey
     * @param ivParameter
     * @return
     * @throws Exception
     */
    public static String decryptS5(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] raw = decoder.decodeBuffer(sKey);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(decoder.decodeBuffer(ivParameter));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] myendicod = decoder.decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(myendicod);
            String originalString = new String(original, encodingFormat);
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    /*
    ///https://blog.csdn.net/qq_42561919/article/details/100065675
        使用HttpClient发送Https请求时，出现异常为：
        PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
        出现这个异常的原因为，客户端向服务器发送https请求时，会验证服务端的证书状态，可以设置信任所有证书，绕过这一步。
        查看HttpClient官方文档，示例如下：
                ————————————————
        版权声明：本文为CSDN博主「有错误先debug」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/qq_42561919/article/details/100065675
        */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            //使用 loadTrustMaterial() 方法实现一个信任策略，信任所有证书
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            //NoopHostnameVerifier类:  作为主机名验证工具，实质上关闭了主机名验证，它接受任何
            //有效的SSL会话并匹配到目标主机。
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();

    }

    //get请求            ArrayList<String> ddd=new ArrayList<String>();
    //            ddd.add("1");
    //            ddd.add("2");
    public static String HttpClientGetSSL(String url,ArrayList<String> DtoHeaderList) throws Exception {
        // 获取http客户端可以直接使用SSLContext来构建实例，代码如下：
        CloseableHttpClient client = createSSLClientDefault();//HttpClients.createDefault();


        ///trustAllHttpsCertificates();
        //HttpsURLConnection.setDefaultHostnameVerifier(hv);
        // 通过httpget方式来实现我们的get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("content-type", "application/json");
        if (DtoHeaderList!=null){
            DtoHeaderList.forEach((value) -> {
                String[] strValueList=value.split("###");
                //String strValue=value;

                httpGet.addHeader(strValueList[0],strValueList[1]);
            });
        }
        // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了
        CloseableHttpResponse Response = client.execute(httpGet);
        // 所有的响应的数据，也全部都是封装在HttpEntity里面
        HttpEntity entity = Response.getEntity();
        // 通过EntityUtils 来将我们的数据转换成字符串
        String str = EntityUtils.toString(entity, "UTF-8");
        // EntityUtils.toString(entity)
        //System.out.println(str);
        // 关闭
        Response.close();
        return str;
    }

    //delete
    public static String HttpClientDeleteSSL(String url) throws Exception {
        // 获取http客户端可以直接使用SSLContext来构建实例，代码如下：
        CloseableHttpClient client = createSSLClientDefault();//HttpClients.createDefault();


        ///trustAllHttpsCertificates();
        //HttpsURLConnection.setDefaultHostnameVerifier(hv);
        // 通过httpget方式来实现我们的get请求
        HttpDelete httpDelete = new HttpDelete(url);
        // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了
        CloseableHttpResponse Response = client.execute(httpDelete);
        // 所有的响应的数据，也全部都是封装在HttpEntity里面
        HttpEntity entity = Response.getEntity();
        // 通过EntityUtils 来将我们的数据转换成字符串
        String str = EntityUtils.toString(entity, "UTF-8");
        // EntityUtils.toString(entity)
        //System.out.println(str);
        // 关闭
        Response.close();
        return str;
    }

    public static String HttpClientPostSSL(String url, String  strjsonObject) throws Exception {
        // 获取http客户端可以直接使用SSLContext来构建实例，代码如下：
        CloseableHttpClient client = createSSLClientDefault();//HttpClients.createDefault();

        //  final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(url);
        StringEntity s = new StringEntity(strjsonObject, "utf-8");
        s.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,                "application/json"));
        httpPost.setEntity(s);
        System.out.println("请求地址：" + url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String str = EntityUtils.toString(entity, "UTF-8");
        System.out.println(str);
        // 关闭
        response.close();

        return str;
    }


    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    //get请求
    public static String HttpClientGet(String url) throws Exception {
        // 获取http客户端
        CloseableHttpClient client = HttpClients.createDefault();
        // 通过httpget方式来实现我们的get请求
        HttpGet httpGet = new HttpGet(url);
        // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了
        CloseableHttpResponse Response = client.execute(httpGet);
        // 所有的响应的数据，也全部都是封装在HttpEntity里面
        HttpEntity entity = Response.getEntity();
        // 通过EntityUtils 来将我们的数据转换成字符串
        String str = EntityUtils.toString(entity, "UTF-8");
        // EntityUtils.toString(entity)
        System.out.println(str);
        // 关闭
        Response.close();
        return str;
    }

    //post请求
    public static String HttpClientPost(String url, String... args) throws Exception {
        // 获取默认的请求客户端
        CloseableHttpClient client = HttpClients.createDefault();
        // 通过HttpPost来发送post请求
        HttpPost httpPost = new HttpPost(url);
        /*
         * post带参数开始
         */
        // 第三步：构造list集合，往里面丢数据
        List<NameValuePair> list = new ArrayList<>();
        BasicNameValuePair basicNameValuePair = new BasicNameValuePair("command", args[0]);
        list.add(basicNameValuePair);
        // 第二步：我们发现Entity是一个接口，所以只能找实现类，发现实现类又需要一个集合，集合的泛型是NameValuePair类型
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
        // 第一步：通过setEntity 将我们的entity对象传递过去
        httpPost.setEntity(formEntity);
        /*
         * post带参数结束
         */
        // HttpEntity
        // 是一个中间的桥梁，在httpClient里面，是连接我们的请求与响应的一个中间桥梁，所有的请求参数都是通过HttpEntity携带过去的
        // 通过client来执行请求，获取一个响应结果
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String str = EntityUtils.toString(entity, "UTF-8");
        System.out.println(str);
        // 关闭
        response.close();

        return str;
    }

    public static String urlpost(String url, String strjsonObject, String Logs_Content) throws ParseException, IOException {

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost(url);
        StringEntity s = new StringEntity(strjsonObject, "utf-8");
        s.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
        httpPost.setEntity(s);
        System.out.println("请求地址：" + url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String str = EntityUtils.toString(entity, "UTF-8");
        System.out.println(str);
        // 关闭
        response.close();

        return str;
    }

    /**
     * 计算得到MongoDB存储的日期，（默认情况下mongo中存储的是标准的时间，中国时间是东八区，存在mongo中少8小时，所以增加8小时）
     * http://www.iteye.com/problems/88507
     *
     * @author: Gao Peng
     * @date: 2016年5月4日 上午9:26:23
     * @param: @param
     *             date
     * @param: @return
     * @return: Date
     */
    public static Date getMongoDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, 8);
        return ca.getTime();
    }
}
