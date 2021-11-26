<h1>基于OpenNess或者k8s集群部署EdgeX</h1>

## 1. 前提条件
服务器已经部署了OpenNess或者k8s集群，并下载以下文件：

| 文件名                    | 
| ------------------------- | 
| nginx.default.conf        | 
| edgex-k8s-cyy-pv.yml      | 
| edgex k8-cyy.ym           | 


 

## 2. 基于k8s单机版部署EdgeX：

 

1. 找到EdgeX服务器的IP地址，替换edgex-k8s-cyy.yml 中的EDGEX_URL_IP，MYSQL_IP，MQTT_TCP_URL里的IP地址。（Note：集群部署应考虑ingress）

2. 替换nginx-config/nginx.default.conf配置文件里的proxy_pass后面的IP地址。

3. 创建nginx的configmap：

 `kubectl create configmap nginx-config --from-file=nginx-config/ -n edgex`

4. 创建smartserver mysql的本地持久化存储：

 `kubectl apply -f edgex-k8s-cyy-pv.yml -n edgex`

（Note：因为是单机版，所以存储为服务器本身硬盘，如果是集群版，应替换为NFS或者其他分布式存储）

5. 创建所有EdgeX部署：

 `kubectl apply -f edgex-k8s-cyy.yml -n edgex`

6. 打开EdgeX 管理页面，按照<a href="https://gitee.com/PubDocumentation/iot-documents/blob/master/1.%20EdgeX%20Foundry%E8%BD%AF%E4%BB%B6%E9%83%A8%E7%BD%B2.md#edgex-foundry%E8%BD%AF%E4%BB%B6%E9%83%A8%E7%BD%B2">EdgeX部署文档</a>配置设备信息等。


Note: 

 > 基于OpenNess部署EdgeX 同上述步骤。<br>



 <br>

Kubenetes版本的端口对应关系（或者直接参考k8s部署yaml文件里的service部分）：

 ```bash

edgex-core-consul： 8500-->30850； 8400-->30840

edgex-support-notifications:   48060-->30060

edgex-core-metadata: 48081-->30081

edgex-core-data: 48080-->30080

edgex-core-command: 48082-->30082

edgex-support-scheduler: 48085-->30085

edgex-app-service-configurable-rules: 48100-->30100

edgex-kuiper: 48075-->30075; 20498-->30098

edgex-ui-go: 4000-->30040   

```