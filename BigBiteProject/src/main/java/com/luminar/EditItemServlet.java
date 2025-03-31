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

@WebServlet("/edititem")
public class EditItemServlet extends HttpServlet {

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
			
			int id = Integer.parseInt(request.getParameter("eid"));
			
			pstmt = conn.prepareStatement("select * from meal_items where meal_item_id="+id+"");
			rs=pstmt.executeQuery();
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			out.println("<html>"
					+ "<body>"
					+ "<head>"
					+ "<link rel='stylesheet' type='text/css' href='asserts/css/style.css'/>"
					+ "</head>");
			RequestDispatcher dis = request.getRequestDispatcher("menu");
			dis.include(request, response);
			out.println( "<h1>Edit Item Details</h1>"
					+ "<form name='edit-item' method='post' action='updateitem'>");
			while(rs.next()) {
				out.println("<input type='hidden' name='itemId' value='"+rs.getInt(1)+"'/><br><br>"
						+ "Item Name<input type='text' name='itemname' value='"+rs.getString(2)+"'/><br><br>"
						+ "Item Description<input type='text' name='itemdesc' value='"+rs.getString(3)+"'><br><br>"
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
