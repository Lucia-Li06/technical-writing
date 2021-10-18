# Getting Started


#### Reference Documentation
```
https://github.com/oshi/oshi
```

####Support
Support Windows • Linux • macOS • Unix (AIX, FreeBSD, OpenBSD, Solaris)


####Prerequisite
Java (JDK8)


#### Guides
1. If you want to try out all the software and hardware information available, run SystemInfoTest.java under src/test/java.

2. This program provide some basic info with restful API so far.
build with:

```sh
	mvn clean install
```
then running with:

```sh
	java -jar system-monitor-0.0.1-SNAPSHOT.jar
```

3. Visit with postman or curl like:

* Obtain OS info:

```sh
curl --request GET \
  --url http://localhost:8080/monitor/osInfo
```

* Obtain CPU info:

```sh
curl --request GET \
  --url http://localhost:8080/monitor/cpuInfo
```

* Obtain Memory info:

```sh
curl --request GET \
  --url http://localhost:8080/monitor/memInfo
```

* Obtain battery info:

 ```sh
 curl --request GET \
  --url http://localhost:8080/monitor/batteryInfo
 ```
 
 * Obtain device info:

 ```sh
 curl --request GET \
  --url http://localhost:8080/monitor/deviceInfo
 ```