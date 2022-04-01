perfcase=`ps -ef | grep java | grep "myApi" | awk  -F ' '  '{print $2}'`
if [ ! -z "$perfcase" ]; then
    echo "kill old myApi process [${perfcase}]"
    kill -9 $perfcase > /dev/null 2>&1
    sleep 1
fi
# 设置单线程Serial垃圾收集器
export SERIALGC="-XX:+UseSerialGC"
# 设置并行垃圾收集器
export PARALLELGC="-XX:+UseParallelGC -XX:+UseParallelOldGC"
# 设置CMS垃圾收集齐
export CMSGC="-XX:+UseParNewGC -XX:+UseConcMarkSweepGC"
# 设置G1垃圾收集器
export G1GC="-XX:+UseG1GC"
# 大对象阈值（适用于ParNew和Serial）
export PretenureSizeThreshold="-XX:PretenureSizeThreshold=1m"
# 分代年龄大小阈值
export MaxTenuringThreshold="-XX:MaxTenuringThreshold=0"
# 原生内存监控
export NMT="-XX:NativeMemoryTracking=detail -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics"
export JAVA_OPTS0="-Xmx512m -Xms512m -Xmn256m -Xss256k -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m $CMSGC -XX:+HeapDumpOnOutOfMemoryError -verbose:class -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.log"
export JAVA_OPTS1="-Xmx512m -Xms512m -Xmn256m -Xss256k -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.log $NMT"
export JAVA_OPTS2="-Xmx512m -Xmx512m -Xss256k -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m -XX:+HeapDumpOnOutOfMemoryError -verbose:class -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc2.log"
export JAVA_OPTS3="-Xmx512m -Xmx512m -Xss256k -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m -XX:+HeapDumpOnOutOfMemoryError -verbose:class -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc3.log"
export JAVA_OPTS4="-Xmx512m -Xmx512m -Xss256k -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m -XX:+HeapDumpOnOutOfMemoryError -verbose:class -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc4.log"

export AGENT_PATH=`ls /home/perfma/perfma-one-agent/agent-bootstrap-*.jar`
export JAR_PATH="/home/perfma/perfcase"

nohup java -DPERFMA_APP_CODE=AOPTest -javaagent:$AGENT_PATH $JAVA_JAVA_OPTS0 -jar /home/perfma/aoptest/myApi.jar >./myApi.out &
#nohup java -DPERFMA_APP_CODE=middle -javaagent:$AGENT_PATH $JAVA_OPTS2 -jar $JAR_PATH/Middle.jar >$JAR_PATH/mid.out &
#nohup java -DPERFMA_APP_CODE=backend -javaagent:$AGENT_PATH $JAVA_OPTS3 -jar $JAR_PATH/BackEnd.jar >$JAR_PATH/end.out &
#nohup java -DPERFMA_APP_CODE=backend-2 -javaagent:$AGENT_PATH $JAVA_OPTS4 -jar $JAR_PATH/Back_End.jar >$JAR_PATH/end-2.out &

