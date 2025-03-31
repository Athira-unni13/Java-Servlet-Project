package com.luminar;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/menubackoffice")
public class MenuBackOfficerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<div>");
        out.println("<a href='customeraddbackofficer'><button>Add Customer</button></a>");
        out.println("<a href='viewcustomersBO'><button>View Customers</button></a>");
        out.println("<a href='itemdetailsBO'><button>Items Details</button></a>");
        out.println("<a href='reportBO'><button>Reports</button></a>");
        out.println("<a href='signout'><button>Sign Out</button></a>");
        out.println("</div>");
		
	}

}
