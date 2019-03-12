package com.dangluan.bean;

public class Vehicle 
{
	private int id;
	private User owner;
	private VehicleType vehicleTypeId;
	private String vehicleName;
	private Location location_id;
	private VehicleStatus vehicleStatus_id;
	
	public Vehicle() {
		super();
	}
	

	public Vehicle(User owner, VehicleType vehicleTypeId, String vehicleName, Location location_id,
			VehicleStatus vehicleStatus_id) {
		super();
		this.owner = owner;
		this.vehicleTypeId = vehicleTypeId;
		this.vehicleName = vehicleName;
		this.location_id = location_id;
		this.vehicleStatus_id = vehicleStatus_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public VehicleType getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(VehicleType vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public Location getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Location location_id) {
		this.location_id = location_id;
	}

	public VehicleStatus getVehicleStatus_id() {
		return vehicleStatus_id;
	}

	public void setVehicleStatus_id(VehicleStatus vehicleStatus_id) {
		this.vehicleStatus_id = vehicleStatus_id;
	}
	

}
