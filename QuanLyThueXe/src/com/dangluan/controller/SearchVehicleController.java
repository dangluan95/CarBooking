package com.dangluan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dangluan.bean.Location;
import com.dangluan.bean.Vehicle;
import com.dangluan.dao.DateStringDAO;
import com.dangluan.dao.SearchVehicleDAO;
import com.dangluan.db.DBConnection;

/**
 * Servlet implementation class SearchVehicleController
 */
@WebServlet("/SearchVehicleController")
public class SearchVehicleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchVehicleController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
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
			String[] arrParaName = DateStringDAO.getParameterName(sb.toString(), 1);
			String paraLocation = arrParaName[0];
			JSONObject jsonObject = new JSONObject(sb.toString());
			int location_id = jsonObject.getInt(paraLocation);
			Location location = new Location();
			location.setId(location_id);
			jsonObject = new JSONObject();
			Connection conn = DBConnection.creatConnection();
			boolean kt = SearchVehicleDAO.searchingVechile(conn, location, request);
			if(kt)
			{
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObject2 = new JSONObject();
				jsonArray.put(jsonObject2);
				JSONObject jsonObject3 = new JSONObject();
				List<Vehicle> listVehicle = (List<Vehicle>) request.getAttribute("list_vehilce");
				for (int i = 0; i < listVehicle.size(); i++) {
					jsonObject3.put("onwer",listVehicle.get(i).getOwner().getName());
					jsonObject3.put("number_phone",listVehicle.get(i).getOwner().getNumberPhone());
					jsonObject3.put("vehicle_type",listVehicle.get(i).getVehicleTypeId().getVehicle_type());
					jsonObject3.put("vehicle_name", listVehicle.get(i).getVehicleName());
					jsonObject2.put("vehicle_"+i, jsonObject3);
					jsonObject3 = new JSONObject();
				}
				jsonObject.put("code", 1);
				jsonObject.put("result", jsonArray);
				out.println(jsonObject.toString());
			}else
			{
				jsonObject.put("code", 0);
				jsonObject.put("result", request.getAttribute("message_info"));
				out.println(jsonObject.toString());
			}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
