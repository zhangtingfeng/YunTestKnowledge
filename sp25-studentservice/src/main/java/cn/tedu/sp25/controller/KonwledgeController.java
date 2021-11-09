package cn.tedu.sp25.controller;

import cn.tedu.sp01.util.CommonUtil;
import cn.tedu.sp01.util.JsonResult;
import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import cn.tedu.sp25.base.BaseController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class KonwledgeController extends BaseController {


    @ResponseBody
    @RequestMapping(value = "/doKnowledge")
    public JsonResult<?> doKnowledge(@RequestBody JsonResult JsonResultbase64) throws IOException, ParseException {
        // Dto dto = WebUtils.getParamAsDto(request);
        Dto dto = webUtils.getParamAsDto(JsonResultbase64.datetoMap());
        JsonResult<?> result = JsonResult.ok();
        try {


        } catch (
                Exception e) {
            e.printStackTrace();
            result = JsonResult.err(e.getLocalizedMessage());

        }

        return result;
    }

    public ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("文     件：" + tempList[i]);
                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
//              System.out.println("文件夹：" + tempList[i]);
            }
        }
        return files;
    }


    private void getALLFile(String path, List<String> AllFileList){
        // get file list where the path has
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();

        for(int i=0;i<array.length;i++){
            if(array[i].isFile()){
                AllFileList.add(array[i].getPath());
                // only take file name
                System.out.println("^^^^^" + array[i].getName());
                // take file path and name
                System.out.println("#####" + array[i]);
                // take file path and name
                System.out.println("*****" + array[i].getPath());
            }else if(array[i].isDirectory()){
                getALLFile(array[i].getPath(),AllFileList);
            }
        }
    }

}
