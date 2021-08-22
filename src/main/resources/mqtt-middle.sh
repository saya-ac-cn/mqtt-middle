#!/bin/sh
#
#该脚本为Linux下启动java程序的通用脚本。即可以作为开机自启动service脚本被调用，
#也可以作为启动java程序的独立脚本来使用。
#
#Author: saya, Date: 2020年01月05日16:02:14
#
###################################
# 注意！！！
# 本脚本stop使用系统的kill命令来强制终止程序的运行
# 所以，在执行本命令时，你懂的！
###################################
#
###################################
#环境变量及程序执行参数
#需要根据实际环境以及Java程序名称来修改这些参数
###################################

#JAVA执行命令
#JAVA_CMD=""
#执行程序启动所使用的系统用户，考虑到安全，推荐不使用root帐号
RUNNING_USER=saya
#设置JAVA_HOME
JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home"

#java虚拟机启动参数
JAVA_OPTS="-Xbootclasspath/a:./ -Xms128m -Xmx128m -Xss256K -XX:MetaspaceSize=32m -XX:MaxMetaspaceSize=32m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Djava.awt.headless=true"
#启动项目的名称
APP_NAME=mqtt-middle

#启动JAR包
APP_JAR=mqtt-middle.jar
#需要启动的Java主程序（main方法类）
APP_MAINCLASS=ac.cn.saya.mqtt.middle.MqttMiddleApplication

#程序目录，如开机自启失败，手动设置
#APP_HOME=$(pwd)
APP_HOME="/Users/saya/project/java/mqtt-middle/target/mqtt-middle"
#程序进程号
APP_PID=$APP_HOME/mqtt-middle.pid

#拼凑完整的classpath参数，包括指定lib目录下所有的jar
CLASSPATH=".:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$APP_HOME/$APP_JAR"

#CLASSPATH=$APP_HOME/classes
#for i in "$APP_HOME"/lib/*.jar; do
#  CLASSPATH="$CLASSPATH":"$i"
#done

if [ -z "$JAVA_HOME" ];then
	echo "JAVA_HOME can not find"
	exit 1
else
	 #echo "================================"
	 #java -version
	 echo "INFO: use JAVA_HOME '$JAVA_HOME'"
     echo "INFO: use JAVA_OPTS '$JAVA_OPTS'"
     echo "INFO: use APP_HOME '$APP_HOME'"
	 echo "INFO: use APP_JAR '$APP_JAR'"
     echo "INFO: use APP_MAINCLASS : '$APP_MAINCLASS'"
     #echo "================================"

fi
#设置classpath
#CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

###################################
#(函数)判断程序是否已启动
#
#说明：
#使用JDK自带的JPS命令及grep命令组合，准确查找pid
#jps 加 l 参数，表示显示java的完整包路径
#使用awk，分割出pid ($1部分)，及Java程序名称($2部分)
###################################
#初始化pids变量（全局）
pids=0
checkpid(){
	if [ -e $APP_PID ];then
		pids=`cat $APP_PID`
		#if [ $pids -ne 0 ]; then
		#	echo "WARN: $APP_NAME already started!  (pid = $pids)"
		#else
		#	pids=0
		#fi
		#监测该进程是否运行
		#netstat -anp |  grep $pids
		#if [ $? -eq 0 ]; then
		#	pids=0
		#else
		#	echo "================================"
		#	echo "$APP_NAME already started!  [pid=$pids]"
		#	echo "================================"
		#	echo "pids is $pids"

		#fi
	fi
	#javaps=`jps -l | grep ${APP_NAME}`

	#if [ -n "$javaps" ]; then
	#	pids=`echo $javaps | awk '{print $1}'`
	#	echo "pids is $pids"
	#else
	#	pids=0
	#fi
}

checkRunning(){
	if [ -f "$APP_PID" ]; then
		if [ -z "`cat $APP_PID`" ]; then
		echo "ERROR: Pidfile '$APP_PID' exists but contains no pid"
        return 2
       fi
	   PID=`cat $APP_PID`
	   RET="`ps -p $PID|grep java`"
       if [ -n "$RET" ];then
         return 0;
       else
         return 1;
       fi
	else
		return 1;
	fi
}
###################################
#(函数)启动程序
#
#说明：
#1. 首先调用checkpid函数，刷新$pids全局变量
#2. 如果程序已经启动（$pids不等于0），则提示程序已启动
#3. 如果程序没有被启动，则执行启动命令行
#4. 启动命令执行后，再次调用checkpid函数
#5. 如果步骤4的结果能够确认程序的pid,则打印[OK]，否则打印[Failed]
#注意：echo -n 表示打印字符后，不换行
#注意: "nohup 某命令 >/dev/null 2>&1 &" 的用法
###################################
start() {
   checkpid
	if [ $pids -ne 0 ]; then
      echo "================================"
	  echo -ne "\033[1;31m  WARN: $APP_NAME already started! (pid=$pids) \033[0m \n"
      echo "================================"
   else
	  echo  "$JAVA_CMD"
      JAVA_CMD="nohup $JAVA_HOME/bin/java"
	  #echo  "su - $RUNNING_USER -c $JAVA_CMD"
	  echo -n "Starting $APP_NAME ..."

	  #su - $RUNNING_USER -c "$JAVA_CMD"
	  #$JAVA_CMD $JAVA_OPTS -jar  $APP_JAR start >$APP_HOME/logs/init.log 2>&1 &
	 $JAVA_CMD $JAVA_OPTS  -classpath  $CLASSPATH  $APP_MAINCLASS    start >$APP_HOME/logs/init.log 2>&1 &

	  #echo "$JAVA_CMD $JAVA_OPTS  -classpath  $CLASSPATH   $APP_JAR $APP_MAINCLASS  start >$APP_HOME/logs/init.log 2>&1 &"
	  echo $! > $APP_PID
      checkpid

      if [ $pids -ne 0 ]; then
		echo -ne "\033[1;32m  (pid=$pids) [OK] \033[0m \n"
      else
		 echo -ne "\033[1;31m   [FAILED] \033[0m \n"
      fi
   fi
}

###################################
#(函数)停止程序
#
#说明：
#1. 首先调用checkpid函数，刷新$pids全局变量
#2. 如果程序已经启动（$pids不等于0），则开始执行停止，否则，提示程序未运行
#3. 使用kill pid命令进行强制杀死进程
#4. 执行kill命令行紧接其后，马上查看上一句命令的返回值: $?
#5. 如果步骤4的结果$?等于0,则打印[OK]，否则打印[Failed]
#6. 为了防止java程序被启动多次，这里增加反复检查进程，反复杀死的处理（递归调用stop）。
#注意：echo -n 表示打印字符后，不换行
#注意: 在shell编程中，"$?" 表示上一句命令或者一个函数的返回值
###################################
stop() {
   checkpid

   if [ $pids -ne 0 ]; then
      echo -n "Stopping $APP_NAME ...(pid = $pids) "
      rm -rf $APP_HOME/mqtt-middle.pid
	  su - $RUNNING_USER -c "kill $pids"

      if [ $? -eq 0 ]; then
		 rm -rf $APP_PID
         echo -ne "\033[1;32m  [OK] \033[0m \n"
      else
         echo -ne "\033[1;31m  [FAILED] \033[0m \n"
      fi

      #checkpid
      #if [ $pids -ne 0 ]; then
      #   stop
      #fi
   else
      echo "================================"
	  echo -ne "\033[1;31m  ERROR: $APP_NAME is not running \033[0m \n"
      echo "================================"
   fi
}

###################################
#(函数)检查程序运行状态
#
#说明：
#1. 首先调用checkpid函数，刷新$pids全局变量
#2. 如果程序已经启动（$pids不等于0），则提示正在运行并表示出pid
#3. 否则，提示程序未运行
###################################
status() {
   checkpid

   if [ $pids -ne 0 ]; then
     echo -ne "\033[1;32m $APP_NAME is running! (pid=$pids) \033[0m \n"
   else
     echo -ne "\033[1;31m  $APP_NAME is not running \033[0m \n"

   fi
}
###################################
#(函数)打印系统环境参数
###################################
info() {
   echo "System Information:"
   echo "****************************"
   echo `head -n 1 /etc/issue`
   echo `uname -a`
   echo
   echo "JAVA_HOME=$JAVA_HOME"
   echo `$JAVA_HOME/bin/java -version`
   echo
   echo "APP_HOME=$APP_HOME"
   echo "APP_MAINCLASS=$APP_MAINCLASS"
   echo "****************************"
}

###################################
#读取脚本的第一个参数($1)，进行判断
#参数取值范围：{start|stop|restart|status|info}
#如参数不在指定范围之内，则打印帮助信息
###################################
case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
	 if ( checkRunning ) ; then
		echo "do stop!!!!!"
		$0 stop
	 fi
    # stop
     start
     ;;
   'status')
     status
     ;;
   'info')
     info
     ;;
  *)
	 echo -ne "\033[1;32mUsage: $0 {start|stop|restart|status|info} \033[0m \n"
     exit 1
esac
exit 0
