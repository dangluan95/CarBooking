package com.dangluan.bean;

import java.util.Date;

public class Renting 
{
	private int renting_id;
	private Date startLeasingTime;
	private Date endLeasingTime;
	private User user;
	private Vehicle vehicle;
	
	public Renting(Date startLeasingTime, Date endLeasingTime, User user, Vehicle vehicle) {
		super();
		this.startLeasingTime = startLeasingTime;
		this.endLeasingTime = endLeasingTime;
		this.user = user;
		this.vehicle = vehicle;
	}
	public Renting() {
		super();
	}
	public int getRenting_id() {
		return renting_id;
	}
	public void setRenting_id(int renting_id) {
		this.renting_id = renting_id;
	}
	public Date getStartLeasingTime() {
		return startLeasingTime;
	}
	public void setStartLeasingTime(Date startLeasingTime) {
		this.startLeasingTime = startLeasingTime;
	}
	public Date getEndLeasingTime() {
		return endLeasingTime;
	}
	public void setEndLeasingTime(Date endLeasingTime) {
		this.endLeasingTime = endLeasingTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
}
