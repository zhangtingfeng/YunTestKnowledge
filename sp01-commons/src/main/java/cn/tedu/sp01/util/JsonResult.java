package cn.tedu.sp01.util;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class JsonResult<T> {
    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 没有登录
     */
    public static final int NOT_LOGIN = 400;

    /**
     * 发生异常
     */
    public static final int EXCEPTION = 401;

    /**
     * 系统错误
     */
    public static final int SYS_ERROR = 402;

    /**
     * 参数错误
     */
    public static final int PARAMS_ERROR = 403;

    /**
     * 不支持或已经废弃
     */
    public static final int NOT_SUPPORTED = 410;

    /**
     * AuthCode错误
     */
    public static final int INVALID_AUTHCODE = 444;

    /**
     * 太频繁的调用
     */
    public static final int TOO_FREQUENT = 445;

    /**
     * 未知的错误
     */
    public static final int UNKNOWN_ERROR = 499;

    private int code;
    private String msg;
    private String loginChannel;
    private String token;
    private String sign;
    private T data;


    public static JsonResult build() {
        return new JsonResult();
    }

    public static JsonResult build(int code) {
        return new JsonResult().code(code);
    }

    public static JsonResult build(int code, String msg) {
        return new JsonResult<String>().code(code).msg(msg);
    }

    public static <T> JsonResult<T> build(int code, T data) {
        return new JsonResult<T>().code(code).data(data);
    }

    public static <T> JsonResult<T> build(int code, String msg, T data) {
        return new JsonResult<T>().code(code).msg(msg).data(data);
    }


    public JsonResult<T> code(int code) {
        this.code = code;
        return this;
    }

    public JsonResult<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public JsonResult<T> loginChannel(String loginChannel) {
        this.loginChannel = loginChannel;
        return this;
    }

    public JsonResult<T> token(String token) {
        this.token = token;
        return this;
    }

    public JsonResult<T> sign(String sign) {
        this.sign = sign;
        return this;
    }

    public JsonResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public Map datetoMap() {
        //创建Map对象
        Map<String, String> map = new HashMap<String, String>();


        if (this.data instanceof LinkedHashMap) {
            LinkedHashMap objMap = (LinkedHashMap) this.data;
            Iterator iterator = objMap.keySet().iterator();
            while (iterator.hasNext()) {
                Object objKey = iterator.next();
                Object objValue = objMap.get(objKey);
                map.put(objKey.toString(), objValue.toString());
            }
        }
        return map;

        /*
        if(this.data instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) this.data;
            for (Map.Entry<String, Object> entry: jsonObject.entrySet()) {
                Object o = entry.getValue();

                map.put(entry.getKey(),entry.getValue().toString());
               /* if(o instanceof String) {
                    System.out.println("key:" + entry.getKey() + "，value:" + entry.getValue());
                } else {
                   // jsonLoop(o);
                }
            }
        }*/
        /*
        if(this.data instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) this.data;
            for(int i = 0; i < jsonArray.size(); i ++) {
                jsonLoop(jsonArray.get(i));
            }
        }*/

        // return map;
    }

    public static JsonResult ok() {
        return build(SUCCESS);
    }

    public static JsonResult ok(String msg) {
        return build(SUCCESS, msg);
    }

    public static <T> JsonResult<T> ok(T data) {
        return build(SUCCESS, data);
    }

    public static JsonResult err() {
        return build(EXCEPTION);
    }

    public static JsonResult err(String msg) {
        return build(EXCEPTION, msg);
    }

    @Override
    public String toString() {
        return JsonUtil.to(this);
    }
}