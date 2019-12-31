import pymysql
import random
import copy
from collections import Counter
import pytagcloud
import threading
from threading import  Thread


class connect:
    host = 'awsdb.cz2vhvaboadi.ap-northeast-2.rds.amazonaws.com'
    user = "gongmo"
    password = "gongmo2018"
    dbname = "innodb"
    """!!!!!!!!!!!얘네들 나중에 바꿔줘야함!!!!!!!!!!!!!!!!"""
    register_size=3    # MyPage Table 원소 갯수
    review_size=17     # Review Table 원소 갯수
    category=[['A41316', 'A10991', 'A10103', 'A41357', 'A40187', 'A40040', 'A40674', 'A10015', 'A10016', 'A40033', 'A40033', 'A41404', 'A10115', 'A40795', 'A40645', 'A100036', 'A40277', 'A20296', 'A41209', 'A10338', 'A40104', 'A40574', 'A10161', 'A41209', 'A40017', 'A11453', 'A40125', 'A11652', 'A41357', 'A20217', 'A100076', 'A10218', 'A11371', 'A40286', 'A10149', 'A10015', 'A20320', 'A10012', 'A40336', 'A20233', 'A40232'], ['A40657', 'A40657', 'A41332', 'A12111', 'A10103', 'A10103', 'A10103', 'A40187', 'A40702', 'A40702', 'A10101', 'A20286', 'A10019', 'A41318', 'A41318', 'A40419', 'A40141', 'A40423', 'A40423', 'A100060', 'A40352', 'A40352', 'A40305', 'A10189', 'A41245', 'A10407', 'A40179', 'A40179', 'A40179', 'A40179', 'A40047', 'A20226', 'A40007', 'A10015', 'A10020', 'A40562', 'A100095', 'A40361', 'A40047', 'A20322', 'A20322', 'A40131', 'A40893', 'A40053', 'A10016', 'A41350', 'A10119', 'A10047', 'A10111', 'A41276', 'A40470', 'A10140', 'A20239', 'A10100', 'A41404', 'A10115', 'A11013', 'A11013', 'A11013', 'A40085', 'A40645', 'A40781', 'A40781', 'A40574', 'A100092', 'A40277', 'A40277', 'A20193', 'A40272', 'A40130', 'A20296', 'A100123', 'A100095', 'A40110', 'A10376', 'A40018', 'A10338', 'A10338', 'A10338', 'A10338', 'A40083', 'A20260', 'A40283', 'A40283', 'A40459', 'A20218', 'A40104', 'A20218', 'A40048', 'A40230', 'A12111', 'A12111', 'A12663', 'A100082', 'A100082', 'A100082', 'A40125', 'A40416', 'A10120', 'A20399', 'A100054', 'A40061', 'A10416', 'A100074', 'A40119', 'A100084', 'A40699', 'A40284', 'A40076', 'A40076', 'A20307', 'A10162', 'A40425', 'A40645', 'A10550', 'A40012', 'A12665', 'A40132', 'A40096', 'A20217', 'A40048', 'A20084', 'A20328', 'A20331', 'A20329', 'A10106', 'A41314', 'A41314', 'A41314', 'A10218', 'A10218', 'A40284', 'A40284', 'A41211', 'A40291', 'A10047', 'A10047', 'A10047', 'A41394', 'A41394', 'A40257', 'A40257', 'A40258', 'A40489', 'A40179', 'A10149', 'A40033', 'A12431', 'A100095', 'A40068', 'A40008', 'A40008', 'A40389', 'A100052', 'A100052', 'A100082', 'A41341', 'A10335', 'A10335', 'A41213', 'A41211', 'A40223', 'A40223', 'A40223', 'A40223', 'A41034', 'A41034', 'A10429', 'A40729', 'A100086', 'A10410', 'A10102', 'A40802', 'A40336', 'A100097', 'A100097', 'A40357', 'A100084', 'A20029', 'A40199', 'A20029', 'A100063', 'A40774', 'A12101', 'A40699', 'A2', 'A12135', 'A40131', 'A40162', 'A40674', 'A40674', 'A20181', 'A100129', 'A10424', 'A40026', 'A100111', 'A40735', 'A40068', 'A12431', 'A40068', 'A12138', 'A20248', 'A41276', 'A40047', 'A40224', 'A40851', 'A40037'], ['A41079', 'A12135', 'A12135', 'A40130', 'A12112', 'A100101', 'A100101', 'A41138', 'A40187', 'A41211', 'A40074', 'A40074', 'A10019', 'A41318', 'A41318', 'A40141', 'A41273', 'A40423', 'A40423', 'A40423', 'A40423', 'A41402', 'A41402', 'A12112', 'A40389', 'A20399', 'A100127', 'A40350', 'A40350', 'A10541', 'A10541', 'A10428', 'A10161', 'A10790', 'A10790', 'A10790', 'A10790', 'A10790', 'A10189', 'A40070', 'A40179', 'A40179', 'A40421', 'A40194', 'A40194', 'A12112', 'A10015', 'A40562', 'A40361', 'A40047', 'A40225', 'A40225', 'A40893', 'A40361', 'A20380', 'A40070', 'A40070', 'A40070', 'A41359', 'A41359', 'A40699', 'A100082', 'A100082', 'A100082', 'A40074', 'A10016', 'A10047', 'A10119', 'A10119', 'A10119', 'A10111', 'A10111', 'A41276', 'A10453', 'A40344', 'A40470', 'A41404', 'A12112', 'A12113', 'A41404', 'A40574', 'A11013', 'A10434', 'A40085', 'A40795', 'A40645', 'A40645', 'A40645', 'A40065', 'A40065', 'A40065', 'A40781', 'A40781', 'A10428', 'A40782', 'A41336', 'A20327', 'A20327', 'A41278', 'A40279', 'A40890', 'A40277', 'A10122', 'A20193', 'A20194', 'A40272', 'A40272', 'A40130', 'A40959', 'A41317', 'A100056', 'A40491', 'A40110', 'A40169', 'A40110', 'A10376', 'A40190', 'A41278', 'A40387', 'A40782', 'A20083', 'A20083', 'A20134', 'A100101', 'A100101', 'A10338', 'A12099', 'A41201', 'A40283', 'A40459', 'A20218', 'A40779', 'A40077', 'A12111', 'A12663', 'A12663', 'A40152', 'A100082', 'A40145', 'A100132', 'A40230', 'A40028', 'A11453', 'A40125', 'A40125', 'A40416', 'A10120', 'A10120', 'A10120', 'A100072', 'A100072', 'A100072', 'A20002', 'A100054', 'A41287', 'A20002', 'A12112', 'A41340', 'A41287', 'A40073', 'A40107', 'A40107', 'A40114', 'A40160', 'A11652', 'A40700', 'A40700', 'A10074', 'A10074', 'A10074', 'A40699', 'A10163', 'A40305', 'A10116', 'A40425', 'A40718', 'A40640', 'A10550', 'A10100', 'A10140', 'A20217', 'A40012', 'A12665', 'A100127', 'A40096', 'A10218', 'A10162', 'A10162', 'A100076', 'A100076', 'A20380', 'A100050', 'A100084', 'A20331', 'A10106', 'A10106', 'A10106', 'A40349', 'A100084', 'A10218', 'A40286', 'A40284', 'A40284', 'A40167', 'A20181', 'A40257', 'A10106', 'A40257', 'A40116', 'A40116', 'A100127', 'A40959', 'A10149', 'A10149', 'A40121', 'A40890', 'A40033', 'A40033', 'A40033', 'A2', 'A10437', 'A10437', 'A10437', 'A12663', 'A10437', 'A10416', 'A20260', 'A40008', 'A40008', 'A20260', 'A40389', 'A40389', 'A40389', 'A10101', 'A10101', 'A10012', 'A40224', 'A100052', 'A100052', 'A100052', 'A40470', 'A100073', 'A100073', 'A40742', 'A40307', 'A40307', 'A40307', 'A40307', 'A20293', 'A20293', 'A10149', 'A41213', 'A100095', 'A10443', 'A10443', 'A10443', 'A10047', 'A10123', 'A41211', 'A40362', 'A40253', 'A40798', 'A40223', 'A40848', 'A40848', 'A40294', 'A40791', 'A20277', 'A10120', 'A10102', 'A20273', 'A40802', 'A100097', 'A40357', 'A40344', 'A40119', 'A12135', 'A40319', 'A20233', 'A20317', 'A100129', 'A41268', 'A100066', 'A12431', 'A40672', 'A40020', 'A41276', 'A40047', 'A40851', 'A40087', 'A40037', 'A20327'], ['A10005', 'A40299', 'A40187', 'A10015', 'A40782', 'A20293', 'A10019', 'A10019', 'A40419', 'A40419', 'A40423', 'A40423', 'A41402', 'A41402', 'A40305', 'A40605', 'A10541', 'A10428', 'A40194', 'A10020', 'A10407', 'A40284', 'A40074', 'A20322', 'A100065', 'A12431', 'A40131', 'A40131', 'A40893', 'A20380', 'A20380', 'A20380', 'A20380', 'A40070', 'A10126', 'A40008', 'A12097', 'A40074', 'A40074', 'A40074', 'A40074', 'A40782', 'A41276', 'A10140', 'A41404', 'A41404', 'A41404', 'A10115', 'A10115', 'A10434', 'A40674', 'A40795', 'A40795', 'A40795', 'A40795', 'A100098', 'A100098', 'A40645', 'A40645', 'A40781', 'A10100', 'A40675', 'A40675', 'A10335', 'A40070', 'A40169', 'A10338', 'A10378', 'A20320', 'A10416', 'A20218', 'A41273', 'A41307', 'A41307', 'A100133', 'A12663', 'A100082', 'A40145', 'A11453', 'A10120', 'A100072', 'A41287', 'A41340', 'A10416', 'A41287', 'A40114', 'A10000', 'A40160', 'A100084', 'A100096', 'A40640', 'A20307', 'A10100', 'A41317', 'A41317', 'A20084', 'A100062', 'A20215', 'A11371', 'A11371', 'A100060', 'A20331', 'A10106', 'A10197', 'A40349', 'A40250', 'A40291', 'A41394', 'A40860', 'A12096', 'A40258', 'A100127', 'A10149', 'A12431', 'A12431', 'A2', 'A10416', 'A40389', 'A10101', 'A10101', 'A40880', 'A20317', 'A40224', 'A40224', 'A40224', 'A20327', 'A20327', 'A40916', 'A100052', 'A100082', 'A40838', 'A40802', 'A11451', 'A100095', 'A40068', 'A20293', 'A10443', 'A10443', 'A10443', 'A10443', 'A40285', 'A20300', 'A20302', 'A40791', 'A40091', 'A40125', 'A10410', 'A20260', 'A10550', 'A40351', 'A40351', 'A40199', 'A40199', 'A40699', 'A40737', 'A40737', 'A2', 'A10115', 'A40675', 'A20181', 'A41268', 'A12431', 'A12431', 'A40005', 'A40250', 'A40250', 'A20248', 'A12133', 'A40672', 'A40020', 'A40020', 'A40994', 'A40224', 'A10103'], ['A41393', 'A41330', 'A12135', 'A41332', 'A41357', 'A41357', 'A40299', 'A41138', 'A40187', 'A40141', 'A40361', 'A41360', 'A41360', 'A20399', 'A100060', 'A10189', 'A10428', 'A10189', 'A10189', 'A40674', 'A20226', 'A40007', 'A10015', 'A10015', 'A10020', 'A10407', 'A10416', 'A40047', 'A40225', 'A40225', 'A40131', 'A40025', 'A40131', 'A100096', 'A100065', 'A40893', 'A20380', 'A20277', 'A41359', 'A40070', 'A40053', 'A40053', 'A40053', 'A40053', 'A40074', 'A10016', 'A41350', 'A40782', 'A41404', 'A10115', 'A10115', 'A12113', 'A40674', 'A10991', 'A40085', 'A40795', 'A10100', 'A10100', 'A10428', 'A100092', 'A100090', 'A10122', 'A40130', 'A40959', 'A40562', 'A40110', 'A40169', 'A10376', 'A40387', 'A40387', 'A40387', 'A40083', 'A40083', 'A40083', 'A12099', 'A40040', 'A10378', 'A20286', 'A20286', 'A11451', 'A10416', 'A20260', 'A20218', 'A40574', 'A41316', 'A40656', 'A12663', 'A40152', 'A100132', 'A100132', 'A11453', 'A11372', 'A40125', 'A40125', 'A40237', 'A10120', 'A100072', 'A41079', 'A10871', 'A20219', 'A12430', 'A12430', 'A12430', 'A12430', 'A41330', 'A40421', 'A40073', 'A10416', 'A41390', 'A40700', 'A40700', 'A10163', 'A40284', 'A20307', 'A20307', 'A40425', 'A40012', 'A40012', 'A20217', 'A40119', 'A10218', 'A10218', 'A12113', 'A10197', 'A10197', 'A10197', 'A41314', 'A10047', 'A40137', 'A40476', 'A40257', 'A40116', 'A40116', 'A40489', 'A40780', 'A41330', 'A10047', 'A40092', 'A10005', 'A12431', 'A40033', 'A40033', 'A40033', 'A40008', 'A40880', 'A10437', 'A40050', 'A40008', 'A40008', 'A10101', 'A20322', 'A40880', 'A40880', 'A20317', 'A20327', 'A20327', 'A12131', 'A40307', 'A100060', 'A100060', 'A40718', 'A20293', 'A10335', 'A10443', 'A10443', 'A11812', 'A40798', 'A40076', 'A40702', 'A40091', 'A10563', 'A40189', 'A40125', 'A10120', 'A100086', 'A20278', 'A20278', 'A40719', 'A40336', 'A40119', 'A20233', 'A20317', 'A40142', 'A20239', 'A40735', 'A40735', 'A40735', 'A40005', 'A40735', 'A40090', 'A40020', 'A40087', 'A10020', 'A10335'], ['A12135', 'A12135', 'A40130', 'A41273', 'A100101', 'A40702', 'A40702', 'A10019', 'A40419', 'A40419', 'A40419', 'A41273', 'A10196', 'A10790', 'A10407', 'A20226', 'A40691', 'A40007', 'A20322', 'A20322', 'A40074', 'A20239', 'A40890', 'A10122', 'A40169', 'A20134', 'A12099', 'A41321', 'A40459', 'A40574', 'A40574', 'A41273', 'A11632', 'A40702', 'A40702', 'A40416', 'A41394', 'A10416', 'A40419', 'A40721', 'A100096', 'A100096', 'A40012', 'A100076', 'A10218', 'A12431', 'A40033', 'A20198', 'A40389', 'A40880', 'A40109', 'A40802', 'A40307', 'A10443', 'A40702', 'A40719', 'A40119', 'A40131', 'A100036', 'A100036', 'A40735', 'A40735', 'A40735', 'A12133', 'A11532'], ['A41138', 'A40141', 'A20399', 'A10196', 'A10196', 'A40065', 'A10407', 'A20277', 'A40691', 'A40691', 'A40025', 'A40131', 'A40083', 'A40025', 'A40065', 'A10196', 'A100090', 'A40272', 'A40959', 'A40083', 'A41073', 'A41319', 'A40711', 'A40846', 'A40028', 'A40028', 'A40846', 'A40416', 'A11551', 'A41390', 'A40721', 'A41100', 'A41100', 'A41100', 'A11731', 'A41211', 'A41211', 'A40476', 'A20198', 'A20198', 'A20198', 'A10416', 'A41213', 'A40847', 'A40847', 'A40025', 'A10563', 'A40189', 'A40719']]
    mid = []
    mid1=[]

    def __init__(self):
        pass
    """2중 list 집어넣으면 카테고리 분류해주는 함수"""
    def classify(self, *save):
        total = [0, 0, 0, 0, 0, 0,0]
        new_list=list(save)
        #del(new_list[0])
        for i,j in new_list:
            for k in range(0, len(self.category)):
                if (i in self.category[k]):
                    total[k] = total[k] + j
                    break
        return total


    """선호하는 마을과 비슷한 마을 랜덤추출 -->정보 0개"""
    def choice0(self):
        m_list=list(self.mid)
        #f_list=m_list[0]
        print("fdhfjshdjadus")
        print(m_list)
        my_list=[]
        for i in range(0,2):
            num = random.choice(self.category[m_list[i]])
            for j in range(3):
                while num in my_list:
                    num = random.choice(self.category[m_list[i]])
                my_list.append(num)
        print(my_list)
        return my_list


    """선호하는 마을과 비슷한 마을 랜덤추출 -->정보 1개 & 2개이상"""
    def choice1(self,*my_list):
        t_list = list(my_list)
        m_list = list(self.mid1)
        c_list = copy.deepcopy(t_list[0])
        max_index=c_list.index(max(c_list)) #가장 큰 값의 index
        del(c_list[max_index])
        sec_index=t_list[0].index(max(c_list)) #2nd 큰값의 index
        my_list=[]
        if max(c_list)==0:   #1개에 대한 정보만 가짐 -->1st:2개  회원가입:4개
            num = random.choice(self.category[max_index])
            for i in range(0,1):  #1st -> 2개
                while num in my_list:
                    num = random.choice(self.category[max_index])
                my_list.append(num)
            for i in range(0, 1): #회원가입 -> 각각2개씩
                num = random.choice(self.category[m_list[i]])
                for j in range(2):
                    while num in my_list:
                        num = random.choice(self.category[m_list[i]])
                my_list.append(num)


        else:   #2개 이상의 카테고리 선호도 가짐 -->1st:3  2nd:2 회원가입:1
            num = random.choice(self.category[max_index])
            for i in range(0, 3):  # 1st -> 3개
                while num in my_list:
                    num = random.choice(self.category[max_index])
                my_list.append(num)
            for i in range(0, 2):  # 2nd -> 2개
                while num in my_list:
                    num = random.choice(self.category[max_index])
                my_list.append(num)
            #회원가입 정보가지고 1개 아이디 추출
            r_num=0
            r_num=random.randint(0,1)
            num = random.choice(self.category[m_list[r_num]])
            my_list.append(num)
        return my_list


    """wordcloud 만들기"""
    def make_wordcloud(self,*total):
        t_list = list(total)
        total_list=t_list[0]
        temp_list=[]
        #print(total_list)

        for i in total_list:
            if i ==0:
                temp_list.append(1*300)
            else:
                temp_list.append(i*300)

        #total_list=[0, 16.0, 5.0, 5.5, 0, 0, 0]
        cloud_catagory = []
        ##rating 합산 별로 값 반복하기. (word cloud 빈도수를 기준으로 단어 크기를 띄어주는 방법을 이용하기위하여)
        for i in range(1, int(temp_list[0])):
            cloud_catagory.append("농작물 경작")
        for i in range(1, int(temp_list[1])):
            cloud_catagory.append("공예(만들기)")
        for i in range(1, int(temp_list[2])):
            cloud_catagory.append("음식체험")
        for i in range(1, int(temp_list[3])):
            cloud_catagory.append("전통문화")
        for i in range(1, int(temp_list[4])):
            cloud_catagory.append("자연생태")
        for i in range(1, int(temp_list[5])):
            cloud_catagory.append("건강레포츠")
        for i in range(1, int(temp_list[6])):
            cloud_catagory.append("산·어촌 생활")
        # 각 카테고리별 빈도 count를 dictionary 형태로 만들어줌 ex '산·어촌 생활': 9,
        count = Counter(cloud_catagory)
        tags = count.most_common(7)
        taglist = pytagcloud.make_tags(tags, maxsize=45)
        pytagcloud.create_tag_image(taglist, 'wordcloud.jpg', size=(500, 200), fontname='Noto Sans CJK',
                                    layout=pytagcloud.LAYOUT_MOST_HORIZONTAL)

    #회원가입 하는 경우 :MyPage
    def register(self):
        self.mid = []
        try:
            conn = pymysql.connect(self.host, self.user, self.password, self.dbname, charset="utf8")
            cursor= conn.cursor(pymysql.cursors.DictCursor)
            cursor.execute("select * from Mypage")
            first_category = cursor.fetchall()
            s = len(first_category)  # 최근 db 데이터 갯수
            if (self.register_size != s):  # 데이터 갯수가 늘어나면
                sql0='SELECT * FROM Mypage ORDER BY date DESC limit 1'
                cursor.execute(sql0)
                result=cursor.fetchone()  #제일 최근에 들어온 데이터 빼내기
                self.register_size=self.register_size+1
                new = result['email']  # 새로운 데이터 빼오기 나중에 s-1로 바꿀것
                for row1 in first_category:
                    if (new == row1['email']):  # 새로추가된 데이터과 email 같은애들 list에 저장
                        if (row1['farm'] == 1):
                            self.mid.append(0)
                        if (row1['making'] == 1):
                            self.mid.append(1)
                        if (row1['food'] == 1):
                            self.mid.append(2)
                        if (row1['tradition'] == 1):
                            self.mid.append(3)
                        if (row1['nature'] == 1):
                            self.mid.append(4)
                        if (row1['health'] == 1):
                            self.mid.append(5)
                        if (row1['life'] == 1):
                            self.mid.append(6)
                        self.mid.append(7)
                my_list = self.choice0()
                #새로운 데이터에 대해서 그림 올려주기
                cursor1 = conn.cursor(pymysql.cursors.DictCursor)
                cursor1.execute("select * from MyImage")
                image_data = cursor1.fetchall()
                b = "GG.jpg"
                # wordcloud 열기
                with open(b, "rb") as f:
                    contents = f.read()
                try:
                    with conn.cursor() as cursor2:
                        sql = 'INSERT INTO MyImage(email,image,vil1,vil2,vil3,vil4,vil5,vil6) VALUES (%s, %s,%s,%s,%s,%s,%s,%s)'
                        cursor2.execute(sql, (new, contents, my_list[0], my_list[1], my_list[2], my_list[3], my_list[4], my_list[5]))
                    conn.commit()
                finally:
                        cursor1.close()
                print("새 정보 등록 완료")
        finally:
            cursor.close()
            conn.close()

        threading.Timer(0.5, self.register).start()
        #self.register_size = len(first_category)


    def review(self):
        save = []
        self.mid1 = []
        try:
            conn = pymysql.connect(self.host, self.user, self.password, self.dbname, charset="utf8")
            cursor = conn.cursor(pymysql.cursors.DictCursor)
            cursor.execute("select * from Review")
            data = cursor.fetchall()
            s = len(data)  # 최근 db 데이터 갯수
            # self.size = len(data) #1초 전 db데이터 갯수
            if (self.review_size != s):  # 데이터 갯수가 늘어나면
                self.review_size=self.review_size+1
                print(self.review_size)
                new = data[s-1]  # 나중에 s-1로 바꿀것
                # 개인데이터 불러오기
                for row in data:
                    a = []
                    if (new['email'] == row['email']):  # 새로추가된 데이터과 email 같은애들 list에 저장
                        a.append(row['villageId'])
                        a.append(row['rating'])
                        save.append(a)  # save=[[],[],[]]의 형태
                cursor1 = conn.cursor(pymysql.cursors.DictCursor)
                cursor1.execute("select * from Mypage")
                first_category = cursor1.fetchall()

                """회원가입 시 선호하는 태그 추출하기 """
                for row1 in first_category:
                    if (new['email'] == row1['email']):  # 새로추가된 데이터과 email 같은애들 list에 저장
                        if (row1['farm'] == 1):
                            self.mid1.append(0)
                        if (row1['making'] == 1):
                            self.mid1.append(1)
                        if (row1['food'] == 1):
                            self.mid1.append(2)
                        if (row1['tradition'] == 1):
                            self.mid1.append(3)
                        if (row1['nature'] == 1):
                            self.mid1.append(4)
                        if (row1['health'] == 1):
                            self.mid1.append(5)
                        if (row1['life'] == 1):
                            self.mid1.append(6)
                        self.mid1.append(7)

                """나중에 지울 것 """
                # self.choice0()
                my_list = self.choice1(self.classify(*save))

                self.make_wordcloud(self.classify(*save))

                b = "wordcloud.jpg"
                # wordcloud 열기
                with open(b, "rb") as f:
                    contents = f.read()

                # 이전에 있는 데이터 이면 email존재하면
                # image 바꿀것, 추천id도 바꿀 것
                #if (new['email'] in image_email):
                try:
                    # email갖는 애의
                    with conn.cursor() as cursor2:
                        sql = 'UPDATE MyImage SET image = %s WHERE email = %s'
                        sql1 = 'UPDATE MyImage SET vil1 = %s WHERE email = %s'
                        sql2 = 'UPDATE MyImage SET vil2 = %s WHERE email = %s'
                        sql3 = 'UPDATE MyImage SET vil3 = %s WHERE email = %s'
                        sql4 = 'UPDATE MyImage SET vil4 = %s WHERE email = %s'
                        sql5 = 'UPDATE MyImage SET vil5 = %s WHERE email = %s'
                        sql6 = 'UPDATE MyImage SET vil6 = %s WHERE email = %s'
                        cursor2.execute(sql, (contents, new['email']))
                        cursor2.execute(sql1, (my_list[0], new['email']))
                        cursor2.execute(sql2, (my_list[1], new['email']))
                        cursor2.execute(sql3, (my_list[2], new['email']))
                        cursor2.execute(sql4, (my_list[3], new['email']))
                        cursor2.execute(sql5, (my_list[4], new['email']))
                        cursor2.execute(sql6, (my_list[5], new['email']))
                    conn.commit()
                        # 1 (last insert id)
                except:
                    print("업데이트 에러")
                print("review등록 완료")
        finally:
            cursor.close()
            conn.close()

        threading.Timer(0.5, self.review).start()
        #self.size = len(data)


def main():
    at=connect()
    #at.register()
    #at.review()
    Thread(target=at.register()).start()
    Thread(target=at.review()).start()

if __name__=='__main__':
    main()


