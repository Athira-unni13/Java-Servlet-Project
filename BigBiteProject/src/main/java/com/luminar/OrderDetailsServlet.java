package com.luminar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/order")
public class OrderDetailsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String user = "root";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String pass = "AdminAthi123";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	RequestDispatcher dis=null;
	
	int custId;
	String custName="";
	String custPhone="";
	int selectedPackage;
	int selectedBreakfast;
	int selectedLunch;
	int selectedDinner;
	int paymentId;
	int mealPackageId;
	int orderId;
	float paymentAmount;
	String packageName = "";
	String paymentMethod="";
	String OrderStartDate;
	String OrderEndDate;
	String OrderStatus = "INCOMPLETE";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			
			HttpSession session = request.getSession(false);
			custName=(String)session.getAttribute("customerName");
			custPhone = (String) session.getAttribute("customerPhone");
			selectedPackage= Integer.valueOf(request.getParameter("packages")) ;
			selectedBreakfast = Integer.valueOf(request.getParameter("breakfast"));
			selectedLunch = Integer.valueOf(request.getParameter("lunch"));
			selectedDinner = Integer.valueOf(request.getParameter("dinner"));
			OrderStartDate = request.getParameter("startdate");
			OrderEndDate =request.getParameter("enddate");
			paymentAmount = Float.valueOf(request.getParameter("price"));
			paymentMethod = request.getParameter("paymentmethod");
			session.setAttribute("selectedPackage", selectedPackage);
			LocalDate currentDate = LocalDate.now();
//			System.out.println(custName);
//			System.out.println(custPhone);
//			System.out.println(selectedOption);
			
			pstmt = conn.prepareStatement("select customer_id from customer_basic_details where customer_name=? AND customer_phno=?");
			pstmt.setString(1, custName);
			pstmt.setString(2, custPhone);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				custId = rs.getInt(1);
			}
			 
			
			pstmt = conn.prepareStatement("INSERT INTO meal_package_details(package_id,breakfast_item_id,lunch_item_id,dinner_item_id,customer_id) VALUES(?,?,?,?,?)");
			pstmt.setInt(1, selectedPackage);
			pstmt.setInt(2, selectedBreakfast);
			pstmt.setInt(3, selectedLunch);
			pstmt.setInt(4, selectedDinner);
			pstmt.setInt(5, custId);
			int rowAffected = pstmt.executeUpdate();
			
			if(rowAffected>0) {
				System.out.println("Inserted");
			}else {
				System.out.println("Not Inserted");
			}
			
			
			pstmt = conn.prepareStatement("SELECT meal_package_id from meal_package_details where customer_id='"+custId+"'");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mealPackageId = rs.getInt(1);
			}
			
			pstmt = conn.prepareStatement("INSERT INTO order_details(customer_id,order_date,order_start_date,"
					+ "order_end_date,order_status,meal_package_id) VALUES(?,?,?,?,?,?)");
			pstmt.setInt(1, custId);
			pstmt.setDate(2, Date.valueOf(currentDate));
			pstmt.setDate(3, Date.valueOf(OrderStartDate));
			pstmt.setDate(4, Date.valueOf(OrderEndDate));
			pstmt.setString(5, OrderStatus);
//			if(Date.valueOf(currentDate) != Date.valueOf(OrderEndDate)) {
//				pstmt.setString(5, OrderStatus);
//			}else {
//				OrderStatus = "COMPLETED";
//				pstmt.setString(5, OrderStatus);
//			}
			pstmt.setInt(6, mealPackageId);
			
			int update = pstmt.executeUpdate();
			if(update>0) {
				System.out.println("Inserted");
			}else {
				System.out.println("Not inserted");
			}
			
			pstmt = conn.prepareStatement("SELECT order_id from order_details");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderId = rs.getInt(1);
			}
			
			pstmt = conn.prepareStatement("INSERT INTO payment_details(order_id,customer_id,payment_date,payment_amount,"
					+ "payment_method,payment_status) VALUES(?,?,?,?,?,?)");
			pstmt.setInt(1, orderId);
			pstmt.setInt(2, custId);
			pstmt.setDate(3, Date.valueOf(currentDate));
			pstmt.setFloat(4, paymentAmount);
			pstmt.setString(5, paymentMethod);
			pstmt.setString(6, "COMPLETED");
		
			int paymentUpdate = pstmt.executeUpdate();
			
			if(paymentUpdate>0) {
				System.out.println("inserted");
			}else {
				System.out.println("Not Inserted");
			}
			
			pstmt = conn.prepareStatement("SELECT package_name from packages where package_id="+selectedPackage+"");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				packageName = rs.getString(1);
			}
			
			
			pstmt = conn.prepareStatement("INSERT INTO report(customer_name,package_name,payment_status,order_status) VALUES(?,?,?,?)");
			pstmt.setString(1, custName);
			pstmt.setString(2, packageName);
			pstmt.setString(3, "COMPLETED");
			pstmt.setString(4, OrderStatus);
			
			pstmt.executeUpdate();
			
			dis = request.getRequestDispatcher("report");
			dis.include(request, response);
			
		}catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
