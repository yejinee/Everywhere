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


class connect:
    host = 'awsdb.cz2vhvaboadi.ap-northeast-2.rds.amazonaws.com'
    user = "gongmo"
    password = "gongmo2018"
    dbname = "innodb"
   # count = 0
    size=0
    def __init__(self):
        pass
    def set_first(self):
        pass
    def con(self):
        s=0
        save = []
        self.mid=[]
        try:
            """
            비교하면서 1초전과 1초후의 데이터 총 갯수가 같지 않으면 
            코드 실행 
            
            1.db접속
            2.
            """
            conn = pymysql.connect(self.host, self.user, self.password, self.dbname, charset="utf8")
            cursor = conn.cursor(pymysql.cursors.DictCursor)
            cursor.execute("select * from Review")
            data = cursor.fetchall()
            #self.size = len(data)  # 1초 전 db데이터 갯수
            s = len(data)  # 최근 db 데이터 갯수
            cursor1 = conn.cursor(pymysql.cursors.DictCursor)
            cursor1.execute("select * from Mypage")
            first_category = cursor1.fetchall()
            print(first_category)

            if (self.size != s):  # 데이터 갯수가 늘어나지 않으면
                print("dfjdhf")
            else:  # data 갯수가 늘면
                new = cursor.fetchone()
                print("1234")
        finally:
            cursor.close()
            conn.close()

        threading.Timer(3, self.con).start()
        self.size = len(data)


def main():
    at=connect()
    at.con()

if __name__=='__main__':
    main()









