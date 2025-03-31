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
import javax.servlet.http.HttpSession;

@WebServlet("/signin")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String user = "root";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String pass = "AdminAthi123";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	RequestDispatcher dis = null;

	String userRole = "";
	String username = "";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String userName = request.getParameter("username");
			String password = request.getParameter("password");

			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);

			pstmt = conn.prepareStatement("select username,user_role from users where username=? AND user_password=?");
			pstmt.setString(1, userName);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				username = rs.getString(1);
				userRole = rs.getString(2);
			}
			
			HttpSession session= request.getSession();
			session.setAttribute("user", username);
			
			if (userRole.equals("Admin")) {
				dis = request.getRequestDispatcher("adminhome");
				dis.include(request, response);
			} else if (userRole.equals("Back Officer")) {
				dis = request.getRequestDispatcher("backofficerhome");
				dis.include(request, response);
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

}
