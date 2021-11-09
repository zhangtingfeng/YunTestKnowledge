package cn.eggsoft.sp34pricereport.excel;


import cn.tedu.sp0ag4studio.core.metatype.Dto;
import cn.tedu.sp0ag4studio.core.metatype.impl.BaseDto;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelImg {


    public static Dto getDataFromExcel(String filePath,String SaveFilePath,String ReturnPath) throws IOException {

        //判断是否为excel类型文件
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            System.out.println("文件不是excel类型");
        }
        Dto resultData=null;
        FileInputStream fis = null;
        Workbook wookbook = null;
        Sheet sheet = null;
        try {
            //获取一个绝对地址的流
            fis = new FileInputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //2003版本的excel，用.xls结尾
            wookbook = new HSSFWorkbook(fis);//得到工作簿

        } catch (Exception ex) {
            //ex.printStackTrace();
            try {
                //2007版本的excel，用.xlsx结尾
                fis = new FileInputStream(filePath);
                wookbook = new XSSFWorkbook(fis);//得到工作簿
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        Map<String, PictureData> maplist = null;

        sheet = wookbook.getSheetAt(0);
        // 判断用07还是03的方法获取图片
        if (filePath.endsWith(".xls")) {
            maplist = getPictures1((HSSFSheet) sheet);
        } else if (filePath.endsWith(".xlsx")) {
            maplist = getPictures2((XSSFSheet) sheet);
        }
        try {
            fis.close();
            wookbook.close();
            resultData= printImg(maplist,SaveFilePath,ReturnPath);
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultData;
    }

    /**
     * 获取图片和位置 (xls)
     *
     * @param sheet
     * @return
     * @throws IOException
     */
    private static Map<String, PictureData> getPictures1(HSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        for (HSSFShape shape : list) {
            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                PictureData pdata = picture.getPictureData();
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1(); // 行号-列号
                map.put(key, pdata);
            }
        }
        return map;
    }

    /**
     * 获取图片和位置 (xlsx)
     *
     * @param sheet
     * @return
     * @throws IOException
     */
    private static Map<String, PictureData> getPictures2(XSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol();
                    map.put(key, picture.getPictureData());
                }
            }
        }
        return map;
    }

    //图片写出
    private static Dto printImg(Map<String, PictureData> sheetList, String SaveFilePath,String ReturnPath) throws IOException, InterruptedException {
        createFolder(SaveFilePath);
        //for (Map<String, PictureData> map : sheetList) {
        Dto resultData = new BaseDto();
        Object key[] = sheetList.keySet().toArray();
        for (int i = 0; i < sheetList.size(); i++) {
            // 获取图片流
            PictureData pic = sheetList.get(key[i]);
            // 获取图片索引
            String picName = key[i].toString();
            // 获取图片格式
            String ext = pic.suggestFileExtension();

            byte[] data = pic.getData();
            String strFileName=DateUtil.getStringFromDate(new Date(), "yyyyMMddhhmmss") + new Date().getTime()+ "."  + ext;
            String strFilePath= SaveFilePath+"/"+strFileName;


            resultData.put(picName,ReturnPath+"/"+strFileName);

            Thread.sleep(1);

            //图片保存路径
            FileOutputStream out = new FileOutputStream(strFilePath);
            out.write(data);
            out.close();
        }
        // }
       return resultData;
    }


    //执行 测试 。图片保存到 D:\\img 就算成功

    public static void main(String[] args) throws Exception {
        //  getDataFromExcel("C:\\Users\\Tingfeng.Zhang\\Downloads\\202105\\A1.xlsx");

    }


    private static void createFolder(String folderPath) {
        File file = new File(folderPath);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("---------create folder-------------" + folderPath);
            file.mkdir();
        } else {
            System.out.println(folderPath + "//目录存在");
        }
    }
}