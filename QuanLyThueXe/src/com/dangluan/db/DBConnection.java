package com.dangluan.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection 
{
	public static Connection creatConnection()
	{
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/quanlythuexe?useUnicode=true&characterEncoding=UTF-8";
		String userName = "root";
		String passWord = "1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, userName, passWord);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
