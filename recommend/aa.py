import pymysql
from wordcloud import WordCloud
from mysql.connector import MySQLConnection, Error
import pymysql
from wordcloud import WordCloud
import threading
import random
import numpy as np
import copy
from collections import Counter
import pytagcloud
from PIL import Image
import tempfile
email='c@naver.com'
total=[[1.0,0.0,0.0,0.0,0.0,0.0,1.5]]
t_list = list(total)
print("Dddddddddd")
print(t_list)
total_list = t_list[0]
# total_list=[0, 16.0, 5.0, 5.5, 0, 0, 0]
print(total_list)
cloud_catagory = []
##rating 합산 별로 값 반복하기. (word cloud 빈도수를 기준으로 단어 크기를 띄어주는 방법을 이용하기위하여)
for i in range(1, int(total_list[0])):
    cloud_catagory.append("농작물 경작")
for i in range(1, int(total_list[1])):
    cloud_catagory.append("공예(만들기)")
for i in range(1, int(total_list[2])):
    cloud_catagory.append("음식체험")
for i in range(1, int(total_list[3])):
    cloud_catagory.append("전통문화")
for i in range(1, int(total_list[4])):
    cloud_catagory.append("자연생태")
for i in range(1, int(total_list[5])):
    cloud_catagory.append("건강레포츠")
for i in range(1, int(total_list[6])):
    cloud_catagory.append("산·어촌 생활")

print(cloud_catagory)
# 각 카테고리별 빈도 count를 dictionary 형태로 만들어줌 ex '산·어촌 생활': 9,
count = Counter(cloud_catagory)
print(count)
print(type(count))
tags = count.most_common(7)
taglist = pytagcloud.make_tags(tags, maxsize=60)
print(taglist)
#b="wordcloud"+email+".jpg"
b="wordcloud.jpg"
pytagcloud.create_tag_image(taglist, b, size=(600, 500), fontname='Noto Sans CJK',
                            layout=pytagcloud.LAYOUT_MOST_HORIZONTAL)


host = 'awsdb.cz2vhvaboadi.ap-northeast-2.rds.amazonaws.com'
user = "gongmo"
password = "gongmo2018"
dbname = "innodb"

my_list=['A100081', 'A41245', 'A40007', 'A10376', 'A40224', 'A41073']
#my_list를 구하고 my_list를 db에 올리기
s=""
for i in my_list:
    s=s+str(i)+","
last=email+","+s
print(last)
print(len(last))



""""conn = pymysql.connect(host, user, password, dbname, charset="utf8")
cursor = conn.cursor(pymysql.cursors.DictCursor)
cursor.execute("select * from MyImage")
data=cursor.fetchall()
print("간나ㅏ난")
print(data)


with open(b, "rb") as f:
    # fin = codecs.open("wordcloud.jpg","rb")
    contents = f.read()
image_email=[]

for row2 in data:
    temp = []
    image_email.append(row2['email'])


#이전에 있는 데이터 이면 email존재하면
# image 바꿀것, 추천id도 바꿀 것
if (email in image_email):
    try:
        # email갖는 애의
        with conn.cursor() as cursor:
            sql = 'UPDATE MyImage SET image = %s WHERE email = %s'
            sql1='UPDATE MyImage SET vil1 = %s WHERE email = %s'
            sql2 = 'UPDATE MyImage SET vil2 = %s WHERE email = %s'
            sql3 = 'UPDATE MyImage SET vil3 = %s WHERE email = %s'
            sql4 = 'UPDATE MyImage SET vil4 = %s WHERE email = %s'
            sql5 = 'UPDATE MyImage SET vil5 = %s WHERE email = %s'
            sql6 = 'UPDATE MyImage SET vil6 = %s WHERE email = %s'
            cursor.execute(sql, (contents, email))
            cursor.execute(sql1,(my_list[0],email))
            cursor.execute(sql2, (my_list[1], email))
            cursor.execute(sql3, (my_list[2], email))
            cursor.execute(sql4, (my_list[3], email))
            cursor.execute(sql5, (my_list[4], email))
            cursor.execute(sql6, (my_list[5], email))
        conn.commit()
        # 1 (last insert id)
    except Error as e:
        print(e)
    finally:
        cursor.close()

else:
    try:
        with conn.cursor() as cursor:
            sql = 'INSERT INTO MyImage(email,image,vil1,vil2,vil3,vil4,vil5,vil6) VALUES (%s, %s,%s,%s,%s,%s,%s,%s)'
            cursor.execute(sql, (email, contents, my_list[0],my_list[1],my_list[2],my_list[3],my_list[4],my_list[5]))
        conn.commit()
        # 1 (last insert id)
    except Error as e:
        print(e)
    finally:
            cursor.close() 
"""
