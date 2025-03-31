package com.luminar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatereport")
public class UpdateReportServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String user = "root";
	final String pass = "AdminAthi123";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	RequestDispatcher dis=null;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pass);
			
			int reportId = Integer.parseInt(request.getParameter("reportid"));
			String orderStatus = request.getParameter("orderstatus");
			pstmt = conn.prepareStatement("update report set order_status=?"
					+ "where report_id=?");
			pstmt.setString(1, orderStatus);
			pstmt.setInt(2, reportId);
			
			pstmt.executeUpdate();
			dis = request.getRequestDispatcher("report");
			dis.include(request, response);
			
			
		}catch(SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
	}

}
