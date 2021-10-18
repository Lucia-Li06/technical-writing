package com.hydsoft.monintors.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: xiaodi.jin
 * @Date: 2021/7/10
 */
public class Event implements Comparable<Event>{
    private int id;
    private int statusCode;
    private long time;
    private Date bootTime;
    private String type;
    private String category;
    private String Source;
    private String log;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Event(int id,int statusCode, long time,Date bootTime, String type, String category, String source, String log) {
        this.id = id;
        this.statusCode = statusCode;
        this.time = time;
        this.bootTime = bootTime;
        this.type = type;
        this.category = category;
        Source = source;
        this.log = log;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Date getBootTime() {
		return bootTime;
	}

	public void setBootTime(Date bootTime) {
		this.bootTime = bootTime;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", status code="+statusCode+
                ", boot time=" + simpleDateFormat.format(bootTime) +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", Source='" + Source + '\'' +
                ", log='" + log + '\'' +
                '}';
    }

	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return o.getBootTime().compareTo(this.getBootTime());
	}

}