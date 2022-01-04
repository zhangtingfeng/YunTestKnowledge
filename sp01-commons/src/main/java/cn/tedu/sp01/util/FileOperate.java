package cn.tedu.sp01.util;

import java.io.File;
import java.util.ArrayList;

public class FileOperate {
    private static ArrayList<String> staticFileList;


    public static ArrayList<String> getPubFiles(String path) {
        staticFileList=new ArrayList<>();
        getPrivateFiles(path);


        return staticFileList;
    }


    private static void getPrivateFiles(String path) {

        File file = new File(path.replace("\\","/"));
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                staticFileList.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
                getPrivateFiles(tempList[i].toString());
            }
        }
    }
}
