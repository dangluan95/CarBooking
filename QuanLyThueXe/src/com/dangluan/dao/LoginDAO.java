package com.dangluan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.dangluan.bean.User;

public class LoginDAO {
	public static boolean authentication(Connection conn, User user, HttpServletRequest request) {
		String sql = "select * from user where username=? and password=?";
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, user.getUserName());
			ptmt.setString(2, user.getPassWord());
			rs = ptmt.executeQuery();
			if (rs.isBeforeFirst()) 
			{
				while (rs.next()) {
					int id = rs.getInt("user_id");
					String userName = rs.getString("username");
					String name = rs.getString("name");
					String numberPhone = rs.getString("number_Phone");
					int age = rs.getInt("age");
					String address = rs.getString("address");
					String email = rs.getString("email");
					int categorymember = rs.getInt("categorymember");
					user = new User();
					user.setUserName(userName);
					user.setName(name);
					user.setNumberPhone(numberPhone);
					user.setAge(age);
					user.setAddress(address);
					user.setEmail(email);
					user.setId(id);
					user.setCategoryMemmber(categorymember);
					request.setAttribute("user_info", user);
				}
				return true;
			}
			else
			{
				request.setAttribute("message_info", "Tài khoản hoặc mật khẩu chưa chính xác");
			}

		} catch (SQLException e) {
			request.setAttribute("message_info", e.getMessage());
		} finally {
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
