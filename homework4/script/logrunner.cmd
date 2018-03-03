
START /wait java -Xmx512m -Xms512m -XX:+UseSerialGC -Xloggc:%0\..\memoryLeak-Serial.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar .\homework4.jar 15000

START /wait java -Xmx512m -Xms512m -XX:+UseParallelGC -XX:+UseParallelOldGC -Xloggc:%0\..\memoryLeak-Parallel.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar .\homework4.jar 15000

START /wait java -Xmx512m -Xms512m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -Xloggc:%0\..\memoryLeak-ParNew.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar .\homework4.jar 5000

START /wait java -Xmx512m -Xms512m -XX:+UseG1GC -Xloggc:%0\..\memoryLeak-G1.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar .\homework4.jar 1000