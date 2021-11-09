package cn.tedu.sp21tools;

import cn.tedu.sp01.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
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
@RequestMapping("/remoteFile")
public class remoteFileController {

    private static final Logger log = LoggerFactory.getLogger(remoteFileController.class);
    @Value("${server.port}")
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;//Spring的JdbcTemplate是自动配置的，可直接使用

    @GetMapping("/getItems/{fileId}")
    @ResponseBody
    public JsonResult<?> getItems(@PathVariable String fileId) {
        ///http://127.0.0.1:48020/remoteFile/getItems/5555
        ////http://localhost:3001/  headRouter=item-service/item/getItems/999
        log.info("orderId=" + fileId + ",port" + port);

        String timeSql = "select base64  from uploadfile where uploadfileID='" + fileId + "';";
        List<String> stringsListExsit = jdbcTemplate.query(timeSql, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
            }
        });
        if (stringsListExsit != null && stringsListExsit.size() > 0) {
            return JsonResult.ok().data(stringsListExsit.get(0)).msg("port=" + port);

        } else {
            return JsonResult.ok().data("error").msg("port=" + port);
        }
        //List<Item> items = itemService.getItems(orderId);

    }

}
