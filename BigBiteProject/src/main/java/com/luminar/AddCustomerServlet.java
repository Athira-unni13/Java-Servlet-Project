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

@WebServlet("/addcustomer")
public class AddCustomerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String user = "root";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String pass = "AdminAthi123";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String custName = "";
	String custaddress = "";
	String custPhone = "";
	String custEmail = "";
	String action = "";

	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			


			custName = request.getParameter("cname");
			custaddress = request.getParameter("caddress");
			custPhone = request.getParameter("cphone");
			custEmail = request.getParameter("cemail");
			action = request.getParameter("action");
			pstmt = conn.prepareStatement(
					"insert into customer_basic_details(customer_name,customer_address,customer_phno,customer_email)"
							+ " values(?,?,?,?)");
			pstmt.setString(1, custName);
			pstmt.setString(2, custaddress);
			pstmt.setString(3, custPhone);
			pstmt.setString(4, custEmail);
			
			int check = pstmt.executeUpdate();
			if (check > 0) {
				if ("saveAndContinue".equals(action)) {
					
					HttpSession session = request.getSession();
					session.setAttribute("customerName", custName);
					session.setAttribute("customerPhone", custPhone);
					//response.sendRedirect("payment");
					dis = request.getRequestDispatcher("payment");
					dis.include(request, response);
				} else {
					response.getWriter().println(
							"<script>alert('Customer saved successfully!');window.location='adminhome';</script>");
				}
			} else {
				response.getWriter().println(
						"<script>alert('failed to insert thecustomer details');window.location='customer.html';</script>");
			}

		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}

	}

}
