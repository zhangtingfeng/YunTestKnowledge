package cn.tedu.sp21tools;

import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * http://219.235.6.205:48001
 * headRouter=TOOLS-SERVICE/uploads/upload3
 * {
 * *     "code": 0,
 * *     "msg": null,
 * *     "data": {
 * *         "base64": "data:image/jpg;base164,/9j/4AAQSkZJRgABAQAAAQABAAD/
 * <p>
 * <p>
 * <p>
 * http://219.235.6.205:48020/uploads/upload3
 * {
 * "code": 0,
 * "msg": null,
 * "data": {
 * "base64": "data:image/jpg;base164,/9j/4AAQSkZJRgABAQAAAQABAAD/
 * <p>
 * {
 * "code": 200,
 * "msg": "查重上传成功",
 * "data": {
 * "FileName": "2021-03-06-22_43_50_601_33HW"
 * }
 * }
 * <p>
 * <p>
 * <p>
 * <p>
 * 图片上传的几种方式
 * <p>
 * <p>
 * 创建一个FileUploadController，其中@GetMapping的方法用来跳转index.html页面，而@PostMapping相关方法则是对应的 单文件上传、多文件上传、BASE64编码 三种处理方式。
 *
 * @RequestParam("file") 此处的"file"对应的就是html 中 name="file" 的 input 标签，而将文件真正写入的还是借助的commons-io中的FileUtils.copyInputStreamToFile(inputStream,file)
 * 测试
 * 完成准备事项后，启动Chapter16Application，访问 http://localhost:8080/uploads 进入到文件上传页面。单文件上传、多文件上传 都是及其简单的就不做演示了，相信各位自己也是可以完成的。
 * <p>
 * 文件上传页
 * <p>
 * BASE64 测试方法
 * <p>
 * 打开浏览器访问 http://base64.xpcha.com/pic.html 选择一张图片将其转换为base64编码的，随后将转换后的base64字符串内容 复制到下图中的文本框中，点击上传即可，随后到指定目录下就可以看到我们上传的文件了
 * <p>
 * BASE64上传
 * <p>
 * 总结
 * 目前很多大佬都写过关于 SpringBoot 的教程了，如有雷同，请多多包涵，本教程基于最新的 spring-boot-starter-parent：2.0.2.RELEASE编写，包括新版本的特性都会一起介绍…
 * @since 2018/5/31 0031
 */
@Controller
@RequestMapping("/uploads")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;//Spring的JdbcTemplate是自动配置的，可直接使用

    @GetMapping
    public String index() {
        return "index";
    }


    @PostMapping("/upload1")
    @ResponseBody
    public Map<String, String> upload1(@RequestParam("file") MultipartFile file) throws IOException {

        ///http://127.0.0.1:48020/uploads/upload1
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", file.getOriginalFilename());
        log.info("[文件大小] - [{}]", file.getSize());
        // TODO 将文件写入到指定目录（具体开发中有可能是将文件写入到云存储/或者指定目录通过 Nginx 进行 gzip 压缩和反向代理，此处只是为了演示故将地址写成本地电脑指定目录）
        file.transferTo(new File("D:\\Temp\\" + file.getOriginalFilename()));
        Map<String, String> result = new HashMap<>(16);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize() + "");
        return result;
    }

    @PostMapping("/upload2")
    @ResponseBody
    public List<Map<String, String>> upload2(@RequestParam("file") MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            return null;
        }
        List<Map<String, String>> results = new ArrayList<>();
        for (MultipartFile file : files) {
            // TODO Spring Mvc 提供的写入方式
            file.transferTo(new File("D:\\Temp\\" + file.getOriginalFilename()));
            Map<String, String> map = new HashMap<>(16);
            map.put("contentType", file.getContentType());
            map.put("fileName", file.getOriginalFilename());
            map.put("fileSize", file.getSize() + "");
            results.add(map);
        }
        return results;
    }

    @PostMapping("/upload3")
    @ResponseBody
    public JsonResult upload3(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
////http://127.0.0.1:48020/uploads/upload3
        ///{"data":{"yyy":1,"filename":"qwe","base64":"data:image/jpg;base64,/9j/4QAYRXhpZgAASUkq"}}

        ///http://127.0.0.1:48001
        ///headRouter tools-service/uploads/upload3
        //{"data":{"yyy":1,"filename":"qwe","base64":"data:image/jpg;base164,/9j/4QAYRXhpZgAA


        String strAddErrorinfo = "";
        JSONObject jsonReturnObject = new JSONObject();
        //string

        /*
         *http://127.0.0.1:48020/uploads/upload3
         * http://127.0.0.1:48020/uploads/upload3
         *headRouter [{"key":"headRouter","value":"item-service/item/decreaseNumber1","description":"","type":"text","enabled":true}]
         * data:image/jpg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAABQAAD/4QMgaHR0cDovL2AAB//Z
         * */
        try {
            debugLogUtil.send("sp21", "upload3get", JsonResultbase64);
            Object ddObjectddd = JsonResultbase64.getData();
            // String strOBJ=JsonUtil.serializeToString(ddddd);
            String ddddasfaddddd = JsonUtil.serialize(ddObjectddd);
            String strBase64 = JsonUtil.getString(ddddasfaddddd, "base64");
            String strFilename = JsonUtil.getString(ddddasfaddddd, "filename");
            String straccount = JsonUtil.getString(ddddasfaddddd, "account");

            // String strd1=JsonUtil.getString(ddddasfaddddd,"d1");
/*
*
*  String sql="select app_id appId,count from table";
      List<TracePointTypeDTO> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TracePointTypeDTO.class));

      若需要有参数传入
      String sql="select app_id appId,count from table where app_id=?";
      List<TracePointTypeDTO> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TracePointTypeDTO.class),new Object[]{appId});


  b.获取String对象的集合
    String timeSql="select update_time  from apps_exchange_rate group by update_time order by update_time desc;";
    List<String> strings =jdbcTemplate.query(timeSql, new RowMapper<String>() {
        public String mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            return rs.getString(1);
        }
    });    
* */
            // int intBase64=JsonUtil.getInt(ddddasfaddddd,"yyy");
            /// TUploadfile reqmyData= JsonUtil.read(ddddasfaddddd,TUploadfile.class);
            ///TUploadfile reqmyData = (TUploadfile)ddObjectddd;
            // String strbase64=reqmyData.getBase64();
            String strMD5 = DigestUtils.md5DigestAsHex(strBase64.getBytes());
            // TODO BASE64 方式的 格式和名字需要自己控制（如 png 图片编码后前缀就会是 data:image/png;base64,）

            // TODO 防止有的传了 data:image/png;base64, 有的没传的情况
            String fileuploadfileIDname = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss_SSS_").format(new Date()) + RandomStringUtils.randomAlphanumeric(4);

            //jdbcTemplate.queryForObject("select uploadfileID  from uploadfile where md5=?", strMD5);
            String timeSql = "select uploadfileID  from uploadfile where md5='" + strMD5 + "';";
            List<String> stringsListExsit = jdbcTemplate.query(timeSql, new RowMapper<String>() {
                public String mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                    return rs.getString(1);
                }
            });
            if (stringsListExsit != null && stringsListExsit.size() > 0) {
                jsonReturnObject.put("FileName", stringsListExsit.get(0));
                strAddErrorinfo = "查重";
            } else {
                int flag = jdbcTemplate.update("insert into uploadfile(uploadfileID, base64,filename,filelength,md5,account) values(?, ?,?,?,?,?)", fileuploadfileIDname, strBase64, strFilename, strBase64.length(), strMD5, straccount);
                ///jdbcTemplate.update("delete from USER where NAME = ?", name);
                //jdbcTemplate.queryForObject("select count(1) from USER", Integer.class);
                // jdbcTemplate.update("delete from USER");

                jsonReturnObject.put("FileName", fileuploadfileIDname);
                ///getString(String json, String key)
                // String strCode=JsonResultbase64.getCode();
                // js
                // Object dObjectddd= JsonResultbase64.getData();
                final File tempFile = new File("D:\\Temp\\" + strFilename);
                String[] d = strBase64.split("base64,");
                final byte[] bytes = Base64Utils.decodeFromString(d.length > 1 ? d[1] : d[0]);
                FileCopyUtils.copy(bytes, tempFile);

            }

        } catch (Exception eee) {
            debugLogUtil.send("tools-service", "uploadpost报错", eee);
            ;
            return JsonResult.err().data(jsonReturnObject).msg(debugLogUtil.send("sp21", "upload3报错了", eee)).code(500);
        }

        return JsonResult.ok().data(jsonReturnObject).msg(strAddErrorinfo + "上传成功").code(200);
    }


    /**
     * 上传图片
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
    public JsonResult<?> imgUpload(HttpServletRequest request) throws IOException, ParseException {
        JsonResult<?> result = JsonResult.ok();
        debugLogUtil.send("imgUpload", "uploads", "收到请求");


        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            if (file.isEmpty()) {
                throw new Exception("文件不存在！");
            }
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));


            String imgUrl = FileUtils.storeImg(file.getInputStream(),
                    DateUtil.getStringFromDate(new Date(), "yyyyMMddhhmmss") + new Date().getTime() + type,
                    DateUtil.getStringFromDate(new Date(), "yyyyMM"));
            Dto dto = (new BaseDto("imgUrl", imgUrl));

            // Dto retDto = new BaseDto();

            result = JsonResult.ok("查询queryPage").data(dto);
            //result.data(retDto);
        } catch (Exception e) {
            e.printStackTrace();
            //result = reduceErr(e.getLocalizedMessage());
            debugLogUtil.send("imgUpload", "uploads报错", e);

            result = JsonResult.err(e.getLocalizedMessage());
            //debugLog.send("QY","程序报错"+request.getRequestURI(),e.getLocalizedMessage());

        }
        return result;
    }


}
