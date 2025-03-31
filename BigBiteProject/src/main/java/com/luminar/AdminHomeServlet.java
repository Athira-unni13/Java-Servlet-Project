package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/adminhome")
public class AdminHomeServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String user = "root";
	final String url = "jdbc:mysql://localhost:3306/servlet_project";
	final String pass = "AdminAthi123";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	RequestDispatcher dis = null;
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException {
		try {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		String name = (String)session.getAttribute("user");
		
		out.println("<html>"
				+ "<head>"
				+ "<link rel='stylesheet' type='text/css' href='asserts/css/style.css'/>"
				+ "</head>"
				+"<body>"
				+ "<h1>Welcome "+name+"</h1><br><br>");
//		out.println("<div>");
//        out.println("<a href='customer.html'><button>Add Customer</button></a>");
//        out.println("<a href='viewstaff'><button>View Staffs</button></a>");
//        out.println("<a href='viewcustomers'><button>View Customers</button></a>");
//        out.println("<a href='itemdetails'><button>Items Details</button></a>");
//        out.println("<a href='report'><button>Reports</button></a>");
//        out.println("<a href='signout'><button>Sign Out</button></a>");
//        out.println("</div>");
		RequestDispatcher dis = request.getRequestDispatcher("menu");
		dis.include(request, response);
		out.println("</body>"
				+ "</html>");
		out.close();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
