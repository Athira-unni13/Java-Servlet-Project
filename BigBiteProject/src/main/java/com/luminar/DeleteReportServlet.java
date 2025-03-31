package com.luminar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deletereport")
public class DeleteReportServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String user = "root";
	final String pass = "AdminAthi123";

	Connection conn = null;
	PreparedStatement pstmt = null;
	RequestDispatcher dis = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);

			int reportId = Integer.parseInt(request.getParameter("did"));

			pstmt = conn.prepareStatement("delete from report where report_id=?");
			pstmt.setInt(1, reportId);
			pstmt.executeUpdate();
//			dis = request.getRequestDispatcher("adminhome");
//			dis.include(request, response);
			
			dis = request.getRequestDispatcher("report");
			dis.include(request, response);

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

}
