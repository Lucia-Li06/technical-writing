package com.hydsoft;

import java.util.Date;

public class DeviceInfo {
    //设备名称(手机品牌+型号+硬件序列号)
    private String deviceName;

    //设备在线状态
    private String deviceStatus;

    //开机时间
    private String deviceBootedDate;

    //使用时长
    private String deviceUptime;

    //低电量警告(其实是电池电量百分比)
    private double batteryRemainingCapacity;

    //电池温度
    private double batteryTemperature;

    //电池状态 充电、非充电
    private String batteryStatus;

    //电池使用时间？非charging状态使用时长
    private String batteryUsageTime;

    //设备使用频次（使用频次等于每周系统app触发次数）
    private String deviceUsageCount;

    //设备使用率 使用率=每周开机时间/每周总时间
    private String deviceUsageRate;

    //设备闲置率 闲置率=1-使用率；
    private String deviceFreeRate;

    //设备能效 = 电池电压
    private String deviceEnergyEfficiency;

    private Date currentRecordTime;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceUptime() {
        return deviceUptime;
    }

    public void setDeviceUptime(String deviceUptime) {
        this.deviceUptime = deviceUptime;
    }

    public double getBatteryRemainingCapacity() {
        return batteryRemainingCapacity;
    }

    public void setBatteryRemainingCapacity(double batteryRemainingCapacity) {
        this.batteryRemainingCapacity = batteryRemainingCapacity;
    }

    public double getBatteryTemperature() {
        return batteryTemperature;
    }

    public void setBatteryTemperature(double batteryTemperature) {
        this.batteryTemperature = batteryTemperature;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getBatteryUsageTime() {
        return batteryUsageTime;
    }

    public void setBatteryUsageTime(String batteryUsageTime) {
        this.batteryUsageTime = batteryUsageTime;
    }

    public String getDeviceUsageCount() {
        return deviceUsageCount;
    }

    public void setDeviceUsageCount(String deviceUsageCount) {
        this.deviceUsageCount = deviceUsageCount;
    }

    public String getDeviceUsageRate() {
        return deviceUsageRate;
    }

    public void setDeviceUsageRate(String deviceUsageRate) {
        this.deviceUsageRate = deviceUsageRate;
    }

    public String getDeviceFreeRate() {
        return deviceFreeRate;
    }

    public void setDeviceFreeRate(String deviceFreeRate) {
        this.deviceFreeRate = deviceFreeRate;
    }

    public String getDeviceEnergyEfficiency() {
        return deviceEnergyEfficiency;
    }

    public void setDeviceEnergyEfficiency(String deviceEnergyEfficiency) {
        this.deviceEnergyEfficiency = deviceEnergyEfficiency;
    }

    public String getDeviceBootedDate() {
        return deviceBootedDate;
    }

    public void setDeviceBootedDate(String deviceBootedDate) {
        this.deviceBootedDate = deviceBootedDate;
    }

    public Date getCurrentRecordTime() {
        return currentRecordTime;
    }

    public void setCurrentRecordTime(Date currentRecordTime) {
        this.currentRecordTime = currentRecordTime;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceName='" + deviceName + '\'' +
                ", deviceStatus='" + deviceStatus + '\'' +
                ", deviceBootedDate='" + deviceBootedDate + '\'' +
                ", deviceUptime='" + deviceUptime + '\'' +
                ", batteryRemainingCapacity=" + batteryRemainingCapacity +
                ", batteryTemperature=" + batteryTemperature +
                ", batteryStatus='" + batteryStatus + '\'' +
                ", batteryUsageTime='" + batteryUsageTime + '\'' +
                ", deviceUsageCount='" + deviceUsageCount + '\'' +
                ", deviceUsageRate='" + deviceUsageRate + '\'' +
                ", deviceFreeRate='" + deviceFreeRate + '\'' +
                ", deviceEnergyEfficiency='" + deviceEnergyEfficiency + '\'' +
                ", currentRecordTime=" + currentRecordTime +
                '}';
    }
}
