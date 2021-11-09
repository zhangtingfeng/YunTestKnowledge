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
mutex = threading.Lock()
winTempPath = "C:/Temp/"
MulitthreadNum = 2
Constpath = 'E:/'
count = 0
avitoMp4List = [".avi", ".flv", ".mpeg", ".rm", ".mpg", ".rmvb", ".wmv", ".mov"]


class myThread(threading.Thread):
    def __init__(self, threadID):
        threading.Thread.__init__(self)
        self.threadID = threadID

    def run(self):
        print("开始线程：" + str(self.threadID))
        var = 1
        while var == 1:
            global count
            count = count + 1
            # mutex.acquire()  # 上锁
            getOneTask = []
            if (len(taskList) > 0):

                getOneTask = taskList[0]
                del taskList[0]
            else:
                var = 0
                print("退出线程：" + str(self.threadID))

            # mutex.release()  # 解锁
            if (len(getOneTask) > 0):
                print("线程开始：" + str(self.threadID) + " working at " + getOneTask[0])
                createMp4(getOneTask[0], getOneTask[1], self.threadID)

                print("线程结束：" + str(self.threadID) + "count=" + str(count) + " working at " + getOneTask[0])


def createMp4(input_file, out_file, markerthreadID):
    try:

        DataHead = time.strftime('%Y%m%d%H%M%S', time.localtime(int(time.time())))
        DataHead1 = ''.join(random.sample(
            ['z', 'y', 'x', 'w', 'v', 'u', 't', 's', 'r', 'q', 'p', 'o', 'n', 'm', 'l', 'k', 'j', 'i', 'h', 'g', 'f',
             'e', 'd', 'c', 'b', 'a'], 8))
        TempFilenale = winTempPath + DataHead + DataHead1 + ".mp4"

        TempSource = winTempPath + DataHead + DataHead1 + os.path.splitext(input_file)[1]
        shutil.copyfile(input_file, TempSource)
        os.chdir('C:/Works/gitworks202101SpringClound/SP50-pythonProject/ffmpeg-N/bin/')
        cmd = "ffmpeg -y -threads 0 -i %s %s" % (TempSource, TempFilenale)

        res = subprocess.call(cmd, shell=True)
        time.sleep(1)
        os.remove(TempSource)
        # shutil.move(TempSource, input_file)
        shutil.move(TempFilenale, out_file)
        os.remove(input_file)

        # print("线程：" + str(markerthreadID) + " working at " + str(interesting1 / HHHAnyWhile))
    except Exception as e:
        # 访问异常的错误编号和详细信息
        print(e.args)
        print(str(e))
        print(repr(e))
        print(input_file + ' is error')
    else:

        print(out_file + ' is ok')


if __name__ == '__main__':

    # //createPdf(letddd, "d:/1.pdf")

    pdfFilelist = []
    g = os.walk(Constpath)
    for path, dir_list, file_list in g:
        for file_name in file_list:
            ospath = os.path.join(path, file_name).replace('\\', '/')
            if not ("RECYCLE.BIN" in ospath):  # 判断文件扩展名是否为“.jpg   .avi, .flv .mpeg .rm .mpg rmvb .wmv”
                # if os.path.splitext(ospath)[1] == ".docx" or os.path.splitext(ospath)[1] == ".doc" or os.path.splitext(ospath)[1] == ".pptx" or os.path.splitext(ospath)[1] == ".ppt" :  # 判断文件扩展名是否为“.jpg”
                if os.path.splitext(ospath)[1] in avitoMp4List:
                    pdfFilelist.append(ospath)
                    count = count + 1
                    print("get file" + ospath + " count=" + str(count))

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
            taskList.append([sourcepath, depath]);
    threadList = []
    count = 0
    for num in range(0, MulitthreadNum):  # 迭代 10 到 20 之间的数字
        thread = myThread(num)
        threadList.append(thread)
    for numthread in threadList:  # 迭代 10 到 20 之间的数字
        numthread.start()
    for numthread in threadList:  # 迭代 10 到 20 之间的数字
        numthread.join()

print(' is over')

# pyinstaller -F word2pdf.py
