# cdrm1 a springboot service
## message  
springboot 2.1.6.RELEASE，为了匹配mysql 8.x  
redis  
jpa  
mysql 8.x + oracle  
swagger  
多数据源配置失败  


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
# 查找进程PID
ps -aux | grep eclipse  
ps -ef | grep eclipse  
pgrep eclipse  # 直接查出PID  
kill -s 9 PID  # 强制杀死进程  
### 查找端口
netstat -lntup  # 查找所有端口  
lsof -i:8090  # 查看端口详细信息  

### 查看log
cat -n redis.conf | grep pass| more  # -n 打印行号  
sed -n "500,520p" redis.conf  # 查看 [500,520] 行内容  
cat -n redis.conf | tail -n +500 | head -n 20  # 查看


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
启动线程 
`FutureTask ft = new FutureTask(Runnable/Callable);  new Thread(ft).start();  `  
时，jvm新开线程，**执行FutureTask的run方法**接下来才是最重要的：  
而通过`FutureTask(Runnable/Callable)`构造出来的FutureTask，最终都会转为`FutureTask(Callable)`，在FutureTask的run方法中会执行Callable的`call()`并设置`call()`的返回值`set(result);`。而FutureTask的句柄`ft`保存在主线程中，所以我们能在主线程中通过`ft.get();`方法拿到`result`。当然在get方法中会判断当前的状态，或阻塞等待或直接返回。get方法也能传参，用来空值get等待时间`V get(long timeout, TimeUnit unit)`  
所以，Future实现线程通信的方式就是操作线程共享内存的区域的变量。

##### FutureTask 在设置返回值set(result);时为什么需要cas？  
还不清楚  
##### Thread.start()发生了什么？  
调用native方法，jvm会开启新线程并定位、执行`Runnable.run`方法
##### 线程池是怎么循环地取任务队列任务呢？  
在线程池`ThreadPoolExecutor`中，有个`runWorker`方法。这个方法通过`while (task != null || (task = getTask()) != null) {`循环获取任务队列的任务，并执行其中的run方法。  
  
而这个**runWorker**是在任务提交execute -> addWorker 时被调用。  
在addWorker中，会创建一个新的ThreadPoolExecutor.Worker(Worker implements Runnable):`w = new Worker(firstTask);`，并将这个worker保存在线程池中。所以实际上，线程池中保存的是Worker——一个线程和Runnable任务的打包，这点可以从Worker中两个实例变量可以看出来`final Thread thread;Runnable firstTask;`。而worker创建的时候，就已经将Worker作为Runnable附加到了`final Thread thread`线程中：
```  
Worker(Runnable firstTask) {
	setState(-1); // inhibit interrupts until runWorker
    this.firstTask = firstTask;
    this.thread = getThreadFactory().newThread(this);
}
```  
在`final Thread thread`线程start的时候，就会执行worker的run方法，然后调用`ThreadPoolExecutor`的**runWorker**  
以上就是线程池循环获取任务的过程。  









