package com.dangluan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dangluan.bean.History;
import com.dangluan.bean.User;
import com.dangluan.dao.DateStringDAO;
import com.dangluan.dao.HistoryRentingDAO;
import com.dangluan.db.DBConnection;

/**
 * Servlet implementation class HistoryRentingController
 */
@WebServlet("/HistoryRentingController")
public class HistoryRentingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HistoryRentingController() {
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
			String[] arrParaName = DateStringDAO.getParameterName(sb.toString(),1);
			String paraName = arrParaName[0];
			JSONObject jsonObject = new JSONObject(sb.toString());
			User user = new User();
			user.setId(jsonObject.getInt(paraName));
			jsonObject = new JSONObject();
			Connection conn = DBConnection.creatConnection();
			boolean kt = HistoryRentingDAO.listVehicleRenting(conn,user,request);
			if(kt)
			{
				List<History> listRenting = (List<History>) request.getAttribute("list_Renting");
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObject2 = new JSONObject();
				jsonArray.put(jsonObject2);
				JSONObject jsObjectHistory = null;
				/*for(int i = 0; i < listRenting.size(); i++)
				{
					jsObjectHistory = new JSONObject(listRenting.get(i));
					jsonObject2.put("vehicle_"+i,jsObjectHistory);
				}*/
				ListIterator<History> listIterator = listRenting.listIterator();
				while(listIterator.hasNext())
				{
					History history = listIterator.next();
					listIterator.set(history);
				}
				int i = 0;
				while(listIterator.hasPrevious())
				{
					jsObjectHistory = new JSONObject(listIterator.previous());
					jsonObject2.put("vehicle_"+i,jsObjectHistory);
					i++;
				}
 				jsonObject.put("code", 1);
				jsonObject.put("result", jsonArray);
				out.println(jsonObject.toString());
				
			}else
			{
				jsonObject.put("code", 0);
				jsonObject.put("result",request.getAttribute("message_info"));
				out.println(jsonObject.toString());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
