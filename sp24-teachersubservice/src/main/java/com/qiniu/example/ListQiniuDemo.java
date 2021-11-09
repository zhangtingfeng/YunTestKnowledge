package com.qiniu.example;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;

import java.util.ArrayList;


public class ListQiniuDemo {

    public static void main(String args[]) {
        ListQiniuDemo dddd = new ListQiniuDemo();
        ArrayList<String> returnList = dddd.getPathList("Konowledge/", "GetFolder");

        for (int i = 0; i < returnList.size(); i++) {
            String strPath = returnList.get(i);
            String[] ddsplitdd = strPath.split("/");
            int dddLength = ddsplitdd.length;

        }

    }

    private FileListing pubListQuery(String strprefix, int limit, String delimiter, String strmarker) throws QiniuException {
        FileListing fileListing =null;
        try {
            //设置需要操作的账号的AK和SK
            String ACCESS_KEY = "gJ1XM_EGrQnSWj-9sjpTmjiVbe1203U98yYu5XSt";
            String SECRET_KEY = "EPI1MsHyFcKnI8ncL66gsYCpwjsL7UqR0Nl9-uFm";
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

            Zone z = Zone.zone0();
            Configuration c = new Configuration(z);

            //实例化一个BucketManager对象
            BucketManager bucketManager = new BucketManager(auth, c);

            //要列举文件的空间名
            String bucket = "weiyuntest";

            fileListing = bucketManager.listFiles(bucket, strprefix, strmarker, limit, delimiter);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            throw e;
        }
        return fileListing;
    }


    public ArrayList<String> getPathList(String strParentPath, String GetFileOrFolder) {


        ArrayList<String> ReturnList = new ArrayList<String>();


        try {
            //调用listFiles方法列举指定空间的指定文件
            //参数一：bucket    空间名
            //参数二：prefix    文件名前缀
            //参数三：marker    上一次获取文件列表时返回的 marker
            //参数四：limit     每次迭代的长度限制，最大1000，推荐值 100
            //参数五：delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
            int intgetLimit = 30;

            if (GetFileOrFolder == "GetFolder") {
                String delimiter = "/";
                String strmarker = null;
                String strprefix = strParentPath;
                FileListing fileListing = pubListQuery(strprefix, intgetLimit, delimiter, strmarker);
                strmarker = fileListing.marker;
                // 获取  列举的目录名
                String[] delimiter1 = fileListing.commonPrefixes;
               if (delimiter1!=null){
                   for (String fileInfo : delimiter1) {
                       ReturnList.add(fileInfo);
                       // System.out.println(fileInfo);
                   }
               }

                while (!(strmarker == null)) {
                    fileListing = pubListQuery(strprefix, intgetLimit, delimiter, strmarker);
                    delimiter1 = fileListing.commonPrefixes;
                    strmarker = fileListing.marker;
                    if (delimiter1!=null) {
                        for (String fileInfo : delimiter1) {
                            ReturnList.add(fileInfo);
                            // System.out.println(fileInfo);
                        }
                    }
                    // System.out.println("strmarker=" + strmarker);
                }
                System.out.println("fileInfo.commonPrefixes");
            } else if (GetFileOrFolder == "GetFile") {
                String delimiter = "";
                String strprefix = strParentPath;
                String strmarker = null;
                FileListing fileListing = pubListQuery(strprefix, intgetLimit, delimiter, strmarker);
                strmarker = fileListing.marker;

                FileInfo[] items = fileListing.items;
                if (items!=null) {
                    for (FileInfo fileInfo : items) {
                        ReturnList.add(fileInfo.key);
                        // System.out.println(fileInfo.key);
                    }
                }
                while (!(strmarker == null)) {
                    fileListing = pubListQuery(strprefix, intgetLimit, delimiter, strmarker);
                    items = fileListing.items;
                    strmarker = fileListing.marker;
                    if (items!=null) {
                        for (FileInfo fileInfo : items) {
                            ReturnList.add(fileInfo.key);
                            // System.out.println(fileInfo.key);
                        }
                    }
                    // System.out.println("strmarker=" + strmarker);
                }
                System.out.println("fileInfo.items");
            } else {

            }
/*
            FileInfo[] items = fileListing.items;
            for (FileInfo fileInfo : items) {
                System.out.println(fileInfo.key);
            }*/
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            throw e;
        }

        return ReturnList;
    }

}
