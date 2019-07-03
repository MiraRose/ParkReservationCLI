package com.techelevator.dao;

import java.math.BigDecimal;

public class Campground {
	
	private long campground_id;
	private long park_id;
	private String name;
	private String openFromMonth;
	private String openToMonth;
	private String dailyFee;
	
	public long getCampground_id() {
		return campground_id;
	}
	public void setCampground_id(long campground_id) {
		this.campground_id = campground_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenFromMonth() {
		return openFromMonth;
	}
	public void setOpenFromMonth(String openFromMonth) {
		this.openFromMonth = openFromMonth;
	}
	public String getOpenToMonth() {
		return openToMonth;
	}
	public void setOpenToMonth(String openToMonth) {
		this.openToMonth = openToMonth;
	}
	public String getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(String dailyFee) {
		this.dailyFee = dailyFee;
	}
	public long getPark_id() {
		return park_id;
	}
	public void setPark_id(long park_id) {
		this.park_id = park_id;
	}
	
	
}
