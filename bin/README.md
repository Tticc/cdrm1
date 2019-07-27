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


