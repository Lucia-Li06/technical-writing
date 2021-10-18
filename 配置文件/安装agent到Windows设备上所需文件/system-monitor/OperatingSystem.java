package com.hydsoft.monintors.entities;

import java.time.Instant;
import java.util.List;

import oshi.software.os.OSSession;

public class OperatingSystem {

    private String manufacturer;

    private String family;

    private String osName;

    private int bitness;
    
    private Instant systemBootTime;
    
    private String upTime;
    
    private List <OSSession> osSessions;

    public List<OSSession> getOsSessions() {
		return osSessions;
	}

	public void setOsSessions(List<OSSession> osSessions) {
		this.osSessions = osSessions;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public int getBitness() {
		return bitness;
	}

	public void setBitness(int bitness) {
		this.bitness = bitness;
	}

	public Instant getSystemBootTime() {
		return systemBootTime;
	}

	public void setSystemBootTime(Instant systemBootTime) {
		this.systemBootTime = systemBootTime;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }
}
