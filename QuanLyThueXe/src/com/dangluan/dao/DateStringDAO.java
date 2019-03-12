package com.dangluan.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class DateStringDAO {
	public static SimpleDateFormat sdf;
	public static Calendar cal = Calendar.getInstance();

	public static Date stringtoDate(String data, HttpServletRequest request) {
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = sdf.parse(data);
			cal.setTime(date);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			String pattern = year + "-" + month + "-" + day;
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(pattern);
		} catch (ParseException e) {
			request.setAttribute("message_info",
					"Vui lòng nhập đúng định dạng cho ngày bắt đầu và kết thúc.VD : 07-09-2018");
		}
		return date;
	}

	public static String datetoString(Date date) {
		String data = sdf.format(date);
		return data;
	}

	public static String[] getParameterName(String line, int indexParam) {
		String data = line.replaceAll("\"", "");
		String subData = data.substring(1, data.length() - 1);
		String[] arrParaName = new String[indexParam];
		String[] arrData = subData.split(",");
		for (int i = 0; i < arrData.length; i++) {
			String[] arrSubData = arrData[i].split(":");
			arrParaName[i] = arrSubData[0];
		}
		return arrParaName;
	}

	public static boolean checkInputIsNumber(String inputData) {
		Pattern pattern = Pattern.compile("\\d*");
		Matcher matcher = pattern.matcher(inputData);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

}
