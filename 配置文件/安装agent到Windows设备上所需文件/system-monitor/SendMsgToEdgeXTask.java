package com.hydsoft.monintors.tasks;

import java.util.Date;
import java.util.List;

import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.hydsoft.monintors.entities.Battery;
import com.hydsoft.monintors.entities.Cpu;
import com.hydsoft.monintors.entities.Memory;
import com.hydsoft.monintors.entities.OperatingSystem;
import com.hydsoft.monintors.windows.GetHardWareInfoService;

import oshi.SystemInfo;

@Component
public class SendMsgToEdgeXTask {
	
	private static final Logger logger = LoggerFactory.getLogger(SendMsgToEdgeXTask.class);
	
	@Value(value= "${batteryUrl}")
	private String batteryUrl;
	
	@Value(value= "${cpuUrl}")
	private String cpuUrl;
	
	@Value(value= "${memUrl}")
	private String memUrl;
	
	@Value(value= "${osUrl}")
	private String osUrl;
	
	@Autowired
	GetHardWareInfoService getHardWareInfoService;
	
	@Autowired
	RestTemplate restTemplate;
	
	public void send() {
		SystemInfo si = new SystemInfo();
		OperatingSystem os = getHardWareInfoService.getOsSystemInfo(si); 
		Cpu cpu = getHardWareInfoService.getCPUInfo(si);
		Memory memory = getHardWareInfoService.getMemoryInfo(si);
		List<Battery> battery = getHardWareInfoService.getBatteryInfo(si);
		
	    String osjson = JSON.toJSONString(os);
	    logger.info("Send os json meessage to EdgeX : {}",osjson);
	    restCall(osjson, osUrl);
	    
	    String cpujson = JSON.toJSONString(cpu);
	    logger.info("Send cpu json meessage to EdgeX : {}",cpujson);
	    restCall(cpujson, cpuUrl);
	    
	    String memoryjson = JSON.toJSONString(memory);
	    logger.info("Send memory json meessage to EdgeX : {}",memoryjson);
	    restCall(memoryjson, memUrl);
	    
	    String batteryjson = JSON.toJSONString(battery);
	    logger.info("Send battery json meessage to EdgeX : {}",batteryjson);
	    restCall(batteryjson, batteryUrl);
	    
	}
	
	@Retryable(
		value = {RestClientException.class},
		backoff = @Backoff(delay = 2000L, multiplier = 2)
	)
	public void restCall(String json,String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setConnection(HTTP.CONN_KEEP_ALIVE);
		HttpEntity<Object> requestEntity = new HttpEntity<>(json,headers);
		ResponseEntity<String> responseEntity =
				restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);
		if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			logger.info("Send json meessage to EdgeX succeess!");
		}
		
	}
	
	

}
