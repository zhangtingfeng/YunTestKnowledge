package cn.tedu.sp24.controller;

import cn.tedu.sp24.entity.Tuploadfile;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.debugLogUtil;
import net.sf.json.JSONArray;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static cn.tedu.sp0ag4studio.common.util.WebUtils.getParamAsDto;

/**
 * @author: Eli Shaw
 * @Date: 2019-11-14 11:33:26
 * @Description：
 */
@RestController
public class ReadPicsController {
    @Autowired
    private JdbcTemplate jdbcTemplate;//Spring的JdbcTemplate是自动配置的，可直接使用


    @RequestMapping(value = "/admin/readPics", method = RequestMethod.POST)
    public JsonResult readPics(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        try {
            /* feign 测试通过 ribbon  eureka
            * http://localhost:48001
            *headRouter  student-service/TestFeign/readPics
            {
    "code": 0,
    "msg": null,
    "data": {
        "base64": "KKACiiigAooooA/9k=",
        "filename": "DrawingAndColoring-2021-03-06_18-07-39.jpg",
        "account":"testpostman",
        "excludeuploadfileIDList":["2021-03-20-12_39_10_659_ij2b,2021-03-20-12_39_20_376_QVPA","2021-03-20"]
    }
}
*
*
*
*  */


            Dto inDto = getParamAsDto(JsonResultbase64.datetoMap());
            String strdtoploadfileIDList = inDto.getAsString("excludeuploadfileIDList");
            ///JSONArray array = JSONArray.fromObject("JSON数组字符串");
            //JSONObject jsonObject = JSONObject.fromObject("JSON格式字符串");
            String strNotIn = "";
            if (!strdtoploadfileIDList.isEmpty()) {
                JSONArray arrayListID = JSONArray.fromObject(strdtoploadfileIDList);

                if (arrayListID.size() > 0) {
                    strNotIn = " and  uploadfileID NOT IN (";

                    for (int i = 0; i < arrayListID.size(); i++) {
                        String strID = arrayListID.get(i).toString();
                        strNotIn += "'" + strID + "'";
                        if (i < (arrayListID.size() - 1)) {
                            strNotIn += ",";
                        }
                    }
                    strNotIn += ")";
                }
            }


            // WHERE name NOT IN ('CVE-1999-0001', 'CVE-1999-0002', NULL);

            // List<String> ddddd= JSONArray.toArray(dtoploadfileIDList);
            // List<String> ddddd=( List<String>)dtoploadfileIDList;
            ///  if (user.getUsername().equals("admin") && user.getPassword().equals("123456")) {
            int IsDeleted = 0;
            //若需要有参数传入
            String sql = "select uploadfileID,filename,account,base64 from uploadfile where IsDeleted=?  and  date(CreateTime) = curdate() " + strNotIn;
            List<Tuploadfile> query = jdbcTemplate.query(sql, new MyRowMapper(), new Object[]{IsDeleted});
            //List<User> users = jdbcTemplate.query(sql, new MyRowMapper());


            return JsonResult.ok("验证通过").data(query).msg("返回数量" + query.size());
            // } else
            // return JsonResult.err("验证失败");
        } catch (Exception eee) {
            debugLogUtil.send("tools-service", "admin/readPic报错", eee);
        }
        ;
        return JsonResult.err("验证失败");
    }

}
