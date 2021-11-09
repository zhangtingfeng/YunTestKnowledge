#!/usr/bin/env python
# -*- coding: utf-8 -*-
#coding=utf-8
import os
import sys
import zipfile
import rarfile
#from pdfplumberztf import checkpdf
#from PyMuPDF import pdf2image2FromPDF
import os.path
from pathlib import Path

# pdfFilelist = []
def rarfileaction(sourcepath,todirname):
    try:
        rf = rarfile.RarFile(sourcepath) # 待解压文件
        rf.extractall(todirname) # 解压到指定的目录（不填的话，默认为当前脚本所在的目录）
    finally:
        print("error " +sourcepath)

# pdfFilelist = []
def zipfileaction(sourcepath,todirname):
    '''
    基本格式：zipfile.ZipFile(filename[,mode[,compression[,allowZip64]]])
    mode：可选 r,w,a 代表不同的打开文件的方式；r 只读；w 重写；a 添加
    compression：指出这个 zipfile 用什么压缩方法，默认是 ZIP_STORED，另一种选择是 ZIP_DEFLATED；
    allowZip64：bool型变量，当设置为True时可以创建大于 2G 的 zip 文件，默认值 True；

    '''


    zip_file = zipfile.ZipFile(sourcepath)
    zip_list = zip_file.namelist()  # 得到压缩包里所有文件

    for f in zip_list:
        zip_file.extract(f, todirname)  # 循环解压文件到指定目录

    zip_file.close()  # 关闭文件，必须有，释放内存

def SearchList(Constpath, keyWord,page):
    global pdfFilelist
    pdfFilelist = []
    g = os.walk(Constpath)
    for path, dir_list, file_list in g:
        for file_name in file_list:
            ospath = os.path.join(path, file_name).replace('\\', '/')
            if os.path.splitext(ospath)[1] == ".rar":  # 判断文件扩展名是否为“.jpg”
                pdfFilelist.append(ospath)
            # s.append()
    intdddd=0
    for path in pdfFilelist:
        dirname, filename = os.path.split(path)
        letletfilename=os.path.splitext(filename)[0]
        intdddd=intdddd+1
        print(str(intdddd)+"/"+ str(len(pdfFilelist))+"   "+path)

        my_folder=dirname+"/"+letletfilename
        my_file = Path(my_folder)
        if not(my_file.is_dir()):
            rarfileaction(path,my_folder)
        #dorar=
        #pdf2image2FromPDF(path)  # 作文本

    #returnList = []
    #for path in pdfFilelist:
    #    ddd=1
            # s.append(os.path.join(path, file_name))

    # CurSeacrhList(Constpath)
    # return returnList

# pyinstaller -F SearchFileList.py
#2） 直接把unrar.exe放置到python目录：D:\Python27\Scripts

if __name__ == '__main__':
    SearchList("E:/网络小说txt格式集/二次元/电子书txt压缩包", "合同", "page")