#1. centos 설치 + 패키지 업데이트 + 만든사람 표기
FROM ansible/centos7-ansible
MAINTAINER soulgoon@gmail.com
RUN yum -y update

#2. openjdk 설치
RUN yum install -y java-11-openjdk-devel.x86_64

#3. 도커 컨테이너 호스트와 공유한 디렉토리 지정

#4. 도커 이미지를 실행할시 실해될 스크립트

CMD mkdir /deploy/makingboard

COPY ./deploy/makingboard.war /deploy/makingboard/makingboard.war

#COPY ./start-server.sh /usr/local/bin
#RUN ln -s /usr/local/bin/start-server.sh /start-server.sh
#CMD ["start-server.sh", $PATH]

CMD java -jar /deploy/makingboard/makingboard.war
