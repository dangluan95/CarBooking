package com.dangluan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dangluan.bean.Renting;
import com.dangluan.bean.Vehicle;
import com.dangluan.bean.VehicleStatus;

public class RentingDAO {

	public static boolean rentingVehicle(Connection conn, Renting renter, HttpServletRequest request) {
		if(!checkOwnerVehicle(conn, renter, request))
		{
			String sql = "insert into renting(startLeasingTime,endLeasingTime,user_id,vehicle_id) values(?,?,?,?)";
			PreparedStatement ptmt = null;
			try {
				ptmt = conn.prepareStatement(sql);
				Date startLeasingTime = renter.getStartLeasingTime();
				Date endLeasingTime = renter.getEndLeasingTime();
				java.sql.Date s1 = new java.sql.Date(startLeasingTime.getTime());
				java.sql.Date s2 = new java.sql.Date(endLeasingTime.getTime());
				ptmt.setDate(1, s1);
				ptmt.setDate(2, s2);
				ptmt.setInt(3, renter.getUser().getId());
				ptmt.setInt(4, renter.getVehicle().getId());
				int kt = ptmt.executeUpdate();
				if (kt != 0) {
					
					VehicleStatus vehicleStatus = new VehicleStatus();
					vehicleStatus.setId(2);
					if (setVehicleStatus(conn, vehicleStatus, renter.getVehicle(),request)) {
						return true;
					}
				}
			} catch (SQLException e) {
				request.setAttribute("message_info", e.getMessage());
			}finally {
				try {
					ptmt.close();
					conn.close();
				} catch (SQLException e) {
					request.setAttribute("message_info", e.getMessage());
				}
			}
		}
		return false;
	}

	public static boolean setVehicleStatus(Connection conn, VehicleStatus vehicleStatus, Vehicle vehicle,HttpServletRequest request) {
		String sql = "update vehicle set vehicle_status_id=? where vehicle_id=?";
		PreparedStatement ptmt = null;
		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, vehicleStatus.getId());
			ptmt.setInt(2, vehicle.getId());
			int kt = ptmt.executeUpdate();
			if (kt != 0) {
				return true;
			}
		} catch (SQLException e) {
			request.setAttribute("message_info", e.getMessage());
		}
		return false;
	}
	public static boolean checkOwnerVehicle(Connection conn, Renting renter, HttpServletRequest request)
	{
		String sql = "select onwer_id from vehicle where vehicle_id = ?";
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, renter.getVehicle().getId());
			rs = ptmt.executeQuery();
			if(rs.isBeforeFirst())
			{
				int onwer_id = 0;
				while(rs.next())
				{
					onwer_id = rs.getInt("onwer_id");
				}
				if(onwer_id == renter.getUser().getId())
				{
					request.setAttribute("message_info", "Đặt xe không thành công");
					return true;
				}
			}
		} catch (SQLException e) {
			request.setAttribute("message_info", e.getMessage());
		}
		return false;
	}
}
