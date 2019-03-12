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

import com.dangluan.bean.Location;
import com.dangluan.bean.User;
import com.dangluan.bean.Vehicle;
import com.dangluan.bean.VehicleStatus;
import com.dangluan.bean.VehicleType;
import com.dangluan.dao.DateStringDAO;
import com.dangluan.dao.VehicleDAO;
import com.dangluan.db.DBConnection;

/**
 * Servlet implementation class VehicleController
 */
@WebServlet("/VehicleController")
public class VehicleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VehicleController() {
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
			String[] arrParaName = DateStringDAO.getParameterName(sb.toString(), 5);
			String paraOwner = arrParaName[0];
			String paraVehicleType = arrParaName[1];
			String paraVehicleName = arrParaName[2];
			String paraLocationId = arrParaName[3];
			String paraStatusID = arrParaName[4];
			JSONObject jsonObject = new JSONObject(sb.toString());

			String onwer_id = jsonObject.getString(paraOwner);
			String vehicle_type_id = jsonObject.getString(paraVehicleType);
			String vehicle_name = jsonObject.getString(paraVehicleName);
			String location_id = jsonObject.getString(paraLocationId);
			String vehicle_status_id = jsonObject.getString(paraStatusID);
			jsonObject = new JSONObject();
			if (onwer_id.equals("null") && vehicle_type_id.equals("null") && vehicle_name.equals("null")
					&& location_id.equals("null") && vehicle_status_id.equals("null")) {
				jsonObject.put("code", 0);
				jsonObject.put("result", "Bạn chưa nhập đầy đủ các trường");
				out.println(jsonObject.toString());
			} else if (vehicle_type_id.equals("null")) {
				jsonObject.put("code", 0);
				jsonObject.put("result", "Bạn chưa chọn loại xe");
				out.println(jsonObject.toString());
			} else if (vehicle_name.equals("null")) {
				jsonObject.put("code", 0);
				jsonObject.put("result", "Bạn chưa nhập tên xe");
				out.println(jsonObject.toString());
			} else if (location_id.equals("null")) {
				jsonObject.put("code", 0);
				jsonObject.put("result", "Bạn chưa chọn vị trí cho thuê xe");
				out.println(jsonObject.toString());
			} else {

				User owner = new User();
				owner.setId(Integer.parseInt(onwer_id));
				VehicleType vehicleTypeId = new VehicleType();
				vehicleTypeId.setVehicle_type_id(Integer.parseInt(vehicle_type_id));
				Location location = new Location();
				location.setId(Integer.parseInt(location_id));
				VehicleStatus vehicleStatus = new VehicleStatus();
				vehicleStatus.setId(Integer.parseInt(vehicle_status_id));
				Vehicle vehicle = new Vehicle(owner, vehicleTypeId, vehicle_name, location, vehicleStatus);
				Connection conn = DBConnection.creatConnection();
				boolean kt = VehicleDAO.registerVehicle(conn, vehicle, request);
				jsonObject = new JSONObject();
				if (kt) {
					jsonObject.put("code", 1);
					jsonObject.put("result", request.getAttribute("message_info"));
					out.println(jsonObject.toString());
				} else {
					jsonObject.put("code", 0);
					jsonObject.put("result", request.getAttribute("message_info"));
					out.println(jsonObject.toString());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
