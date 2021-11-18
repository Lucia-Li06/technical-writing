<h1>OpenNess离线部署</h1>

[1. 部署环境](#1-部署环境)<br>
[2. 免密配置](#2-免密配置)<br>
[3. 对时配置](#3-对时配置)<br>
[4. 下载离线包](#4-下载离线包)<br>
[5. 安装Ansible](#5-安装ansible)<br>
[6. OpenNess配置](#6-OpenNess配置)<br>
[7. 运行脚本](#7-运行脚本)<br>

本文介绍了如何离线部署OpenNess。<br>


 Note: 

> 如果您是初次部署，建议先使用一台服务器作为OpenNess部署节点，尝试进行部署。服务器建议配置如下：
>
>| CPU  | 内存 | OS              |
>| ---- | ---- | --------------- |
>| 6核  | ≥8G  | CentOS 7.9.2009 |


## 1. 部署环境

| Role                | OS              | Mem  | Disk                           | SoftWare       | IP             |
| ------------------- | --------------- | ---- | ------------------------------ | -------------- | -------------- |
| Ansible             | CentOS 7.6.1810 | /    | /                              | ansible、git   | 172.16.182.116 |
| OpenNess_controller | CentOS 7.9.2009 | /    | 建议根的容量在250G以上，尽量大 | 最小化安装即可 | 172.16.182.145 |
| OpenNess_node01     | CentOS 7.9.2009 | /    | 同上                           | 最小化安装即可 | x.x.x.x        |
| ...                 | ...             | ...  | ...                            | ...            | ...            |

Note:

> 每台服务器内存建议8G以上
>
> 每台服务器网络需要互通
>
> Ansible所在服务器，可以运行脚本即可，对硬盘无要求
>
> OpenNess_Master所在服务器，如果不按照最小化安装，后续运行脚本可能会出现各种依赖版本冲突
>
> 如果是OpenNess多机部署，这里还可以有OpenNess_Node的服务器，关于如何部署，后续文档会进一步说明。

## 2. 免密配置

Ansible>OpenNess

```bash
ssh-copy-id root@172.16.182.145
ssh-copy-id root@x.x.x.x
```

OpenNess_Mster->OpenNess_Master

离线化的脚本中也会出现本机对本机的cp，所以OpenNess本机也要传密钥给自己。

```bash
ssh-copy-id root@172.16.182.145
```

如果还有其他主机Node，建议master对node做免密，node对自己也要做免密。

## 3. 对时配置

每台服务器的时间应当一致，尤其是OpenNess节点之间。具体操作可参考 [同步时间](https://www.feiyiblog.com/2020/08/06/Linux%E6%9C%8D%E5%8A%A1%E5%99%A8%E4%B8%AD%E7%9A%84%E6%97%B6%E9%97%B4%E5%90%8C%E6%AD%A5%E9%97%AE%E9%A2%98/) 。

## 4. 下载离线包

```bash
链接：https://pan.baidu.com/s/1S0ooU1PsZ3trvt5ofhSjbw 
提取码：inte 

checksum.txt           # opcdownloads.tar.gz 的md5值
opcdownloads.tar.gz    # openness 离线包
prepackages.tar.gz     # ansible依赖解决
# 以下两个包，选择其中一个下载即可，下载前请先看下方说明
openness_2103.tar.gz   # ansible脚本{minimal(kubeovn+sriov)+flexran(calico+sriov)}
# 如果要使用istio的话，请下载该脚本
openness_2103_mini_istio.tar.gz  # ansible脚本{minimal(kubeovn+sriov)+istio}
```

关于两个Ansible脚本的说明

> openness_2103.tar.gz：用来安装 minimal 或者 flexran 的 flavor 的脚本（支持 calico+sriov 和 kubeovn+sriov ）
>
> openness_2103-mini_istio.tar.gz：相比上一个脚本，增加了istio的功能



## 5. 安装Ansible

### （1）完善Ansible环境

解压 `prepackages.tar.gz`

```bash
mkdir /prepackages
tar zxf prepackages.tar.gz -C /prepackages
yum localinstall -y /prepackages/*.rpm
```

### （2）准备安装脚本

解压`openness_2103.tar.gz | openness_2103_mini_istio.tar.gz`

这里以`openness_2103.tar.gz`为例进行操作，`openness_2103_mini_istio.tar.gz`和以下操作一致，只是包里面的一些配置不同，按照需要选择一个即可。

```bash
mkdir openness_21.03
tar zxf openness_2103.tar.gz -C openness_21.03
```

将`checksum.txt & opcdownloads.tar.gz` 放到以下路径

```bash
openness_21.03/roles/offline_roles/unpack_offline_package/files/
```

### （3）修改配置

**以下配置中，minimal和flexran选则一个进行配置即可。**

#### (a) minimal

回到openness_21.03目录，修改`openness_2103/inventory.yml`

```yml
$ vim inventory.yml
# 只修改注释部分即可
---
all:
  vars:
    cluster_name: cluster_test    # 该名字不能为数字，不能有-
    flavor: minimal  
    single_node_deployment: true  # 只有一台服务器就true，反之false
    limit:                        
controller_group:
  hosts:
    controller:
      ansible_host: 172.16.182.145 # 修改IP
      ansible_user: root 
edgenode_group:
  hosts:
    node01:
      ansible_host: 172.16.182.145  # controller和node01的ip一样，因为是single，如果两台服务器，node01要改为另一个服务器的IP，也可以根据格式继续添加node02,node03
      ansible_user: root   
edgenode_vca_group:
  hosts:
ptp_master:
  hosts:
ptp_slave_group:
  hosts:
```

修改`flavors/minimal/all.yml`

```yaml
# 将以下cpu部分注释即可
cpu:
  # CPU policy - possible values: none (disabled), static (default)
  policy: "static"
  # Reserved CPUs for K8s and OS daemons - list of reserved CPUs - adjust according to CPU model, HT enabled/disabled and Single Node enabled/disabled
  #reserved_cpus: "0,32,64,96"
  reserved_cpus: "0,24,48,72"

# Kubernetes Topology Manager policy - possible values: none (disabled), best-effort (default), restricted, single-numa-node
topology_manager:
  policy: "single-numa-node"
```

#### (b) flexran

修改`openness_2103/inventory.yml`

> 结合 minimal，flavor 只需要修改flavor为 flexran 即可

```yml
$ vim inventory.yml
    flavor: flexran               # 修改为flexran
```

修改`openness_2103/flavor/flexran/`

##### single模式的修改

`flavors/flexran/edgenode_group.yml`

举例服务器的cpu_numa配置

> ```bash
> $ lscpu
> ...
> NUMA node0 CPU(s):     0-23,48-71
> NUMA node1 CPU(s):     24-47,72-95
> ...
> ```

```yml
$ vim flavors/flexran/edgenode_group.yml
# 去掉每段的开头，并写出范围，每段用逗号隔开
cpu_isol: "1-23,25-47,49-71,73-95"
# 将每段的开头写在以下配置中，用逗号隔开
cpu_os: "0,24,48,72"
```

##### 非single模式的修改(两台)

`flavors/flexran/controller_group.yml`：master节点

`flavors/flexran/edgenode_group.yml`：worker节点

```yml
$ vim flavors/flexran/controller_group.yml
# 根据controller机器的cpu配置，效仿single的cpu_numa设置

cpu_isol: "1-23,25-47,49-71,73-95"
cpu_os: "0,24,48,72"

$ vim flavors/flexran/edgenode_group.yml
# 根据作为node01机器的cpu配置，效仿single的cpu_numa设置

cpu_isol: "1-23,25-47,49-71,73-95"
cpu_os: "0,24,48,72"
```



## 6. OpenNess配置

如果是多台服务器，操作一致。

主机名不得为localhost。

```bash
hostnamectl set-hostname xxx   # 不能带下划线，k8s不允许，可以带-
# exit 重新登录，检查hostname是否更改成功，否则在写入hosts文件时，HOSTNAME变量与更改后的不一致
```

写入hosts文件，写入`127.0.0.1`和`::1`的末尾

```bash
sed -i "/^::1/ s/$/ $HOSTNAME/g;/^127.0.0.1/ s/$/ $HOSTNAME/g" /etc/hosts
```

## 7. 运行脚本

```python
nohup python3 deploy.py > log.txt 2>&1 &   # 后台运行
python3 deploy.py           # 前台运行
# log目录：openness_2103/logs/
```

