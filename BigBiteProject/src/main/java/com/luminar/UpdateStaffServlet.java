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

@WebServlet("/updatestaff")
public class UpdateStaffServlet extends HttpServlet{

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
			
			int staffId = Integer.parseInt(request.getParameter("staffId"));
			String staffName = request.getParameter("staffname");
			String staffAddress = request.getParameter("saddress");
			String staffEmail = request.getParameter("staffemail");
			String staffPhone =request.getParameter("staffphone");
			float staffSalary =Float.valueOf(request.getParameter("staffsalary"));
			String staffRole = request.getParameter("staffrole");
			pstmt = conn.prepareStatement("update staffs set staff_name=?,staff_address=?,"
					+ "staff_email=?,staff_salary=?,staff_role=?,staff_phone=? where staff_id=?");
			pstmt.setString(1, staffName);
			pstmt.setString(2, staffAddress);
			pstmt.setString(3, staffEmail);
			pstmt.setFloat(4, staffSalary);
			pstmt.setString(5, staffRole);
			pstmt.setString(6, staffPhone);
			pstmt.setInt(7, staffId);
			
			pstmt.executeUpdate();
			dis = request.getRequestDispatcher("viewstaff");
			dis.include(request, response);
			
			
		}catch(SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
	}
}
