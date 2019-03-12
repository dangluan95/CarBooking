package com.dangluan.bean;

public class History 
{
	private String vehicle_type;
	private String vehicle_name;
	private String owner;
	private String number_phone;
	private String neighborhood;
	private String district;
	private String startLeasingTime;
	private String endLeasingTime;
	
	public History() {
		super();
	}
	public History(String vehicle_type, String vehicle_name, String owner, String number_phone, String neighborhood,
			String district, String startLeasingTime, String endLeasingTime) {
		super();
		this.vehicle_type = vehicle_type;
		this.vehicle_name = vehicle_name;
		this.owner = owner;
		this.number_phone = number_phone;
		this.neighborhood = neighborhood;
		this.district = district;
		this.startLeasingTime = startLeasingTime;
		this.endLeasingTime = endLeasingTime;
	}
	public String getVehicle_type() {
		return vehicle_type;
	}
	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}
	public String getVehicle_name() {
		return vehicle_name;
	}
	public void setVehicle_name(String vehicle_name) {
		this.vehicle_name = vehicle_name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getNumber_phone() {
		return number_phone;
	}
	public void setNumber_phone(String number_phone) {
		this.number_phone = number_phone;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStartLeasingTime() {
		return startLeasingTime;
	}
	public void setStartLeasingTime(String startLeasingTime) {
		this.startLeasingTime = startLeasingTime;
	}
	public String getEndLeasingTime() {
		return endLeasingTime;
	}
	public void setEndLeasingTime(String endLeasingTime) {
		this.endLeasingTime = endLeasingTime;
	}
	
	
	

}
