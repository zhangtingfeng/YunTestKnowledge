'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
#作者：cacho_37967865
#博客：https://blog.csdn.net/sinat_37967865
#文件：baiduAI.py
#日期：2019-06-18
#备注：Python利用百度AI进行文字识别, pip install baidu-aip
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

import base64
import os

from aip import AipOcr

import pytesseract
from PIL import Image

# pip3 install pillow


# 定义常量
APP_ID = '24416438'
API_KEY = '24RPyGdfwBteOcGQX1GrcwIj'
SECRET_KEY = 'xwQVEMPHudObHYcwB5FcSWuLXIgA6aqv'


HuaweiSesison = ""
# 初始化AipFace对象
aipOcr = AipOcr(APP_ID, API_KEY, SECRET_KEY)


# 打开图片
def get_file_content(filePath):
    with open(filePath, 'rb') as fp:
        return fp.read()

# 若img.save()报错 cannot write mode RGBA as JPEG
# 则img = Image.open(image_path).convert('RGB')
def image_to_base64(image_path):
    image = open(image_path, 'rb')
    image_read = image.read()
    encoded_string= base64.b64encode(image_read)
    ddddd=encoded_string.decode('utf-8')
    # UUU = base64.b64decode(encoded_string)
    # image_64_encode = base64.encodestring(image_read)  # encodestring also works aswell as decodestring
    '''
    img = Image.open(image_path)
    output_buffer = BytesIO()
    img.save(output_buffer, format='JPEG')
    byte_data = output_buffer.getvalue()
    base64_str = base64.b64encode(byte_data)
    '''
    return ddddd


# 调用通用文字识别接口
def basicGeneral(file):
    """ 如果有可选参数 """
    options = {}
    options["detect_direction"] = "true"  # 检测朝向
    options["detect_language"] = "true"  # 检测语言
    result = aipOcr.basicGeneral(file, options)
    return (result)


# 通用文字识别（高精度版）
def basicAccurate(file):
    options = {}
    options["detect_direction"] = "true"  # 检测朝向
    options["detect_language"] = "true"  # 检测语言
    result = aipOcr.basicAccurate(file, options)
    return (result)


# 识别一些网络上背景复杂，特殊字体的文字。
def webImage(file):
    options = {}
    options["detect_direction"] = "true"  # 检测朝向
    options["detect_language"] = "true"  # 检测语言
    result = aipOcr.webImage(file, options)
    return (result)





def main(pngpath, outTextPath):
    file = get_file_content(pngpath)
    result = basicGeneral(file)
    # print(result)
    pageText = ''

    f = open(outTextPath, 'a', encoding="utf-8")
    try:

        # f.writelines(['the fourth writing...\n'])
        boolwords_result = 'words_result' in result
        if boolwords_result:
            for word in result['words_result']:
                # f.writelines(word['words'])
                dimword = word['words']
                pageText += dimword + '\n'
                f.writelines([dimword, '\n'])
                # print(word['words'])
    finally:
        f.close()
    return pageText

# 调用通用文字识别接口
def basicGeneralHuaWeiToken():
    """ 如果有可选参数 """
    options = {}
    options["name"] = "hw18082197"  # 检测朝向
    options["password"] = "oliver000"  # 检测语言
    result = aipOcr.HuaWeiToken(options)
    return (result)


# 调用通用文字识别接口
def basicGeneralHuaWeiText(base64,argHuaweiSesison):
    """ 如果有可选参数 """

    options = {}
    options["X-Subject-Token"] = argHuaweiSesison  # 检测朝向
    result = aipOcr.HuaWeiText(base64,options)
    return (result)


def TesseractText(pngpath, outTextPath):
    text = pytesseract.image_to_string(Image.open(pngpath), lang='chi_sim+eng')
    f = open(outTextPath, 'a', encoding="utf-8")
    try:

        # f.writelines(['the fourth writing...\n'])

                f.writelines([text, '\n'])
                # print(word['words'])
    finally:
        f.close()
    return text




def mainHuaWei(pngpath, outTextPath):
    fileBase64 = image_to_base64(pngpath)
    # if (HuaweiSesison == ''):
    global HuaweiSesison
    if (HuaweiSesison == ""):
        result = basicGeneralHuaWeiToken()
        HuaweiSesison = result


    result = basicGeneralHuaWeiText(fileBase64,HuaweiSesison)

    # print(result)
    pageText = ''

    f = open(outTextPath, 'a', encoding="utf-8")
    try:

        # f.writelines(['the fourth writing...\n'])
        boolwords_result = 'result' in result
        if boolwords_result:
            for word in result['result']['words_block_list']:
                # f.writelines(word['words'])
                dimword = word['words']
                pageText += dimword + '\n'
                f.writelines([dimword, '\n'])
                # print(word['words'])
    finally:
        f.close()
    return pageText


if __name__ == '__main__':
    mainHuaWei("C:/tmp/Newfolder/202105200554401621504480563.jpg" , "C:/tmp/Newfolder/202105200554401621504480563.txt")
