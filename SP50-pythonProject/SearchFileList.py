#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import sys


from pdfplumberztf import checkpdf
from PyMuPDF import pdf2image2FromPDF

pdfFilelist = []


def SearchList(Constpath, keyWord,page):
    global pdfFilelist
    pdfFilelist = []
    g = os.walk(Constpath)
    for path, dir_list, file_list in g:
        for file_name in file_list:
            ospath = os.path.join(path, file_name).replace('\\', '/')
            if os.path.splitext(ospath)[1] == ".pdf":  # 判断文件扩展名是否为“.jpg”
                pdfFilelist.append(ospath)
            # s.append()

    for path in pdfFilelist:
        pdf2image2FromPDF(path)  # 作文本

    returnList = []
    for path in pdfFilelist:
        for path_name in checkpdf(Constpath, path, keyWord,page):
            returnList.append(path_name)
            # s.append(os.path.join(path, file_name))

    # CurSeacrhList(Constpath)
    return returnList

# pyinstaller -F SearchFileList.py


if __name__ == '__main__':
    SearchList("C:/tmp/Newfolder/New folder", "合同", "page")