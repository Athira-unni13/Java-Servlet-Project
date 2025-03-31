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

@WebServlet("/editcustomer")
public class EditCustomerServlet extends HttpServlet{

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
			
			int id = Integer.parseInt(request.getParameter("ecid"));
			
			pstmt = conn.prepareStatement("select * from customer_basic_details where customer_id="+id+"");
			rs=pstmt.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.println("<html>"
					+ "<head>"
					+ "<link rel='stylesheet' type='text/css' href='asserts/css/style.css'/>"
					+ "</head"
					+ "<body>");
			RequestDispatcher dis = request.getRequestDispatcher("menu");
			dis.include(request, response);
			out.println( "<h1>Edit Customers Details</h1>"
					+ "<form name='edit-customer' method='post' action='updatecustomer'>");
			while(rs.next()) {
				out.println("<input type='hidden' name='custId' value='"+rs.getInt(1)+"'/><br><br>"
						+ "Full Name<input type='text' name='custname' value='"+rs.getString(2)+"'/><br><br>"
						+ "Address<textarea name='custaddress' rows='4' cols='50' value='"+rs.getString(3)+"'></textarea><br><br>"
						+ "Phone number<input type='text' name='custphone' value='"+rs.getString(4)+"'/><br><br>"
						+ "Email<input type='email' name='custemail' value='"+rs.getString(5)+"'/><br><br>"
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
