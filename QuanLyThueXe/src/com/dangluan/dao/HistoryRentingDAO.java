package com.dangluan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dangluan.bean.History;
import com.dangluan.bean.User;

public class HistoryRentingDAO {
	public static boolean listVehicleRenting(Connection conn, User user, HttpServletRequest request) {
		String sql = "select vehicle_type,vehicle_name,name,number_phone,neighborhood,district,startLeasingTime,endLeasingTime from renting inner join vehicle on renting.vehicle_id = vehicle.vehicle_id inner join user on user.user_id = vehicle.onwer_id inner join vehicle_type on vehicle_type.vehicle_type_id = vehicle.vehicle_type_id inner join register_location on register_location.location_id = vehicle.location_id where renting.user_id = ?";
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, user.getId());
			rs = ptmt.executeQuery();
			if (rs.isBeforeFirst()) {
				List<History> listRenting = new ArrayList<History>();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				while(rs.next())
				{
					String vehicle_type = rs.getString("vehicle_type");
					String vehicle_name = rs.getString("vehicle_name");
					String owner = rs.getString("name");
					String number_phone = rs.getString("number_phone");
					String neighborhood = rs.getString("neighborhood");
					String district = rs.getString("district");
					String startLeasingTime = sdf.format(rs.getDate("startLeasingTime"));
					String endLeasingTime= sdf.format(rs.getDate("endLeasingTime"));
					
					History history = new History(vehicle_type, vehicle_name, owner, number_phone, neighborhood, district, startLeasingTime, endLeasingTime);
					listRenting.add(history);
				}
				request.setAttribute("list_Renting", listRenting);
				return true;
			}else
			{
				request.setAttribute("message_info", "Bạn chưa thuê bất kỳ xe nào cả");
			}
		} catch (SQLException e) {
			request.setAttribute("message_info",e.getMessage());
		}finally {
			try {
				rs.close();
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				request.setAttribute("message_info",e.getMessage());
			}
		}
		return false;
	}

}
