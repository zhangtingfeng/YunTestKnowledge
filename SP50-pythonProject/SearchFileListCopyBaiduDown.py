#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import shutil

import time


pdfFilelist = []


def SearchList(fromfoler, tofoler,outExpand):
    global pdfFilelist
    pdfFilelist = []
    g = os.walk(fromfoler)
    for path, dir_list, file_list in g:
        for file_name in file_list:
            ospath = os.path.join(path, file_name).replace('\\', '/')
            if os.path.splitext(ospath)[1] != outExpand:  # 判断文件扩展名是否为“.jpg”
                newpath=tofoler+path.replace(fromfoler,"")
                if not os.path.exists(newpath):
                    os.makedirs(newpath)
                despath = os.path.join(newpath, file_name).replace('\\', '/')   # .jpg为你的文件类型，即后缀名，读者自行修改
                shutil.move(ospath, despath)
                print(despath)
            # s.append()

    # CurSeacrhList(Constpath)
    # return returnList

# pyinstaller -F SearchFileList.py


if __name__ == '__main__':
    fromfoler="C:/360Downloads"
    tofoler = "C:/tmp/to"
    outExpand =".downloading"
    count = 0
    while (count < 9):
        time.sleep(60)  # 暂停 1 秒
        print('The count is:', count)
        count = count + 1
        SearchList(fromfoler, tofoler, outExpand)
