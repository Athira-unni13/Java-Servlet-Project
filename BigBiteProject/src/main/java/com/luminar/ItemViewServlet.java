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

@WebServlet("/itemdetails")
public class ItemViewServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String user = "root";
	final String pass = "AdminAthi123";

	String staffName = "";
	String staffAddress = "";
	String staffemail = "";
	String staffRole = "";
	float staffSalary;
	
	Connection conn =null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pass);
			
			pstmt = conn.prepareStatement("select * from meal_items");
			rs=pstmt.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.println("<html>"
					+ "<head>"
					+ "<link rel='stylesheet' type='text/css' href='asserts/css/style.css'/>"
					+ "</head>"
					+ "<body>");
			RequestDispatcher dis = request.getRequestDispatcher("menu");
			dis.include(request, response);
			out.println("<h1>Item Details</h1>"
					+ "<table border='1'>"
					+ "<tr>"
					+ "<th>Sl No.</th>"
					+ "<th>Item Name</th>"
					+ "<th>Item Description</th>"
					+ "<th>Edit</th>"
					+ "<th>Delete</th>"
					+ "</tr>");
			int i = 1;
			while(rs.next()) {
				out.println("<tr>"
							+"<td>"+i+"</td>"
							+"<td>"+rs.getString(2)+"</td>"
							+"<td>"+rs.getString(3)+"</td>"
							+"<td><a href=edititem?eid="+rs.getInt(1)+">Edit</a></td>"
							+"<td><a href=deleteitem?did="+rs.getInt(1)+">Delete</a></td>"
							+"</tr>");
				i++;
			}
			out.println("</table>"
					+ "<a href=adminhome><button>Back to Home<button></a>"
					+ "</body>"
					+ "</html>");
			
			
		}catch(SQLException|ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		
	}

}
