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
import com.dangluan.dao.RegisterDAO;
import com.dangluan.db.DBConnection;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			reader.close();
		}
		try {
			String[] arrParaName = DateStringDAO.getParameterName(sb.toString(), 7);
			String paraUser = arrParaName[0];
			String paraPass = arrParaName[1];
			String paraName = arrParaName[2];
			String paraNumberPhone = arrParaName[3];
			String paraAge = arrParaName[4];
			String paraAddress = arrParaName[5];
			String paraEmail = arrParaName[6];
			JSONObject jsonObject = new JSONObject(sb.toString());
			String userName = jsonObject.getString(paraUser);
			String passWord = jsonObject.getString(paraPass);
			String name = jsonObject.getString(paraName);
			String numberPhone = jsonObject.getString(paraNumberPhone);
			String sAge = jsonObject.getString(paraAge);
			String address = jsonObject.getString(paraAddress);
			String email = jsonObject.getString(paraEmail);
			jsonObject = new JSONObject();
			if (userName.equals("null") && passWord.equals("null") && name.equals("null") && numberPhone.equals("null")
					&& sAge.equals("null") && address.equals("null") && email.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập bất kỳ trường nào, vui lòng điền đầy đủ thông tin");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (userName.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập tên tài khoản");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (passWord.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập mật khẩu");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (name.equals("null")) {
				jsonObject.put("result", "Vui lòng nhập tên");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (numberPhone.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập số điện thoại");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (sAge.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập tuổi");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (address.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập địa chỉ");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (email.equals("null")) {
				jsonObject.put("result", "Bạn chưa nhập email");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (!DateStringDAO.checkInputIsNumber(numberPhone)) {
				jsonObject.put("result", "Vui lòng nhập số điện thoại chỉ là số");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else if (!DateStringDAO.checkInputIsNumber(sAge)) {
				jsonObject.put("result", "Vui lòng nhập tuổi ở dạng chữ số");
				jsonObject.put("code", 0);
				out.println(jsonObject.toString());
			} else {
				int age = Integer.parseInt(sAge);
				User user = new User(userName, passWord, name, numberPhone, age, address, email);
				Connection conn = DBConnection.creatConnection();
				boolean kt = RegisterDAO.insertAccountUser(conn, user, request);
				jsonObject = new JSONObject();
				if (kt) {
					jsonObject.put("code", 1);
					jsonObject.put("result", "Đăng ký thành công");
					out.println(jsonObject.toString());
				} else {
					jsonObject.put("code", 0);
					jsonObject.put("result", request.getAttribute("message_info"));
					out.println(jsonObject.toString());

				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
