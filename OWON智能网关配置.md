<h1>OWON智能网关配置</h1>
[TOC]

在IoT项目的架构中，OWON网关是设备间的通讯中枢。本文档介绍了与OWON网关相关的配置流程，包括软件准备与具体配置步骤。

## 1. 前提条件

开始配置之前，请确保您已经在PC上安装了以下两个软件：
- Socket Tool
- Smart OWON Configuration Tool
可联系慧博云通技术支持获取软件安装包。

## 2. 配置设备

开始执行操作之前，需要先用网线连接OWON网关，并确保您执行操作的PC与OWON网关处于同一局域网内。

**注意：**
如果您之前配置过OWON网关设备，需要执行以下操作重置：
- OWON网关：在关机状态下同时按住蓝牙键与ZigBee键不放，开机成功后松开。
- 智能ZigBee灯组：反复开关灯组10次，灯泡闪烁表示重置完毕。

### 2.1 注册网关

1. 使用Smart OWON Configuration Tool查找OWON网关的IP地址。
<img src="/images/OWN网关配置.png" width="450"/>
<br>
1. 使用Socket工具，向{OWON网关IP地址}:11500发送如下报文注册网关：  

   `{"type":"wizardConfig","command":"serverCfg","argument":{"host":"{EdgeX Foundry IP地址}","port":1883},"sequence":1}`

    其中，1883为MQTT服务的端口号。

2. 向{OWON网关IP地址}:11500发送如下报文，启动网关并连接到MQTT 服务器：
    `{"type":"wizardConfig", "command":"startRegister", "sequence":1 }`

### 2.2 连接灯组

1. 使用root用户登录EdgeX Foundry服务器。

2. 执行以下命令安装mosquitto_pub工具：

    `apt install -y mosquitto-clients`

3. 将ZigBee灯组接通电源，保持灯亮。

4. 查看OWON网关盒子背面的MAC地址。

5. 在EdgeX Foundry服务器上执行以下命令：

    `mosquitto_pub -t api/device/{deviceId}/test -m '{"type":"zigbeeConfig","command":"permitJoin", "sequence":1010, "argument": { "switchgear":"on", "second":60 } }'`

    其中**{deviceId}**为OWON网关的MAC地址。