package com.dangluan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.dangluan.bean.User;
import com.dangluan.dao.DateStringDAO;
import com.dangluan.dao.LoginDAO;
import com.dangluan.db.DBConnection;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');

			}
		} finally {
			reader.close();
		}
		try {
			String[] arrParaName = DateStringDAO.getParameterName(sb.toString(), 2);
			String paraName = arrParaName[0];
			String paraPassWord = arrParaName[1];
			JSONObject jsonObject = new JSONObject(sb.toString());
			String username = jsonObject.getString(paraName);
			String password = jsonObject.getString(paraPassWord);
			jsonObject = new JSONObject();
			if (username.equals("null") && password.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập tài khoản và mật khẩu");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (password.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập mật khẩu");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (username.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập tên tài khoản");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else {
				Connection conn = DBConnection.creatConnection();
				User user = new User();
				user.setUserName(username);
				user.setPassWord(password);
				boolean kt = LoginDAO.authentication(conn, user, request);
				if (kt) {
					user = (User) request.getAttribute("user_info");
					JSONObject jsonObjectUser = new JSONObject(user);
					jsonObject.put("user_info", jsonObjectUser);
					jsonObject.put("code", 1);
					out.println(jsonObject.toString());
				} else {
					jsonObject.put("result", request.getAttribute("message_info"));
					jsonObject.put("code", 0);
					out.println(jsonObject.toString());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
