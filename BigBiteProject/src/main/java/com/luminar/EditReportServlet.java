package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/editreport")

public class EditReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String user = "root";
	final String pass = "AdminAthi123";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pass);
			
			int id = Integer.parseInt(request.getParameter("eid"));
			
			pstmt = conn.prepareStatement("select * from report where report_id="+id+"");
			rs=pstmt.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.println("<html>"
					+ "<body>"
					+ "<head>"
					+ "<link rel='stylesheet' type='text/css' href='asserts/css/style.css'/>"
					+ "</head>");
			RequestDispatcher dis = request.getRequestDispatcher("menu");
			dis.include(request, response);
			out.println( "<h1>Edit Report Details</h1>"
					+ "<form name='edit-report' method='post' action='updatereport'>");
			while(rs.next()) {
				out.println("<input type='hidden' name='reportid' value='"+rs.getInt(1)+"'/><br><br>"
						+ "Package Name<input type='text' name='packagename' value='"+rs.getString(3)+"' readOnly/><br><br>"
						+ "Payment Status<input type='text' name='packagestatus' value='"+rs.getString(4)+"' readOnly><br><br>"
						+ "Order Status<input type='text' name='orderstatus' value='"+rs.getString(5)+"' /><br><br>"
						+ "<input type='submit' class='inputbutton' value='UPDATE' />");
			}
			out.println("</form>"
					+ "</body>"
					+ "</html>");
			out.close();
			
			
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}
