package com.hydsoft.monintors.windows;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hydsoft.monintors.entities.Battery;
import com.hydsoft.monintors.entities.Cpu;
import com.hydsoft.monintors.entities.DeviceInfo;
import com.hydsoft.monintors.entities.Memory;
import com.hydsoft.monintors.entities.OperatingSystem;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PowerSource;
import oshi.util.FormatUtil;
import oshi.util.Util;

@Service
public class GetHardWareInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(GetHardWareInfoService.class);
	
	
	public OperatingSystem getOsSystemInfo(SystemInfo si ) {
		
        logger.info("getOsSystemInfo...");
        oshi.software.os.OperatingSystem os = si.getOperatingSystem();
        
        OperatingSystem sysShortInfo = new OperatingSystem();
        
        sysShortInfo.setOsName(String.valueOf(os));
        sysShortInfo.setManufacturer(os.getManufacturer());
        sysShortInfo.setFamily(os.getFamily());
        sysShortInfo.setBitness(os.getBitness());
        sysShortInfo.setSystemBootTime(Instant.ofEpochSecond(os.getSystemBootTime()));
        sysShortInfo.setUpTime(FormatUtil.formatElapsedSecs(os.getSystemUptime()));
        sysShortInfo.setOsSessions(os.getSessions());
        return sysShortInfo;
		
	}
	
	public Cpu getCPUInfo(SystemInfo si ) {
        logger.info("getCPUInfo...");
        CentralProcessor processor = si.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        Cpu cpu = new Cpu();
        cpu.setCpuNumber(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSysUsage(cSys);
        cpu.setUserUsage(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
        return cpu;
	}
	
    public Memory getMemoryInfo(SystemInfo si ) {
    	logger.info("getMemoryInfo...");
    	GlobalMemory memory = si.getHardware().getMemory();
    	Memory mem = new Memory();
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
        return mem;
    }
    
    public List<Battery> getBatteryInfo(SystemInfo si ) {
    	logger.info("getBatteryInfo...");
    	List<PowerSource> list = si.getHardware().getPowerSources();
    	List<Battery> batteries = new ArrayList<>();
    	for (PowerSource powerSource:list) {
    		Battery battery = new Battery();
    		battery.setName(powerSource.getName());
    		battery.setDeviceName(powerSource.getDeviceName());
    		battery.setCharging(powerSource.isCharging());
    		battery.setCurrentCapacity(powerSource.getCurrentCapacity());
    		battery.setCycleCount(powerSource.getCycleCount());
    		battery.setDesignCapacity(powerSource.getDesignCapacity());
    		battery.setMaxCapacity(powerSource.getMaxCapacity());
    		battery.setPowerOnLine(powerSource.isPowerOnLine());
    		battery.setRemainingCapacityPercent(powerSource.getRemainingCapacityPercent());
    		battery.setTemperature(powerSource.getTemperature());
    		batteries.add(battery);
    	}
    	return batteries;
    }
    
    //1.将信息分为两部分，一部分为实时信息，EdgeX实时调取。另一部分为统计信息，windows记录在eventlog
    public DeviceInfo getDeviceInfo(SystemInfo si ) {
    	logger.info("getDeviceInfo...");
    	List<PowerSource> list = si.getHardware().getPowerSources();
    	oshi.software.os.OperatingSystem os = si.getOperatingSystem();
    	ComputerSystem computerSystem= si.getHardware().getComputerSystem();
    	
    	DeviceInfo deviceInfo = new DeviceInfo();
    	deviceInfo.setDeviceName(computerSystem.getModel()+":"+computerSystem.getHardwareUUID());
    	deviceInfo.setDeviceStatus("online");
    	deviceInfo.setDeviceUptime(FormatUtil.formatElapsedSecs(os.getSystemUptime()));
    	deviceInfo.setDeviceBootedDate(Instant.ofEpochSecond(os.getSystemBootTime()).toString());
    	if(list.get(0)!=null) {
    		PowerSource powerSource = list.get(0);
    		deviceInfo.setBatteryRemainingCapacity(powerSource.getRemainingCapacityPercent());
    		boolean isPowerOnLine = powerSource.isPowerOnLine();//有外接电源
    		if(isPowerOnLine) {
    			deviceInfo.setBatteryStatus("PowerOnLine");
    			String winEE= "9kwh";
            	deviceInfo.setDeviceEnergyEfficiency(winEE);
    		}else if(powerSource.isCharging()){
    			deviceInfo.setBatteryStatus("Charging");
    			String winEE= "9kwh";
            	deviceInfo.setDeviceEnergyEfficiency(winEE);
    		}else {
    			deviceInfo.setBatteryStatus("Discharging");
    			double winKwh=Math.abs(powerSource.getPowerUsageRate())/1000;
            	String winEE=winKwh +"kwh";
            	deviceInfo.setDeviceEnergyEfficiency(winEE);
    		}
    		deviceInfo.setBatteryTemperature(powerSource.getTemperature());
    		//TODO Question:我们需要计算电池在非充电状态下的使用时间？如何计算。
    		deviceInfo.setBatteryUsageTime(FormatUtil.formatElapsedSecs(os.getSystemUptime()));
    		
    	}
    	if(os.getFamily().equals("Windows")) {
	    	Map<String,String> map = new WinLog().getSummaryUpTimeAndCount();
		    deviceInfo.setDeviceUsageCount(map.get("Count"));
		    //使用率=每周开机时间/每周总时间；闲置率=1-使用率；使用频次等于每周开机次数；
		    deviceInfo.setDeviceUsageRate(map.get("UseRate"));
		    deviceInfo.setDeviceFreeRate(map.get("FreeRate"));
    	}else if(os.getFamily().equals("macOS")){
    		deviceInfo.setDeviceUsageCount("0");
		    //使用率=每周开机时间/每周总时间；闲置率=1-使用率；使用频次等于每周开机次数；
		    deviceInfo.setDeviceUsageRate("0");
		    deviceInfo.setDeviceFreeRate("0");
    	}
    	deviceInfo.setCurrentRecordTime(new Date());
    	
    	return deviceInfo;
    }   

}
