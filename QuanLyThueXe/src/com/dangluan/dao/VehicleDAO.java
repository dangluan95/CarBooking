package com.dangluan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.dangluan.bean.Vehicle;

public class VehicleDAO {
	public static boolean registerVehicle(Connection conn, Vehicle vehicle, HttpServletRequest request) {

		if (checkRegisterVehicle(conn, vehicle, request)) 
		{
			String sql = "insert into vehicle (onwer_id,vehicle_type_id,vehicle_name,location_id,vehicle_status_id) values(?,?,?,?,?)";
			PreparedStatement ptmt = null;
			try {
				ptmt = conn.prepareStatement(sql);
				ptmt.setInt(1, vehicle.getOwner().getId());
				ptmt.setInt(2, vehicle.getVehicleTypeId().getVehicle_type_id());
				ptmt.setString(3, vehicle.getVehicleName());
				ptmt.setInt(4, vehicle.getLocation_id().getId());
				ptmt.setInt(5, vehicle.getVehicleStatus_id().getId());
				int kt = ptmt.executeUpdate();
				if (kt != 0) {
					request.setAttribute("message_info", "Đăng ký xe thành công");
					return true;
				}
			} catch (SQLException e) {
				request.setAttribute("message_info", e.getMessage());
			} finally {
				try {
					ptmt.close();
					conn.close();
				} catch (SQLException e) {
					request.setAttribute("message_info", e.getMessage());
				}
			}
		}else
		{
			request.setAttribute("message_info", "Đăng ký xe không thành công, xe đã được đăng ký");
		}
		return false;

	}

	public static boolean checkRegisterVehicle(Connection conn, Vehicle vehicle, HttpServletRequest request) {
		String sql = "select * from vehicle where onwer_id=? and vehicle_type_id=? and vehicle_name=? and location_id=?";
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, vehicle.getOwner().getId());
			ptmt.setInt(2, vehicle.getVehicleTypeId().getVehicle_type_id());
			ptmt.setString(3, vehicle.getVehicleName());
			ptmt.setInt(4, vehicle.getLocation_id().getId());
			rs = ptmt.executeQuery();
			if (!rs.isBeforeFirst()) {
				return true;
			}

		} catch (SQLException e) {
			request.setAttribute("message_info", e.getMessage());
		}
		return false;
	}

}
