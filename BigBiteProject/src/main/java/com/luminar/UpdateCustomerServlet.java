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

@WebServlet("/updatecustomer")
public class UpdateCustomerServlet extends HttpServlet{

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
			
			int custId = Integer.parseInt(request.getParameter("custId"));
			String custName = request.getParameter("custname");
			String custAddress = request.getParameter("custaddress");
			String custPhone =request.getParameter("custphone");
			String custEmail = request.getParameter("custemail");
			
			pstmt = conn.prepareStatement("update customer_basic_details set customer_name=?,customer_address=?,"
					+ "customer_phno=?,customer_email=? where customer_id=?");
			pstmt.setString(1, custName);
			pstmt.setString(2, custAddress);
			pstmt.setString(3, custPhone);
			pstmt.setString(4, custEmail);
			pstmt.setInt(5, custId);
			
			pstmt.executeUpdate();
			dis = request.getRequestDispatcher("viewcustomers");
			dis.include(request, response);
			
			
		}catch(SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
	}
}
