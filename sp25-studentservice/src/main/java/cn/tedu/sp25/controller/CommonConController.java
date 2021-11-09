package cn.tedu.sp25.controller;


import cn.tedu.sp01.util.CommonUtil;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp01.util.JsonUtil;
import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp0ag4studio.common.util.WebUtils;
import cn.tedu.sp25.base.BaseController;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import net.sf.ehcache.search.impl.BaseResult;
import org.apache.commons.lang3.StringUtils;

import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/cmn")
public class CommonConController extends BaseController {


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
     * 查询对象 数据模糊
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/queryInfoRole")
    public JsonResult<?> queryInfoRole(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {

        try {
            String strgetData = JsonUtil.serialize(JsonResultbase64.getData());
            String strt = JsonUtil.getString(strgetData, "t");

            Dto info = (BaseDto) bizService.queryForDto(strt + ".getInfo", JsonResultbase64.getData());
            List<Dto> paramList = new ArrayList<Dto>();
            paramList.add(info);
            return JsonResult.ok("查询对象").data(paramList.get(0));
        } catch (Exception e) {
            String strErr = debugLogUtil.send("queryInfoRole", "程序报错", e);
            log.info(strErr);
            e.printStackTrace();
            return JsonResult.err(strErr);
        }
        //return result;
    }


    /**
     * 查询列表
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryListRole")
    public JsonResult<?> queryListRole(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        //Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            Dto member = redisServiceImpl.getObject(JsonResultbase64.getToken(), BaseDto.class);

            dto.put("userid", member == null ? "" : member.get("id"));
            List paramList = null;
            if (StringUtils.isNotBlank(dto.getAsString("sql"))) {
                paramList = bizService.queryForList(dto.getAsString("t") + "." + dto.getAsString("sql"), dto);
            } else {
                paramList = bizService.queryForList(dto.getAsString("t") + ".queryList", dto);
            }
            result = JsonResult.ok(paramList);
            // result.setData(paramList);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());
        }
        return result;
    }


    /**
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/queryPage/{pathJsonResultbase64}")
    public JsonResult<?> getqueryPage(@PathVariable String pathJsonResultbase64) throws IOException, ParseException {
        JsonResult<?> dErrordd = JsonResult.err("查询queryPage");

        String result = java.net.URLDecoder.decode(pathJsonResultbase64, "UTF-8");

        // Map<String, Object> dummyMap =  JSON.parseObject(jsonStr, LinkedHashMap.class, Feature.OrderedField);//关键的地方，转化为有序map
        Map<String, Object> dummyMapooododErrordd = JSON.parseObject(result, LinkedHashMap.class);// JSONObject.fromObject(strredisTemplate);
        Map<String, Object> dummyMapooododErrorddData = JSON.parseObject(dummyMapooododErrordd.get("data").toString(), LinkedHashMap.class);// JSONObject.fromObject(strredisTemplate);

        JsonResult dodErrordd = JSON.parseObject(result, JsonResult.class);// JSONObject.fromObject(strredisTemplate);
        dodErrordd.data(dummyMapooododErrorddData);

        dErrordd = queryPostPage(dodErrordd);
        return dErrordd;
    }

    /**
     * 分页
     *
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/queryPage")
    public JsonResult<?> queryPostPage(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        try {
            String strgetData = JsonUtil.serialize(JsonResultbase64.getData());
            String strtoken = JsonResultbase64.getToken();
            Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
            //Dto dto=(BaseDto)JsonResultbase64;
            Dto member = redisServiceImpl.getObject(JsonResultbase64.getToken(), BaseDto.class);

            Dto retDto = new BaseDto();
            /*if (null == member) {
                retDto.put("code", StatusConstant.CODE_4000);
                retDto.setMsg("请登录");
                return retDto;
            }*/
            if (null != member) {
                dto.put("userid", member.get("id"));
            }
            String sql = dto.getAsString("sql");
            if (!StringUtils.isNotBlank(sql)) {
                sql = "queryList";
            }

            List<Dto> paramList = bizService.queryForPageCenter(dto.getAsString("t") + "." + sql, dto);
            retDto.put("rows", paramList);
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
            String strErr = debugLogUtil.send("queryInfoRole", "程序queryPage报错", e);
            log.info(strErr);
            e.printStackTrace();
            return JsonResult.err(strErr);
        }
    }


    /**
     * 分页
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryPageRole")
    public JsonResult<?> queryPageRole(@RequestBody JsonResult JsonResultbase64) {
        //  Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        Dto retDto = new BaseDto();
        JsonResult<?> result = JsonResult.ok();
        try {
            Dto member = redisServiceImpl.getObject(JsonResultbase64.getToken(), BaseDto.class);

            dto.put("userid", member == null ? "" : member.get("id"));
            List<Dto> paramList = bizService.queryForPage(dto.getAsString("t") + ".queryList", dto);
            System.out.println("分页查询：   " + paramList.size());
            retDto.put("rows", paramList);
            retDto.put("total", bizService.queryForList(dto.getAsString("t") + ".queryList", dto).size());
            result.ok().data(retDto);
        } catch (Exception e) {
            e.printStackTrace();
            result.err().msg(e.getLocalizedMessage());
            //reduceErr();
        }
        return result;
    }

    /**
     * 分页
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryForPage")
    public Dto queryForPage(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        Dto retDto = new BaseDto();
        try {
            Dto member = redisServiceImpl.getObject(JsonResultbase64.getToken(), BaseDto.class);

            dto.put("userid", member == null ? "" : member.get("id"));
            List<Dto> paramList = bizService.queryForPage(dto.getAsString("t") + ".queryList", dto);
            System.out.println("分页查询：   " + paramList.size());
            retDto.put("data", paramList);
//			retDto.put("total", bizService.queryForList(dto.getAsString("t") + ".queryList", dto).size());
            retDto.put("code", "0000");
        } catch (Exception e) {
            e.printStackTrace();
            // reduceErr(e.getLocalizedMessage());
            //  debugLog.send("QY","程序报错"+request.getRequestURI(),e.getLocalizedMessage());

        }
        return retDto;
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
            Dto member = redisServiceImpl.getObject(JsonResultbase64.getToken(), BaseDto.class);


            if (inDto.getAsLong("id") != null) {// 修改
                if (!StringUtils.isNotEmpty(inDto.getAsString("method"))) {
                    inDto.put("method", "updateInfo");
                }
                inDto.put("updator", inDto.get("updator") == null ? (member == null ? "" : member.get("id")) : inDto.get("updator"));
                if (inDto.getAsString("t").equals("activity") && StringUtils.isNotEmpty(inDto.getAsString("fileid"))) {
                    //CommonController.getTxVideoPic(inDto.getAsString("fileid"));
                }
                if (!StringUtils.isNotEmpty(inDto.getAsString("method"))) {
                    bizService.update(inDto);
                } else {
                    bizService.updateInfo(inDto.getAsString("tableName") + "." + inDto.getAsString("method"), inDto);
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
                inDto.put("creator", member == null ? "" : member.get("id"));
                if (!StringUtils.isNotEmpty(inDto.getAsString("method"))) {
                    bizService.save(inDto);
                } else {
                    bizService.saveInfo(inDto.getAsString("tableName") + "." + inDto.getAsString("method"), inDto);
                }


            }
            result = JsonResult.ok(inDto);
            //   result.setData(inDto);
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }

    /**
     * 保存信息
     *
     * @param
     * @returnlIST
     */
    @ResponseBody
    @RequestMapping(value = "/editParmas")
    public JsonResult<?> editParmas(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        //  Dto inDto = WebUtils.getParamAsDto(request);
        Dto inDto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        inDto.put("tableName", inDto.getAsString("t"));
        try {
            Dto member = redisServiceImpl.getObject(JsonResultbase64.getToken(), BaseDto.class);

            if (!StringUtils.isNotEmpty(inDto.getAsString("loginCode"))) {
                if (null == member) {
                    result.setCode(4000);
                    result.setMsg("请登录");
                    return result;
                }
            }
            if (!StringUtils.isNotEmpty(inDto.getAsString("method"))) {
                inDto.put("method", "updateInfo");
            }
            inDto.put("updator", member == null ? "" : member.get("id"));

            if (!StringUtils.isNotEmpty(inDto.getAsString("method"))) {
                bizService.update(inDto);
            } else {
                bizService.updateInfo(inDto.getAsString("tableName") + "." + inDto.getAsString("method"), inDto);
            }
            result = JsonResult.ok(inDto);
            //result.setData(inDto);
        } catch (Exception e) {
//            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }


    /**
     * 删除信息
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteInfo")
    public JsonResult<?> deleteInfo(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        Dto inDto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        inDto.put("tableName", inDto.getAsString("t"));
        if (!StringUtils.isNotEmpty(inDto.getAsString("sql"))) {
            inDto.put("method", "deleteInfo");
        } else {
            inDto.put("method", inDto.getAsString("sql"));
        }
        try {
            String[] checked = inDto.getAsString("ids").split(",");
            for (int i = 0; i < checked.length; i++) {
                inDto.put("id", checked[i]);
                bizService.delete(inDto);
            }
            result = JsonResult.ok("数据操作成功");
            // result.setData(new BaseDto("msg", "数据操作成功"));
        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }

}
