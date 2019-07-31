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

##### 线程池 Future 是怎么实现线程之间的交互的？  
`FutureTask implements RunnableFuture`. 而 `RunnableFuture extends Runnable,Future`  
```  
FutureTask ft = new FutureTask(Runnable/Callable);  
new Thread(ft).start();  
```   
时，jvm新开线程，**执行FutureTask的run方法**接下来才是最重要的：  
而通过`FutureTask(Runnable/Callable)`构造出来的FutureTask，最终都会转为`FutureTask(Callable)`，在FutureTask的run方法中会执行Callable的`call()`并设置`call()`的返回值`set(result);`。而FutureTask的句柄`ft`保存在主线程中，所以我们能在主线程中通过`ft.get();`方法拿到`result`。当然在get方法中会判断当前的状态，或阻塞等待或直接返回。get方法也能传参，用来空值get等待时间`V get(long timeout, TimeUnit unit)`  
所以，Future实现线程通信的方式就是操作线程共享内存的区域的变量。

##### FutureTask 在设置返回值set(result);时为什么需要cas？  
还不清楚  
##### Thread.start()发生了什么？  
调用native方法，jvm会开启新线程并定位、执行`Runnable.run`方法




