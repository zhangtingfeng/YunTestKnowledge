
#用于副本操作
# 封装Excel类，用于操作excel文件，管理数据并CRUD
#Excel表需提前建好 表头也需提前在Excel表中写好
import xlrd
import xlwt
import os

workbook = xlrd.open_workbook(u'China_SRD_CP_Newgen_Mapping03.xlsx')
content = workbook.sheet_by_name("Local CP")
ord_list = []
for rownum in range(content.nrows):
    ord_list.append(content.row_values(rownum))
