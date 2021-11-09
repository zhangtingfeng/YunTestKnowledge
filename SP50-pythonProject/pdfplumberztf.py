import time

#import pdfplumber
#from PIL import Image
# import pandas as pd
#from scikitlearn import process_tfidf_similarity, process_indexIF


def checkpdf(Constpath, argpath, keyWord,page):
    pdfplumberopen = []

    if (page == 'page'):

        with pdfplumber.open(argpath) as pdf_file:
            content = ''
            # for i in range(len(pdf.pages)):
            #    # 读取PDF文档第i+1页
            #    page = pdf.pages[i]

            #  page.extract_text()函数即读取文本内容，下面这步是去掉文档最下面的页码
            #    page_content = '\n'.join(page.extract_text().split('\n')[:-1])
            #    content = content + page_content
            # pdf_image_reader = PyPDF2.PdfFileReader(open(argpath, "rb"))
            # print(pdf_image_reader.getNumPages())

            for inum in range(len(pdf_file.pages)):
                # content = pdf_file.pages[inum].extract_text().split('\n')[:-1]
                OutPath = argpath[0:len(argpath) - 4]


                textFileContent = OutPath + '/' + 'psReport_%s.txt' % inum
                content = ''
                f = open(textFileContent, encoding="utf-8")
                line = f.readline().replace('\n','')
                content = f.read().replace('\n', '')
                f.close()


                # content = pdf_file.pages[inum].extract_text().replace('\n', '')
                # 提取图片

                # 提取以上解析结果中，“地方法规”和“2.其他有关资料”之间的内容
                # result = content.split('地方法规列举如下：')[1].split('2.其他有关资料')[0]
                aprocess_tfidf_similarity = process_tfidf_similarity(keyWord, content)
                process_indexIFPos = process_indexIF(keyWord, content)
                if aprocess_tfidf_similarity > 0 or process_indexIFPos > -1:
                    repath = argpath[len(Constpath):len(argpath)]
                    ab = {'process_tfidf_similarity': aprocess_tfidf_similarity,
                          'process_indexIFPos': process_indexIFPos,
                          'Page': inum,
                          'path': repath
                          }
                    # print(argpath)
                    # print(inum)
                    pdfplumberopen.append(ab)
    elif (page == 'pages'):
        OutPath = argpath[0:len(argpath) - 4]
        textFileContent = OutPath + '.txt'
        content = ''
        f = open(textFileContent, encoding="utf-8")
        content = f.read().replace('\n', '')
        f.close()

        aprocess_tfidf_similarity = process_tfidf_similarity(keyWord, content)
        process_indexIFPos = process_indexIF(keyWord, content)
        if aprocess_tfidf_similarity > 0 or process_indexIFPos > -1:
            repath = argpath[len(Constpath):len(argpath)]
            ab = {'process_tfidf_similarity': aprocess_tfidf_similarity,
                  'process_indexIFPos': process_indexIFPos,
                  'Page': -1,
                  'path': repath
                  }
            # print(argpath)
            # print(inum)
            pdfplumberopen.append(ab)

    return (pdfplumberopen)

# print(checkpdf())
# pdfplumber.open("c://tmp/word.pdf")
