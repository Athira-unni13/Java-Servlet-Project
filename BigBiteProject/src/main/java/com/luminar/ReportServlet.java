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
import javax.servlet.http.HttpSession;

@WebServlet("/report")
public class ReportServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String user = "root";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String pass = "AdminAthi123";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	RequestDispatcher dis=null;
	HttpSession session = null;
	
	int i=1;
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request,response);
	}
	 
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			pstmt = conn.prepareStatement("SELECT * from report");
			rs=pstmt.executeQuery();
			
			
			
			out.println("<html>"
					+ "<head>"
					+ "<link rel='stylesheet' type='text/css' href='asserts/css/style.css'/>"
					+ "</head>"
					+ "<body>");
			RequestDispatcher dis = request.getRequestDispatcher("menu");
			dis.include(request, response);
			out.println( "<h1>Detailed Report</h1>"
					+ "<table border='1'>"
					+ "<tr>"
					+ "<th><center>Details</center></th>"
					+ "</tr>"
					+ "<tr>"
					+ "<th>Sl No</th>"
					+ "<th>Customer Name</th>"
					+ "<th>Package Name</th>"
					+ "<th>Payment Status</th>"
					+ "<th>Order Status</th>"
					+ "<th>Edit</th>"
					+ "<th>Delete</th>"
					+ "</tr>"
					);
			while(rs.next()) {
			out.println( "<tr>"
					+"<td>"+i+"</td>"
					+ "<td>"+rs.getString(2)+"</td>"
					+ "<td>"+rs.getString(3)+"</td>"
					+ "<td>"+rs.getString(4)+"</td>"
					+ "<td>"+rs.getString(5)+"</td>"
					+ "<td><a href='editreport?eid="+rs.getInt(1)+"'>Edit</a></td>"
					+ "<td><a href='deletereport?did="+rs.getInt(1)+"'>Delete</a>");
					i++;
			}
			out.println("</tr>"
					+ "</table>"
					+ "</body>"
					+ "</html>");
			
		}catch(SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		
		
	}

}
