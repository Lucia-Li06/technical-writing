@echo off
%1 mshta vbscript:CreateObject("WScript.Shell").Run("%~s0 ::",0,FALSE)(window.close)&&exit
set CLASSPATH=%CLASSPATH%;  
set PATH=%PATH%  
set JAVA_HOME=%JAVA_HOME%
d:
cd D:\system-monitor
java -jar system-monitor-0.0.1-SNAPSHOT.jar >System-monitor-Log.log 2>&1
exit
@pause