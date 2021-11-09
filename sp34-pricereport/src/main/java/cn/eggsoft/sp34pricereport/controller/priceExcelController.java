package cn.eggsoft.sp34pricereport.controller;


import cn.eggsoft.sp34pricereport.base.BaseController;
import cn.eggsoft.sp34pricereport.excel.*;
import cn.eggsoft.sp34pricereport.myuploadProperties;
import cn.tedu.sp01.util.JsonResult;

import cn.tedu.sp01.util.debugLogUtil;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.eggsoft.sp34pricereport.FileUtils.createFolder;

@RestController
@RequestMapping("/priceExcel")
public class priceExcelController extends BaseController {

    @Autowired
    private myuploadProperties foo;


    @ResponseBody
    @PostMapping(value = "/saveExcel")
    public JsonResult<?> saveExcel(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {
            String strIMG_PATH = foo.getServerIMGUpload();
            String strimg_url = foo.getImg_url();

            String strIMGURL = dto.getAsString("imgUrl");
            // 设定Excel文件所在路径
            String excelFileName = strIMG_PATH + File.separator + strIMGURL;
            // 读取Excel文件内容
            List<Dto> readResult = ExcelReader.readExcel(excelFileName);

            String strFilePath = DateUtil.getStringFromDate(new Date(), "yyyyMM");
            String newPath = foo.getServerIMGUpload() + File.separator + foo.getImg_url() + File.separator + strFilePath;



            Dto ListIMGData = ExcelImg.getDataFromExcel(excelFileName, newPath, foo.getImg_url() + "/" + strFilePath);
            if (ListIMGData != null) {
                for (int i = 0; i < readResult.size(); i++) {
                    Dto dtoEachPara = readResult.get(i);
                    String strgoodspicture = dtoEachPara.getAsString("goodspicture");
                    ;

                    dtoEachPara.put("goodspicture", ListIMGData.getAsString(strgoodspicture));

                    // int intValue= bizService.saveInfo("goodimports.saveInfo", dtoEachPara);
                }
                // saveFile(readResult);
            }
            String MarkerDeletefileuploadfileIDname = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss_SSS_").format(new Date()) + RandomStringUtils.randomAlphanumeric(4);

            try {
                for (int i = 0; i < readResult.size(); i++) {
                    Dto dtoEachPara = readResult.get(i);
                    dtoEachPara.put("updator", MarkerDeletefileuploadfileIDname);

                    bizService.saveInfo("goods_imports.saveInfo", dtoEachPara);
                }
            } catch (Exception ee) {
                Dto resultDeleteData = new BaseDto();
                resultDeleteData.put("updator", MarkerDeletefileuploadfileIDname);

                bizService.saveInfo("goods_imports.deleteTrueInfo", resultDeleteData);

                throw ee;
            }

            result = JsonResult.ok(readResult);

        } catch (Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }


    @ResponseBody
    @PostMapping(value = "/downloadExcel")
    public JsonResult<?> downloadExcel(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        JsonResult<?> result = new JsonResult<>();


        try {
            Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
            //List<Dto> readResultList = bizService.queryForList("goods_imports.queryList", dto);
            List readResultList = bizService.queryForPageCenter("goods_imports.queryList", dto);

            // String obgStringList = dto.getAsString("data");
           /// List<Map<String, Object>> readResultList = JSON.parseObject(obgStringList, new TypeReference<List<Map<String, Object>>>() {

            String strPath=saveFile(readResultList);
            Dto returndto =new BaseDto();
            returndto.put("downexcel",strPath);
            result = JsonResult.ok(returndto);

        } catch (Exception e) {
            debugLogUtil.send("downloadExcel", "Exception=", e.getMessage());
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }
        return result;
    }


    private String saveFile(List<Map<String, Object>> readResultList) throws IOException, ParseException {
        List<intField> intFieldList = new ArrayList<>();

        intFieldList.add(new intField(1, "Num"));
        intFieldList.add(new intField(2, "goodsclass"));
        intFieldList.add(new intField(3, "goodspicture"));
        intFieldList.add(new intField(4, "goodname"));
        intFieldList.add(new intField(5, "specification"));
        intFieldList.add(new intField(6, "Shelflife"));
        intFieldList.add(new intField(7, "unit"));
        intFieldList.add(new intField(8, "retailprice"));
        intFieldList.add(new intField(9, "activePrice"));
        intFieldList.add(new intField(10, "BFactoryPrice"));
        intFieldList.add(new intField(11, "yunfei"));

        String strTempleFile=foo.getServerIMGUpload() + File.separator + foo.getImg_url() + File.separator+"COutTemplate02.xlsx";
        debugLogUtil.send("downloadExcel", "strTempleFile=", strTempleFile);


        DynamicOperateExcelUtils dynamicOperateExcelUtils = new DynamicOperateExcelUtils(strTempleFile);
        String strIMG_PATH = foo.getServerIMGUpload();
        // 读取内容
        String cellValue = dynamicOperateExcelUtils.getCellValue(1, 1);
        System.out.println(cellValue);
        debugLogUtil.send("downloadExcel", "strIMG_PATH=", strIMG_PATH);

        for (int i = 0; i < readResultList.size(); i++) {
            Map<String, Object> oneLine = readResultList.get(i);
            for (int j = 0; j < intFieldList.size(); j++) {
                String strFieldName = intFieldList.get(j).getFiledName();
                Integer intPosCol = (int) intFieldList.get(j).getPosRow();
                String strValue = null;
                if (strFieldName.equals("goodspicture")) {
                    Object objoneLine=oneLine.get(strFieldName);
                    if (objoneLine!=null){
                        strValue = objoneLine.toString();
                        debugLogUtil.send("downloadExcel", "strIMG_PATH=", strIMG_PATH + "/" + strValue);
                        dynamicOperateExcelUtils.InserImg((2 + i), intPosCol, strIMG_PATH + "/" + strValue);
                    }
                } else {
                    if (strFieldName.equals("Num")) {

                        strValue = Integer.toString(i + 1);
                    } else {
                        Object ddddd = oneLine.get(strFieldName);
                        if (ddddd != null) {
                            strValue = ddddd.toString();
                        }
                    }

                    dynamicOperateExcelUtils.replaceCellValue((2 + i), intPosCol, strValue);
                }

            }
            // 替换单元格内容(注意获取的cell的下标是合并之前的下标)

        }
        // 动态插入数据-增加行
        String strFilePath = DateUtil.getStringFromDate(new Date(), "yyyyMMddHHmmss")+".xlsx";
        String newPath = foo.getServerIMGUpload() + File.separator +foo.getImg_url()+"OutExcel" + File.separator + strFilePath;
        createFolder(foo.getServerIMGUpload() + File.separator +foo.getImg_url()+"OutExcel");
        dynamicOperateExcelUtils.exportExcel(new File(newPath));

        return foo.getImg_url()+"OutExcel" + File.separator + strFilePath;
    }
}
