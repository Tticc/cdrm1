# cdrm1 a springboot service
## message  
springboot 2.1.6.RELEASE，为了匹配mysql 8.x  
redis  
jpa  
mysql 8.x + oracle  
swagger  


## oracle jdbc包安装方式  
### Windows  
命令如下  
mvn install:install-file -Dfile=C:\Users\wenc\.m2\repository\oracle\ojdbc6\12.1.0.2\ojdbc6-12.1.0.2.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=12.1.0.2 -Dpackaging=jar  

解释  
mvn install:install-file   
-Dfile=C:\Users\wenc\.m2\repository\oracle\ojdbc6\12.1.0.2\ojdbc6-12.1.0.2.jar //jar文件  
-DgroupId=com.oracle //<groupId>com.oracle</groupId>  
-DartifactId=ojdbc6   //<artifactId>ojdbc6</artifactId>  
-Dversion=12.1.0.2   //<version>12.1.0.2</version>  
-Dpackaging=jar  


# Linux  
## Linux Mysql  
1.查看mysql配置
cat /etc/mysql/my.cnf
如下：
!includedir /etc/mysql/conf.d/
!includedir /etc/mysql/mysql.conf.d/
2.修改 /etc/mysql/mysql.conf.d/mysqld.cnf, 在[mysqld]下添加 skip-grant-tables
保持，重启mysql服务
sudo service mysql status
sudo service mysql stop
sudo service mysql start

## Linux命令  
### 查找和杀进程  
ps -aux | grep eclipse  
ps -ef | grep eclipse  
直接查出PID  
pgrep eclipse  
强制杀死进程  
kill -s 9 PID  

# git
git rm -r dir
git commit -m "re dir"
git push -u origin master



# ？？？  
get load find  

spring 事务  
mysql 事务  
hibernate 事务  
http://www.yq1012.com/ThinkingInJava/  
