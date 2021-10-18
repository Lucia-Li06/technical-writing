package com.hydsoft.monintors.entities;

import com.hydsoft.monintors.utils.CalculateUtil;

/**
 * CPU entity
 * @author Xiaodi.Jin
 * @version 1
 * @time 2021/06/24
 */
public class Cpu {
    
    /**
     * 核心数
     */
    private int cpuNumber;

    /**
     * CPU总的使用率
     */
    private double cpuTotalUsage;

    /**
     * CPU系统使用率
     */
    private double sysUsage;

    /**
     * CPU用户使用率
     */
    private double userUsage;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNumber() {
        return cpuNumber;
    }

    public void setCpuNumber(int cpuNumber) {
        this.cpuNumber = cpuNumber;
    }

    public double getTotal() {
        return CalculateUtil.round(CalculateUtil.mul(cpuTotalUsage, 100), 2);
    }

    public void setTotal(double cpuTotalUsage) {
        this.cpuTotalUsage = cpuTotalUsage;
    }

    public double getSysUsage() {
        return CalculateUtil.round(CalculateUtil.mul(sysUsage / cpuTotalUsage, 100), 2);
    }

    public void setSysUsage(double sysUsage) {
        this.sysUsage = sysUsage;
    }

    public double getUserUsage() {
        return CalculateUtil.round(CalculateUtil.mul(userUsage / cpuTotalUsage, 100), 2);
    }

    public void setUserUsage(double userUsage) {
        this.userUsage = userUsage;
    }

    public double getWait() {
        return CalculateUtil.round(CalculateUtil.mul(wait / cpuTotalUsage, 100), 2);
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getFree() {
        return CalculateUtil.round(CalculateUtil.mul(free / cpuTotalUsage, 100), 2);
    }

    public void setFree(double free) {
        this.free = free;
    }

}
