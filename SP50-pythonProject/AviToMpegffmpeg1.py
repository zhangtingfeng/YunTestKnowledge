import os
import subprocess
import threading
import time
import random
import shutil


exitFlag = 0
taskList = []
# 创建一个互斥锁
# 默认是未上锁的状态




def createMp4(input_file, tofoler,fromfoler,depath):
    try:
        depath=depath.replace('\\', '/')
        aFileName = os.path.basename(input_file.replace('\\', '/'))  # 带后缀的文件名
        newpath = tofoler + input_file.replace('\\', '/').replace(fromfoler, "")
        if not os.path.exists(newpath.replace(aFileName, '')):
            os.makedirs(newpath.replace(aFileName, ''))
        NewMp4File=os.path.splitext(newpath)[0]+".mp4"
        if os.path.exists(NewMp4File):
            shutil.move(NewMp4File, depath)
            os.remove(input_file)
            os.remove(newpath)
            print(input_file + ' move back is ok')
            #return
        else:
            shutil.copyfile(input_file, newpath)



            # print("线程：" + str(markerthreadID) + " working at " + str(interesting1 / HHHAnyWhile))
    except Exception as e:
        # 访问异常的错误编号和详细信息
        print(e.args)
        print(str(e))
        print(repr(e))
        print(input_file + ' is error')
    else:

        print(depath + ' is ok')


if __name__ == '__main__':

    # //createPdf(letddd, "d:/1.pdf")
    fromfoler = "I:/"
    tofoler = "H:/MP4/"
    int100=0
    pdfFilelist = []
    g = os.walk(fromfoler)
    exitfor = False
    for path, dir_list, file_list in g:
        for file_name in file_list:
            ospath = os.path.join(path, file_name).replace('\\', '/')
            if not ("RECYCLE.BIN" in ospath):  # 判断文件扩展名是否为“.jpg”
                # if os.path.splitext(ospath)[1] == ".docx" or os.path.splitext(ospath)[1] == ".doc" or os.path.splitext(ospath)[1] == ".pptx" or os.path.splitext(ospath)[1] == ".ppt" :  # 判断文件扩展名是否为“.jpg”
                if os.path.splitext(ospath)[1] == ".avi" or os.path.splitext(ospath)[1] == ".flv" or \
                        os.path.splitext(ospath)[1] == ".mpeg" or os.path.splitext(ospath)[1] == ".rm" or \
                        os.path.splitext(ospath)[1] == ".mpg" or \
                        os.path.splitext(ospath)[1] == ".rmvb" or os.path.splitext(ospath)[1] == ".wmv":
                    pdfFilelist.append(ospath)
                    print("get file"+str(int100)+"  "+ospath)
                    int100=int100+1
                    if (int100>450):
                        exitfor = True
                        break
        if exitfor:
            break

    for path in pdfFilelist:
        sourcepath = path
        depath = os.path.splitext(sourcepath)[0] + '.mp4'
        if os.path.exists(depath):  # 如果文件存在
            # 删除文件，可使用以下两种方法。
            os.remove(depath)
        sourcepath = sourcepath.replace('/', '\\')
        depath = depath.replace('/', '\\')
        if os.path.exists(depath):  # 如果文件存在
            # 删除文件，可使用以下两种方法。
            os.remove(depath)
        print(sourcepath)
        if os.path.exists(sourcepath):
            createMp4(sourcepath, tofoler,fromfoler,depath);


print(' is over')

# pyinstaller -F word2pdf.py
