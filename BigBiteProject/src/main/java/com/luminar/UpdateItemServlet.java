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

@WebServlet("/updateitem")
public class UpdateItemServlet extends HttpServlet{

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
			
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			String itemName = request.getParameter("itemname");
			String itemDesc = request.getParameter("itemdesc");
			pstmt = conn.prepareStatement("update meal_items set meal_items_name=?,meal_item_desc=?"
					+ "where meal_item_id=?");
			pstmt.setString(1, itemName);
			pstmt.setString(2, itemDesc);
			pstmt.setInt(3, itemId);
			
			pstmt.executeUpdate();
			dis = request.getRequestDispatcher("itemdetails");
			dis.include(request, response);
			
			
		}catch(SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
	}

}
