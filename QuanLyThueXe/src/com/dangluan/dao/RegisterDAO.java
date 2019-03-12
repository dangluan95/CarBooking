package com.dangluan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.dangluan.bean.User;

public class RegisterDAO {
	public static boolean insertAccountUser(Connection conn, User user, HttpServletRequest request) {
		if (accountUsernameCheck(conn, user, request) && accountNumberPhoneCheck(conn, user, request)
				&& accountEmailCheck(conn, user, request)) 
		{
			String sql = "insert into user (username, password, name, number_phone, age, address, email, categorymember) VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement ptmt = null;
			try {
				ptmt = conn.prepareStatement(sql);
				String username = user.getUserName();
				String password = user.getPassWord();
				String name = user.getName();
				String number_phone = user.getNumberPhone();
				int age = user.getAge();
				String address = user.getAddress();
				String email = user.getEmail();
				int categoryMember = 1;
				ptmt.setString(1, username);
				ptmt.setString(2, password);
				ptmt.setString(3, name);
				ptmt.setString(4, number_phone);
				ptmt.setInt(5, age);
				ptmt.setString(6, address);
				ptmt.setString(7, email);
				ptmt.setInt(8, categoryMember);
				int kt = ptmt.executeUpdate();
				if (kt != 0) {
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
		}
		return false;
	}

	public static boolean accountUsernameCheck(Connection conn, User user, HttpServletRequest request) {
		String sql = "select username from user where username=?";
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, user.getUserName());
			rs = ptmt.executeQuery();
			if (!rs.isBeforeFirst()) {
				return true;
			}else
			{
				request.setAttribute("message_info", "Tài khoản đã đăng ký");
			}
		} catch (SQLException e) {
			request.setAttribute("message_info", e.getMessage());
		}
		return false;
	}

	public static boolean accountNumberPhoneCheck(Connection conn, User user, HttpServletRequest request) {
		String sql = "select number_phone from user where number_phone=?";
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, user.getNumberPhone());
			rs = ptmt.executeQuery();
			if (!rs.isBeforeFirst()) {
				return true;
			}else
			{
				request.setAttribute("message_info", "Số điện thoại đã đăng ký");
			}
		} catch (SQLException e) {
			request.setAttribute("message_info", e.getMessage());
		}
		return false;
	}

	public static boolean accountEmailCheck(Connection conn, User user, HttpServletRequest request) {
		String sql = "select email from user where email=?";
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, user.getEmail());
			rs = ptmt.executeQuery();
			if (!rs.isBeforeFirst()) {
				return true;
			}
			else
			{
				request.setAttribute("message_info", "Email đã được đăng ký");
			}
		} catch (SQLException e) {
			request.setAttribute("message_info", e.getMessage());
		}
		return false;
	}

}
