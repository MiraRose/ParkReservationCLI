package com.techelevator.dao;

import java.sql.Date;

public class Park {

		private long park_id;
		private String name;
		private String location;
		private Date dateEstablished;
		private int area;
		private int numOfVisitors;
		private String description;
		
		public long getPark_id() {
			return park_id;
		}
		public void setPark_id(long park_id) {
			this.park_id = park_id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public Date getDateEstablished() {
			return dateEstablished;
		}
		public void setDateEstablished(Date dateEstablished) {
			this.dateEstablished = dateEstablished;
		}
		public int getArea() {
			return area;
		}
		public void setArea(int area) {
			this.area = area;
		}
		public int getNumOfVisitors() {
			return numOfVisitors;
		}
		public void setNumOfVisitors(int numOfVisitors) {
			this.numOfVisitors = numOfVisitors;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
}
