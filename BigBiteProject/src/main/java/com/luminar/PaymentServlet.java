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

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

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

	RequestDispatcher dis = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
	
	
	public void processRequest(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			
			HttpSession session = request.getSession(false);
			custName = (String) session.getAttribute("customerName");
			custPhone = (String) session.getAttribute("customerPhone");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.println("<html>"
					+ "<head>"
					+ "<script>"
					+ "function updatePrice() {"
					+ "var selectBox = document.getElementById('packageDropdown');"
					+ "var selectedOption = selectBox.options[selectBox.selectedIndex];"
					+ "var price = selectedOption.getAttribute('data-price');"
					+ "var desc = selectedOption.getAttribute('data-desc');"
					+ "document.getElementById('priceBox').value = price;"
					+ "document.getElementById('descBox').value=desc;"
					+ "}"
					+ "</script>"
					+ "<link rel='stylesheet' type='text/css' href='asserts/css/style.css'/>"
					+ "</head>"
					+ "<body>"
					+ "<h1>Payment Details</h1>"
					+ "<form name='payment' method='post' action='order'>"
					+ "Customer name<input type=text name=custname value='"+custName+"'readonly/><br><br>"
					+ "Customer Phone Number<input type=text name=custnum value='"+custPhone+"'readonly/><br><br>"
					+ "Select Package<select name='packages' id='packageDropdown' onchange='updatePrice()'>"
					+ "<option>--Select--One--</option>");
			pstmt = conn.prepareStatement("select * from packages");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String packageId = rs.getString(1);
				String packageName = rs.getString(2);
				String descPack = rs.getString(3);
				float packagePrice = rs.getFloat(4);
				out.println("<option value='" + packageId + "' data-price='" + packagePrice + "' data-desc='"+descPack+"'>" + packageName + "</option>");
			}
			
			out.println("</select><br><br>"
					+"Package Description: <input type='text' id='descBox' readonly/><br><br>"
					+ "Package Price: <input type='text' id='priceBox' name='price' readonly/><br><br>"
					+ "Select Breakfast item<select name='breakfast'>"
					+ "<option>--Select--One--</option>");
			pstmt = conn.prepareStatement("select * from breakfast_items");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String breakfast_id = rs.getString(1);
				out.println("<option value ='"+breakfast_id+"' name='breakfastitem'>"+rs.getString(2)+"</option>");
			}
			out.println("</select><br><br>");
			
			out.println("Select Lunch item<select name='lunch'>"
					+ "<option>--Select--One--</option>");
			pstmt = conn.prepareStatement("select * from lunch_items");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String lunch_id = rs.getString(1);
				out.println("<option value='"+lunch_id+"' name='lunchitem'>"+rs.getString(2)+"</option>");
			}
			out.println("</select><br><br>");
			
			out.println("Select Dinner item<select name='dinner'>"
					+ "<option>--Select--One--</option>");
			pstmt = conn.prepareStatement("select * from dinner_items");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String dinner_id = rs.getString(1);
				out.println("<option value='"+dinner_id+"' name='dinneritem'>"+rs.getString(2)+"</option>");
			}
			out.println("</select><br><br>"
					+ "Select the date to Start<input type='date' name='startdate' /><br><br>"
					+ "Select the date to End<input type='date' name='enddate' /><br><br>"
					+ "Select payment mode <select name='paymentmethod'>"
					+ "<option>--Select--One--</option>"
					+ "<option value='Google Pay'>Google Pay</option>"
					+ "<option value='Pay Pal'>Pay Pal</option>"
					+ "<option value='Credit card'>Credit card</option>"
					+ "</select><br><br>"
					+ "<input type='submit'class='inputbutton' value='Proceed to pay' />");
			
			out.println("</form>"
					+ "</body>"
					+ "</html>");

		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		
	}
}
