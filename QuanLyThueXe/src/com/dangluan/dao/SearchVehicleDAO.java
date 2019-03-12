package com.dangluan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dangluan.bean.Location;
import com.dangluan.bean.User;
import com.dangluan.bean.Vehicle;
import com.dangluan.bean.VehicleType;

public class SearchVehicleDAO 
{
	public static boolean searchingVechile(Connection conn, Location location, HttpServletRequest request)
	{
		String sql = "select name,vehicle_type,vehicle_name,number_phone from vehicle inner join user on vehicle.onwer_id = user.user_id inner join vehicle_type on vehicle.vehicle_type_id = vehicle_type.vehicle_type_id where location_id = ? and vehicle_status_id = 1";
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, location.getId());
			rs = ptmt.executeQuery();
			if(rs.isBeforeFirst())
			{
				List<Vehicle> listVehilce = new ArrayList<Vehicle>();
				while(rs.next())
				{
					Vehicle vehicle = new Vehicle();
					User owner = new User();
					owner.setName(rs.getString("name"));
					owner.setNumberPhone(rs.getString("number_phone"));
					String vehicle_name = rs.getString("vehicle_name");
					VehicleType type = new VehicleType();
					type.setVehicle_type(rs.getString("vehicle_type"));
					vehicle.setOwner(owner);
					vehicle.setVehicleName(vehicle_name);
					vehicle.setVehicleTypeId(type);
					listVehilce.add(vehicle);
				}
				request.setAttribute("list_vehilce", listVehilce);
				return true;
			}
			else
			{
				request.setAttribute("message_info", "Không tìm thấy bất kỳ xe nào cho thuê trong địa điểm bạn tìm kiếm");
			}
		} catch (SQLException e) {
			request.setAttribute("message_info", e.getMessage());
		}finally {
			try {
				rs.close();
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				request.setAttribute("message_info", e.getMessage());
			}
		}
		return false;
	}
}
