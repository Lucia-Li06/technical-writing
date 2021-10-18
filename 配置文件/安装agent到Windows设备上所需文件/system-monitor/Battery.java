package com.hydsoft.monintors.entities;

public class Battery {
	
	private String name;
	
	private String deviceName;
	
	private double remainingCapacityPercent;
	
	private boolean isPowerOnLine;
	
	private boolean isCharging;
	
	private int currentCapacity;
	
	private int maxCapacity;
	
	private int designCapacity;
	
	private int cycleCount;
	
	private double temperature;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public double getRemainingCapacityPercent() {
		return remainingCapacityPercent;
	}

	public void setRemainingCapacityPercent(double remainingCapacityPercent) {
		this.remainingCapacityPercent = remainingCapacityPercent;
	}

	public boolean isPowerOnLine() {
		return isPowerOnLine;
	}

	public void setPowerOnLine(boolean isPowerOnLine) {
		this.isPowerOnLine = isPowerOnLine;
	}

	public boolean isCharging() {
		return isCharging;
	}

	public void setCharging(boolean isCharging) {
		this.isCharging = isCharging;
	}

	public int getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public int getDesignCapacity() {
		return designCapacity;
	}

	public void setDesignCapacity(int designCapacity) {
		this.designCapacity = designCapacity;
	}

	public int getCycleCount() {
		return cycleCount;
	}

	public void setCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
}
