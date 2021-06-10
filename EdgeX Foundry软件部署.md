<h1>EdgeX Foundry软件部署</h1>

- [一、硬件设备](#一硬件设备)
- [二、前提条件](#二前提条件)
- [三、部署EdgeX Foundry](#三部署edgex-foundry)
- [四、配置串口服务器](#四配置串口服务器)
- [五、适配OWON网关](#五适配owon网关)
- [六、适配中弘Modbus网关](#六适配中弘modbus网关)
- [七、适配安卓盒子](#七适配安卓盒子)

在IoT项目的架构中，EdgeX Foundry为边缘端智能设备提供了一种安全、高效的接入方式。本文档介绍了EdgeX Foundry软件的部署过程，包括所使用的硬件设备、部署前准备、具体操作步骤以及部署后的配置。

## 一、硬件设备
| 设备 | 规格/型号 |
| --- | --- |
| EdgeX Foundry服务器<br>（Intel边缘设备） | 8G Intel (R) Core (TM) i7-7700HQ CPU @ 2.80GHz <br> OS：Ubuntu 16.04 |
| 中弘Modbus网关 | B17X2RT |
| RS485串口服务器 | ZX-S508D |
| OWON网关 | SEG-X1 |
| 智能ZigBee灯组 | FNB56-ZCW39GD1.1 |
| 安卓音乐盒子 | audio_2021309_Rk3288 |

## 二、前提条件
开始部署前，请确认满足以下条件：<br>
- 已按照如下示意图安装好相关的硬件设备：<br>
  <img src="/images/软件部署指南/硬件连线图-icon.png" width=450 align=center />
  <br>

- 已在EdgeX Foundry服务器上安装下表中的软件：
    | 软件包名称 | 版本要求 |
    | --- | --- |
    | docker | 18.09.7或以上 |
    | docker-compose | 1.29.1或以上 |

- 已获取以下配置文件：<br>
    docker-compose.yml<br>
    owon_mqtt_profile.yaml<br>
    zhonghong_modbus_profile.yaml<br>
遇到问题请联系慧博云通技术支持。

## 三、部署EdgeX Foundry

1. 使用root用户登录EdgeX Foundry服务器。

2. 进入/root目录：<br>

    `cd /root`<br>

3. 使用WinSCP将**docker-compose.yml**文件放入root目录。<br>

4. 启动EdgeX Foundry进程：<br>

    `docker-compose up -d`<br>

5. 查看EdgeX Foundry服务是否启动成功：<br>

    `docker-compose ps`<br>

- 如果命令回显中所有容器的状态均为up，则EdgeX Foundry服务启动成功。<br>

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/566e8c84-f9b3-4003-a70a-61868795d2b8/11.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/566e8c84-f9b3-4003-a70a-61868795d2b8/11.png)
<br>
- 如果存在状态为**exit**的容器，执行**docker start**命令尝试启动。<br>

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/db01045c-2660-41ad-97aa-dd195ebd78a6/22.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/db01045c-2660-41ad-97aa-dd195ebd78a6/22.png)
<br>
若仍然无法启动，执行**docker logs**命令查看日志，并将日志发送给慧博云通技术支持进行分析定位。

## 四、配置串口服务器

1. 使用浏览器访问http://{串口服务器IP地址}/cgi-bin/luci。

2. 输入用户名密码登录串口服务器web管理界面。

3. 根据下图，配置服务器端口信息，本示例中配置PORT 8。<br>
   本地端口设置为4008。<br>

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/46a093cb-99f0-4e68-8dde-13fce5e25eaf/11.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/46a093cb-99f0-4e68-8dde-13fce5e25eaf/11.png)
<br>
## 五、适配OWON网关

1. 使用浏览器进入http://{EdgeX Foundry IP地址}:8500/ui/dc1/kv/edgex/devices/1.0/edgex-device-mqtt/Driver。

2. 修改IncomingHost为EdgeX Foundry的IP地址。<br>

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e6291a27-de3c-402b-8564-f1da8105059a/11.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e6291a27-de3c-402b-8564-f1da8105059a/11.png)
<br>
3. 修改**ResponseHost**为EdgeX Foundry的IP地址。<br>

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0296b96d-437f-4924-8ee2-607379b78ee4/22.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0296b96d-437f-4924-8ee2-607379b78ee4/22.png)
<br>
4. 修改IncomingTopic为device/{deviceId}/report。<br>
   其中{deviceId}是OWON网关的MAC地址。<br>

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c18a7e6b-3c97-4b0f-9689-37dde21180ff/33.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c18a7e6b-3c97-4b0f-9689-37dde21180ff/33.png)
<br>
5. 修改ResponseTopic为reply/device/{deviceId}/{appclientId}。<br>
   {deviceId}是OWON网关的MAC地址，{appclientId}是自定义的标识符。

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/10455cda-9776-4d11-b63b-cd7f27c0e0b8/44.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/10455cda-9776-4d11-b63b-cd7f27c0e0b8/44.png)
<br>
6. 上传OWON网关信息配置文件。<br>
   (1). 使用浏览器进入http://{EdgeX Foundry IP地址}:4000/并登录。<br>

>**注意：**
> 如果是首次登录，系统会要求您设置用于登录的用户名密码。本示例中用户名和密码均为admin。<br>

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d2009caa-fb89-4472-8e67-c07e5e235df5/11.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d2009caa-fb89-4472-8e67-c07e5e235df5/11.png)

<br>  

    (2). 修改owon_mqtt_profile.yaml配置文件，把设备的MAC地址全部替换为当前环境灯的MAC地址。<br>
    例如：switch_08F02004008D1500，需要将08F02004008D1500替换为实际的MAC地址。
    您可以从灯的设备信息中获取该MAC地址。
    (3). 点击Device Profile下面的+号，上传OWON网关信息的配置文件。 <br> 

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9ef2d0bd-68c1-48b2-97ac-5c6fe56f2a5c/22.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9ef2d0bd-68c1-48b2-97ac-5c6fe56f2a5c/22.png)
<br>
    (4). 点击Devices下面的+号，添加OWON网关设备信息。<br>

Schema：tcp<br>
Host：EdgeX Foundry服务器的IP地址<br>
Port：MQTT服务的端口号，此处为1883<br>
Clientid：clientid<br>
Topic：api/device/{deviceId}/{appclientId}<br>
其中**{deviceId}是OWON网关的MAC地址，{appclientId}是自定义的标识符。<br>

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c02794d5-4dfd-46a6-be70-6d8935fe8e8f/33.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c02794d5-4dfd-46a6-be70-6d8935fe8e8f/33.png)
<br>
(5). 重启edgex-device-mqtt容器使配置生效：<br>

`docker restart edgex-device-mqtt`

## 六、适配中弘Modbus网关

1. 使用浏览器进入http://{EdgeX Foundry IP地址}:4000/。

2. 编辑zhonghong_modbus_profile.yaml配置文件，将空调地址全部修改为当前环境的实际地址。<br>
   Get命令的地址需要替换为空调状态寄存器的地址，Set命令的地址需要替换为控制寄存器的地址。<br>
   例如，在PowerGet_193，需要将193替换成实际的状态寄存器地址；在TemperatureSet_4130中，需要将4130替换成实际的控制寄存器地址。 <br>
   状态寄存器计算方法：(AA×32 + BB) ×6 + 1<br>
   控制寄存器计算方法：(AA×32 + BB) × 4 + 4001<br>
   其中AA和BB的值可从中弘空调的web管理界面获取，如下图所示。详情请参考中弘官方文档《室外机网关使用手册》。<br>
   ![](/images/附录-空调寄存器地址.png)

<br>
1. 点击Device Profile下面的+号，上传中弘Modbus网关信息的配置文件。

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c071b553-aa85-4dee-b568-ef33c1d01999/11.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c071b553-aa85-4dee-b568-ef33c1d01999/11.png)
<br>
4. 点击Devices下面的+号，添加中弘Modbus网关设备的信息。<br>
   Address：串口服务器的IP地址
   Port：串口服务器的端口号，此处设置为4008<br>
   此端口号需要与串口服务器中的配置保持一致，详情参考[配置串口服务器](#)中的步骤3。<br>
   UnitID：中弘Modbus网关管理页面中配置的设备地址，本示例中设置为1<br>

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dd889e17-1f0d-451b-8cc0-b2360ad324e5/22.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dd889e17-1f0d-451b-8cc0-b2360ad324e5/22.png)

<br>    

下图是中弘Modbus网关的管理页面，可从该页面获取设备地址，详情请参考中弘官方文档《室外机网关使用手册》。<br>

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/52293b28-2a55-472e-acbc-4c2bfa093778/33.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/52293b28-2a55-472e-acbc-4c2bfa093778/33.png)

## 七、适配安卓盒子

1. 将安卓盒子连接到EdgeX Foundry服务器的MQTT Broker上。
2. 使用root用户登录EdgeX Foundry服务器。
3. 进入/root目录：<br>
    `cd /root`<br>

4. 检查docker-compose.yml文件，确保文件包含安卓盒子的配置，如下图所示：<br>
<img src="/images/软件部署指南/docker-compose配置文件-安卓.png" width=300 align=center/>
<br>
5. 启动安卓服务的容器：<br>
   `docker-compose up -d device-mqtt-android`<br>

6. 使用浏览器进入http://{EdgeX Foundry IP地址}:8500/ui/dc1/kv/edgex/devices/1.0/edgex-device-mqtt-android/Driver/。

7. 修改IncomingHost为EdgeX Foundry的IP地址。<br>
<img src="/images/软件部署指南/安卓-IncomingHost.png" width=500 align=center/>
<br>
8. 修改**ResponseHost**为EdgeX Foundry的IP地址。<br>
   <img src="/images/软件部署指南/安卓-ResponseHost.png" width=500/>
<br>

9. 修改IncomingTopic和ResponseTopic为api/android/{type}/{brand_id}/{mac}。
   brand_id：品牌ID
   mac：安卓盒子的MAC地址
   type：设备类型，取值如下：
   - VIDEO：广告屏
   - SVIDEO：同步屏
   - AI：互动屏
   - AUDIO：音乐盒子

10. 修改IncomingClientId为IncomingDataSubscriber-android。<br>
    <img src="/images/软件部署指南/安卓-IncomingClientId.png" width=500/>

11. 修改ResponseClientId为CommandResponseSubscriber-android。
12. 上传安卓盒子信息配置文件。
    (1) 使用浏览器进入http://{EdgeX Foundry IP地址}:4000/。
    (2) 点击Device Profile下面的+号，上传安卓音乐盒子的配置文件。<br>
    <img src="/images/软件部署指南/安卓-上传profile.png"/>
<br>
    (3) 点击Devices下面的+号，添加安卓音乐盒子信息。
        Schema：tcp
        Host：EdgeX Foundry服务器的IP地址
        Port：MQTT服务的端口号，此处为1883
        Clientid：clientid
        Topic：api/android/{type}/{brand_id}/{mac}
        其中，brand_id是品牌ID，mac是安卓盒子的MAC地址，type是设备类型
        type取值：
        - VIDEO：广告屏
        - SVIDEO：同步屏
        - AI：互动屏
        - AUDIO：音乐盒子
13.  重启edgex-device-mqtt-android容器使配置生效：<br>

     `docker restart edgex-device-mqtt-android`