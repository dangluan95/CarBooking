package com.dangluan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.dangluan.bean.Renting;
import com.dangluan.bean.User;
import com.dangluan.bean.Vehicle;
import com.dangluan.dao.DateStringDAO;
import com.dangluan.dao.RentingDAO;
import com.dangluan.db.DBConnection;

@WebServlet("/RentingController")
public class RentingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RentingController() {
		super();
		// TODO Auto-generated constructor stub
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
				sb.append(line).append("\n");
			}
		} finally {
			reader.close();
		}
		try {
			String[] arrParaName = DateStringDAO.getParameterName(sb.toString(), 4);
			String paraStartLeasing = arrParaName[0];
			String paraEndLeasing = arrParaName[1];
			String paraUser = arrParaName[2];
			String paraVehicle = arrParaName[3];
			JSONObject jsonObject = new JSONObject(sb.toString());
			String startLeasing = jsonObject.getString(paraStartLeasing);
			String endLeasing = jsonObject.getString(paraEndLeasing);
			int user_id = jsonObject.getInt(paraUser);
			int vehicle_id = jsonObject.getInt(paraVehicle);
			jsonObject = new JSONObject();
			if (startLeasing.equals("null") && endLeasing.equals("null")) {
				jsonObject.put("code", 0);
				jsonObject.put("result", "Bạn chưa nhập đầy đủ các trường");
				out.println(jsonObject.toString());
			} else if (startLeasing.equals("null")) {
				jsonObject.put("code", 0);
				jsonObject.put("result", "Bạn chưa nhập thời gian bắt đầu thuê xe");
				out.println(jsonObject.toString());
			} else if (endLeasing.equals("null")) {
				jsonObject.put("code", 0);
				jsonObject.put("result", "Bạn chưa nhập thời gian kết thúc thuê xe");
				out.println(jsonObject.toString());
			} else {
				Date startLeasingTime = DateStringDAO.stringtoDate(startLeasing, request);
				Date endLeasingTime = DateStringDAO.stringtoDate(endLeasing, request);
				if (startLeasingTime == null || endLeasingTime == null) {
					jsonObject.put("code", 0);
					jsonObject.put("result", request.getAttribute("message_info"));
					out.println(jsonObject.toString());
				} else {
					User user = new User();
					user.setId(user_id);
					Vehicle vehicle = new Vehicle();
					vehicle.setId(vehicle_id);
					Renting renter = new Renting(startLeasingTime, endLeasingTime, user, vehicle);
					Connection conn = DBConnection.creatConnection();
					boolean kt = RentingDAO.rentingVehicle(conn, renter, request);
					jsonObject = new JSONObject();
					if (kt) {
						jsonObject.put("code", 1);
						jsonObject.put("result", "Đặt xe thành công");
						out.println(jsonObject.toString());
					} else {
						jsonObject.put("code", 0);
						jsonObject.put("result", request.getAttribute("message_info"));
						out.println(jsonObject.toString());
					}
				}

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
