import shutil

from pdf2image import convert_from_path, convert_from_bytes, pdfinfo_from_path
import tempfile
import os
import pdfplumber
from OCR import main,mainHuaWei,TesseractText
from pdf2image.exceptions import (
    PDFInfoNotInstalledError,
    PDFPageCountError,
    PDFSyntaxError
)


def pdf2image2FromPDF(pdfPath):
    OutPath = pdfPath[0:len(pdfPath) - 4]
    pdf2image2(pdfPath, OutPath)


def mkdir(path):
    folder = os.path.exists(path)

    if not folder:  # 判断是否存在文件夹如果不存在则创建为文件夹
        os.makedirs(path)  # makedirs 创建文件时如果路径不存在会创建这个路径
        print
        "---  new folder...  ---"
        print
        "---  OK  ---"

    else:
        print
        "---  There is this folder!  ---"


def pdf2image2(pdfPath, imagePath):
    outFullTxt = imagePath + '.txt'

    if os.path.exists(outFullTxt):
        return
    # 方法一：
    # convert_from_path('a.pdf', dpi=500, "output",fmt="JPEG",output_file="ok",thread_count=4)
    # 这会将a.pdf转换成在output文件夹下形如ok_线程id-页码.jpg的一些文件。
    # 若不指定thread_count则默认为1，并且在文件名中显示id. 这种转换是直接写入到磁盘上的，因此不会占用太多内存。

    # 方法三，也是最推荐的方法
    temppath = "C:/tmp/WindowsTemp/"
    mkdir(temppath)
    page_count = pdfinfo_from_path(pdfPath)["Pages"]
    AllPageCount = -1
    g = os.walk(imagePath)
    for path, dir_list, file_list in g:
        for file_name in file_list:
            ospath = os.path.join(path, file_name)
            if os.path.splitext(ospath)[1] == ".jpg":  # 判断文件扩展名是否为“.jpg”
                AllPageCount = AllPageCount + 1
    if (page_count != (AllPageCount + 1)):
        print(pdfPath)
        images_from_path = convert_from_path(pdfPath, fmt="jpeg", output_folder=temppath, dpi=150, thread_count=40)
        print("convert_from_path is over")
        if not os.path.exists(imagePath):
            os.makedirs(imagePath)

        for image in images_from_path:
            savepngpath = imagePath + '/' + 'psReport_%s.jpg' % images_from_path.index(image)
            if not os.path.exists(savepngpath):
                image.save(savepngpath, 'JPEG')
                print('savepngpath=%s is over' % savepngpath)
                AllPageCount = AllPageCount + 1

        shutil.rmtree(temppath, ignore_errors=True)

    AllText = ''

    # print(path)
    for i in range(0, (AllPageCount + 1)):
        savepngpath = imagePath + '/' + 'psReport_%s.jpg' % i
        outTextPath = imagePath + '/' + 'psReport_%s.txt' % i
        print(i)
        if not os.path.exists(outTextPath):
            pageText = TesseractText(savepngpath, outTextPath)
            AllText = AllText + pageText + '\n\n\n'
        else:
            f = open(outTextPath, encoding="utf-8")
            pageText = f.readline()
            AllText = AllText + pageText + '\n\n\n'
            f.close()

    # AllText = AllText.replace('\n', '')
    f = open(outFullTxt, 'a', encoding="utf-8")
    f.writelines([AllText])
    f.close()

    # print(images_from_path)

# pdf2image2("C:\\tmp\\Newfolder\\[羡慕与嫉妒：深层心理分析(精神健康系列)].(瑞士)卡斯特.著,陈瑛.译.影印版.pdf", "C:\\tmp\\Newfolder\\[羡慕与嫉妒：深层心理分析(精神健康系列)].(瑞士)卡斯特.著,陈瑛.译.影印版\\", 1)
