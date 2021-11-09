package cn.tedu.sp24.controller;


import cn.tedu.sp01.util.CommonUtil;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import cn.tedu.sp24.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/cmn")
public class CommonConController extends BaseController {

    /**
     * 分页
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/queryPage")
    public JsonResult<?> queryPage(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        try {
            String strgetData = JsonUtil.serialize(JsonResultbase64.getData());
            String strtoken = JsonResultbase64.getToken();
            Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
            //Dto dto=(BaseDto)JsonResultbase64;
            ///Dto member = redisServiceImpl.getObject(JsonResultbase64.getToken(), BaseDto.class);

            Dto retDto = new BaseDto();
            /*if (null == member) {
                retDto.put("code", StatusConstant.CODE_4000);
                retDto.setMsg("请登录");
                return retDto;
            }
            if (null != member) {
                dto.put("userid", member.get("id"));
            }*/
            String sql = dto.getAsString("sql");
            if (!StringUtils.isNotBlank(sql)) {
                sql = "queryList";
            }
            String AttachExtentFileNamesql = dto.getAsString("Attachsql");
            if (StringUtils.isNotBlank(AttachExtentFileNamesql)) {
                AttachExtentFileNamesql = java.net.URLDecoder.decode(AttachExtentFileNamesql,"UTF-8");
                dto.put("Attachsql",AttachExtentFileNamesql);
            }

            List<Dto> paramList = bizService.queryForPageCenter(dto.getAsString("t") + "." + sql, dto);
            retDto.put("rows", paramList);
            retDto.put("rowsCount", paramList.size());
            String sqlCount = dto.getAsString("sqlCount");
            if (!StringUtils.isNotBlank(sqlCount)) {
                sqlCount = "queryListCount";
            }
            Dto totalCount = (BaseDto) bizService.queryForDto(dto.getAsString("t") + "." + sqlCount, dto);
            if (null != totalCount && totalCount.size() > 0) {
                retDto.put("total", totalCount.get("total"));
            } else {
                retDto.put("total", 0);
            }
            retDto.put("code", "0000");
            return JsonResult.ok("查询queryPage").data(retDto);
        } catch (Exception e) {
            String strErr = debugLogUtil.send("queryPage", "程序queryPage报错", e);
            log.info(strErr);
            e.printStackTrace();
            return JsonResult.err(strErr);
        }
    }


    /**
     * 查询对象
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryInfo")
    public JsonResult<?> queryInfo(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            /*Dto member = redisService.getObject(dto.getAsString("token"), BaseDto.class);
            if(!StringUtils.isNotEmpty(dto.getAsString("loginCode"))){
                if(null == member){
                    result.setCode(StatusConstant.CODE_4000);
                    result.setMsg("请登录");
                    return result;
                }
            }*/
            String sql = dto.getAsString("sql");
            if (!StringUtils.isNotBlank(sql)) {
                sql = "getInfo";
            }

            Dto info = (BaseDto) bizService.queryForDto(dto.getAsString("t") + "." + sql, dto);
            result = JsonResult.ok(info);
            //result.setData(info);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }

    /**
     * 查询列表
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryList")
    public JsonResult<?> queryList(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        //Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
           /* Dto member = redisService.getObject(dto.getAsString("token"), BaseDto.class);
            if(!StringUtils.isNotEmpty(dto.getAsString("loginCode"))){
                if(null == member){
                    result.setCode(StatusConstant.CODE_4000);
                    result.setMsg("请登录");
                    return result;
                }
            }
            // dto.put("flag",member.getAsString("shopid"));
            dto.put("userid", member == null ? "" : member.get("id"));*/
            //查询总网点
            String sql = dto.getAsString("sql");
            if (!StringUtils.isNotBlank(sql)) {
                sql = "queryList";
            }
            List paramList = bizService.queryForList(dto.getAsString("t") + "." + sql, dto);
            result = JsonResult.ok(paramList);
            // result.setData(paramList);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());
        }
        // debugLog.send("QY","返回数据"+request.getRequestURI()+dto.getAsString("t"),result);

        return result;
    }


    /**
     * 保存信息
     *
     * @param
     * @returnlIST
     */
    @ResponseBody
    @RequestMapping(value = "/editInfo")
    public JsonResult<?> editInfo(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto inDto = WebUtils.getParamAsDto(request);
        Dto inDto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        inDto.put("tableName", inDto.getAsString("t"));
        try {
            //Dto member = redisServiceImpl.getObject(JsonResultbase64.getToken(), BaseDto.class);


            if (inDto.getAsLong("id") != null) {// 修改
                if (!StringUtils.isNotEmpty(inDto.getAsString("method"))) {
                    inDto.put("method", "updateInfo");
                }
                //inDto.put("updator", member == null ? "" : member.get("id"));
                if (inDto.getAsString("t").equals("activity") && StringUtils.isNotEmpty(inDto.getAsString("fileid"))) {
                    //CommonController.getTxVideoPic(inDto.getAsString("fileid"));
                }
                if (!StringUtils.isNotEmpty(inDto.getAsString("method"))) {
                    bizService.update(inDto);
                } else {
                    bizService.updateInfo(inDto.getAsString("tableName") + "." + inDto.getAsString("method"), inDto);
                }
                if (inDto.getAsString("t").equals("userDynamic") && StringUtils.isNotEmpty(inDto.getAsString("fileid"))) {
                    // String task_id = CommonController.getTxVideo(inDto.getAsString("fileid"));
                    Dto param = new BaseDto();
                    param.put("tableName", "userDynamic");
                    // param.put("task_id", task_id);
                    param.put("id", inDto.getAsString("id"));
                    bizService.update(param);
                }
                if (inDto.getAsString("t").equals("activity") && StringUtils.isNotEmpty(inDto.getAsString("fileid"))) {
                    // String task_id = CommonController.getTxVideo(inDto.getAsString("fileid"));
                    Dto param = new BaseDto();
                    param.put("tableName", "activity");
                    //   param.put("task_id", task_id);
                    param.put("id", inDto.getAsString("id"));
                    bizService.update(param);
                }
            } else {// 新增
                if ("1".equals(inDto.getAsString("flag_no"))) {
                    inDto.put("number", CommonUtil.getCarrierNo());
                }
                if (!StringUtils.isNotEmpty(inDto.getAsString("method"))) {
                    inDto.put("method", "saveInfo");
                }
                if (inDto.getAsString("t").equals("activity") && StringUtils.isNotEmpty(inDto.getAsString("fileid"))) {
                    //  CommonController.getTxVideoPic(inDto.getAsString("fileid"));
                }
                //inDto.put("creator", member == null ? "" : member.get("id"));
                if (!StringUtils.isNotEmpty(inDto.getAsString("method"))) {
                    bizService.save(inDto);
                } else {
                    bizService.saveInfo(inDto.getAsString("tableName") + "." + inDto.getAsString("method"), inDto);
                }
                if (inDto.getAsString("t").equals("userDynamic") && StringUtils.isNotEmpty(inDto.getAsString("fileid"))) {
                    //String task_id = CommonController.getTxVideo(inDto.getAsString("fileid"));
                    Dto param = new BaseDto();
                    param.put("tableName", "userDynamic");
                    // param.put("task_id", task_id);
                    param.put("id", inDto.getAsString("id"));
                    bizService.update(param);
                }
                if (inDto.getAsString("t").equals("activity") && StringUtils.isNotEmpty(inDto.getAsString("fileid"))) {
                    // String task_id = CommonController.getTxVideo(inDto.getAsString("fileid"));
                    Dto param = new BaseDto();
                    param.put("tableName", "activity");
                    //param.put("task_id", task_id);
                    param.put("id", inDto.getAsString("id"));
                    bizService.update(param);
                }

            }
            result = JsonResult.ok(inDto);
            //   result.setData(inDto);
        } catch (Exception e) {
//            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }
}
