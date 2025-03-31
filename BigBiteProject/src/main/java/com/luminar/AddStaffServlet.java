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

@WebServlet("/addstaff")
public class AddStaffServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String user = "root";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String pass = "AdminAthi123";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String staffName = "";
	String staffaddress = "";
	String staffPhone = "";
	String staffEmail = "";
	String staffRole = "";
	String userRole = "";
	String userName = "";
	String userPass = "";
	int staffId;
	float staffSalary;

	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);

			staffName = request.getParameter("sname");
			staffaddress = request.getParameter("saddress");
			staffPhone = request.getParameter("sphone");
			staffEmail = request.getParameter("semail");
			staffSalary = Float.valueOf(request.getParameter("ssalary"));

			staffRole = request.getParameter("srole");
			userName = request.getParameter("uname");
			userPass = request.getParameter("upassword");

			pstmt = conn.prepareStatement(
					"insert into staffs(staff_name,staff_address,staff_email,staff_salary,staff_role,staff_phone)"
							+ " values(?,?,?,?,?,?)");
			pstmt.setString(1, staffName);
			pstmt.setString(2, staffaddress);
			pstmt.setString(3, staffEmail);
			pstmt.setFloat(4, staffSalary);
			pstmt.setString(5, staffRole);
			pstmt.setString(6, staffPhone);

			pstmt.executeUpdate();
			
			pstmt =conn.prepareStatement("SELECT staff_id FROM staffs WHERE staff_role='Admin' OR staff_role='Back Officer' AND staff_email='"+staffEmail+"'");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				staffId = rs.getInt(1);
			}
			pstmt = conn.prepareStatement("INSERT INTO users(staff_id,username,user_password,user_role) VALUES(?,?,?)");
			pstmt.setInt(1, staffId);
			pstmt.setString(2, userName);
			pstmt.setString(3, userPass);
			pstmt.setString(4, staffRole);
			
			
			pstmt.executeUpdate();
			response.sendRedirect("login.html");

		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}

	}
}