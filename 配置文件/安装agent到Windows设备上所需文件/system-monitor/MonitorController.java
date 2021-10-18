package com.hydsoft.monintors.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hydsoft.monintors.entities.Response;
import com.hydsoft.monintors.windows.GetHardWareInfoService;

import oshi.SystemInfo;


@RestController
@RequestMapping("/monitor")
public class MonitorController {
	private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);
	
	/**
	 * 使用率=每周开机时间/每周总时间；闲置率=1-使用率；使用频次等于每周开机次数；
	 */
	
	@Autowired
	GetHardWareInfoService getHardWareInfoService;

    @GetMapping("/osInfo")
    public @ResponseBody Response getOsInfo(){
        SystemInfo si = new SystemInfo();
        return new Response().success(getHardWareInfoService.getOsSystemInfo(si)); 
    }
    
    @GetMapping("/cpuInfo")
    public @ResponseBody Response getCpuInfo(){
        SystemInfo si = new SystemInfo();
        return new Response().success(getHardWareInfoService.getCPUInfo(si)); 
    }
    
    @GetMapping("/memInfo")
    public @ResponseBody Response getMemInfo(){
        SystemInfo si = new SystemInfo();
        return new Response().success(getHardWareInfoService.getMemoryInfo(si)); 
    }
    
    @GetMapping("/batteryInfo")
    public @ResponseBody Response getBatteryInfo(){
        SystemInfo si = new SystemInfo();
        return new Response().success(getHardWareInfoService.getBatteryInfo(si)); 
    }
    
    @GetMapping("/deviceInfo")
    public @ResponseBody Response getDeviceInfo(){
        SystemInfo si = new SystemInfo();
        return new Response().success(getHardWareInfoService.getDeviceInfo(si)); 
    }   
    
}
