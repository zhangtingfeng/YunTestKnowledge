package cn.tedu.sp24.controller;


import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import cn.tedu.sp24.base.BaseController;
import com.qiniu.example.ListQiniuDemo;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/qiNiu")
public class QiNiuController extends BaseController {


    /**

     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/DoModifyqiNiuWriteVisitPath")
    public JsonResult<?> DoModifyqiNiuWriteVisitPath(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        try {

            Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
            //Dto dto=(BaseDto)JsonResultbase64;
            Integer IntParentID = dto.getAsInteger("id");
            String strpath = "";
            if (IntParentID == 0) {
                /*第一步  第一个大目录
                *  {
     "code": 200,
     "msg": "查询queryPage",
     "loginChannel": null,
     "token": null,
     "sign": null,
     "data": {
     "id":0
     }
     }
                * */


                strpath = "Konowledge/";
            } else {

                  /*第二步  次级目录
                *  {
     "code": 200,
     "msg": "查询queryPage",
     "loginChannel": null,
     "token": null,
     "sign": null,
     "data": {
     "id":0
     }
     }
                * */


                Dto dtolearn_knowledge = new BaseDto();
                dtolearn_knowledge.put("id", IntParentID);
                Dto learn_knowledgeGetInfo = (BaseDto) bizService.queryForDto("learn_knowledge.getInfo", dtolearn_knowledge);
                strpath = learn_knowledgeGetInfo.getAsString("path");


                Dto retDeleteDto = new BaseDto();
                retDeleteDto.put("pid", IntParentID);//0  会是null  不能写入数据库
                retDeleteDto.put("tableName", "learn_knowledge");//0  会是null  不能写入数据库
                retDeleteDto.put("method", "deleteTrueInfo");//0  会是null  不能写入数据库
                bizService.delete(retDeleteDto);

            }

            ListQiniuDemo ddListQiniuDemodd = new ListQiniuDemo();

            //strpath="Konowledge/财经证券经济管理/财会税务/";
            ArrayList<String> returnList = ddListQiniuDemodd.getPathList(strpath, "GetFolder");


            for (int i = 0; i < returnList.size(); i++) {
                String strPath = returnList.get(i);
                String[] ddsplitdd = strPath.split("/");
                int dddLength = ddsplitdd.length;

                Dto retDto = new BaseDto();
                retDto.put("Title", ddsplitdd[dddLength - 1]);
                retDto.put("path", strPath);
                retDto.put("pid", IntParentID);//0  会是null  不能写入数据库
                retDto.put("DataType", "folder");
                Object ddobjectdddd = bizService.saveInfo("learn_knowledge.saveInfo", retDto);
                int ddd = 0;
            }

            //  dto.put("QiuNiuList",returnList);

            return JsonResult.ok("查询queryPage").data(returnList);
        } catch (Exception e) {
            String strErr = debugLogUtil.send("queryPage", "程序queryPage报错", e);
            log.info(strErr);
            e.printStackTrace();
            return JsonResult.err(strErr);
        }
    }


    /**
     * http://localhost:48024/teacher-service/qiNiu/DoModifyqiNiuWriteVisitFile
     * <p>
     * {
     * "code": 200,
     * "msg": "查询queryPage",
     * "loginChannel": null,
     * "token": null,
     * "sign": null,
     * "data": {
     * "pid":1
     * }
     * }
     * <p>
     * {
     * "code": 200,
     * "msg": "查询queryPage",
     * "loginChannel": null,
     * "token": null,
     * "sign": null,
     * "data": {
     * "pid":1
     * }
     * }
     *
     * @param
     * @return
     */


    @ResponseBody
    @PostMapping(value = "/DoModifyqiNiuWriteVisitFile")
    public JsonResult<?> DoModifyqiNiuWriteVisitFile(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        try {

            Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
            //Dto dto=(BaseDto)JsonResultbase64;
            // Integer IntParentID = dto.getAsInteger("pid");
            // Dto dtolearn_knowledge = new BaseDto();
            // dtolearn_knowledge.put("id", IntParentID);
            //Dto learn_knowledgeGetInfo = (BaseDto) bizService.queryForDto("learn_knowledge.getInfo", dto);
            // String strpath=learn_knowledgeGetInfo.getAsString("path");

            List<Dto> dto_learn_knowledgeList = bizService.queryForList("learn_knowledge.queryList", dto);
            ListQiniuDemo ddListQiniuDemodd = new ListQiniuDemo();

            for (int j = 0; j < dto_learn_knowledgeList.size(); j++) {
                Integer intCurID = dto_learn_knowledgeList.get(j).getAsInteger("id");
                String strpath = dto_learn_knowledgeList.get(j).getAsString("path");

                ArrayList<String> returnList = ddListQiniuDemodd.getPathList(strpath, "GetFile");

                for (int i = 0; i < returnList.size(); i++) {
                    String strPath = returnList.get(i);
                    // String[] ddsplitdd = strPath.split(".");
                    //  int dddLength = ddsplitdd.length;

                    //从字符串后开始检索
                    int splitIndex = strPath.lastIndexOf("/");
                    int typeIndex = strPath.lastIndexOf(".");

//用于处理没有“.*”（拓展名的情况）
                    if (typeIndex < 0)
                        typeIndex = strPath.length();

//文件路径开始到“\”为文件路径
                    String filePath = strPath.substring(0, splitIndex);
                    String fileName = strPath.substring(splitIndex + 1, typeIndex);
                    String extName = strPath.substring(typeIndex);
                    if (extName.equals(".txt")) continue;

                    Dto retDto = new BaseDto();
                    retDto.put("Title", fileName);
                    retDto.put("path", strPath);
                    retDto.put("pid", intCurID);
                    retDto.put("DataType", extName);

                    Object ddobjectdddd = bizService.saveInfo("learn_knowledge.saveInfo", retDto);
                    int ddd = 0;
                }
            }


            //  dto.put("QiuNiuList",returnList);

            return JsonResult.ok("查询queryPage").data(dto_learn_knowledgeList);
        } catch (Exception e) {
            String strErr = debugLogUtil.send("queryPage", "程序queryPage报错", e);
            log.info(strErr);
            e.printStackTrace();
            return JsonResult.err(strErr);
        }
    }
}
