

import java.io.IOException;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class PokemonSearch
 */
@WebServlet("/PokemonSearch")
public class PokemonSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// database url
   static final String DB_URL = "jdbc:mysql://localhost3306/pokedex";
   
   //database credentials 
   static final String USER = "root";
   static final String PASS = "sesame80";
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//sql statements
		String sql = "Select Hp, attack, defense from pokemon where name like ?"; 
		
		
		//need to set the response type
				response.setContentType("text/html");
				PrintWriter out = response.getWriter(); 
				
				//get input data from form 
				String name = request.getParameter("name");
				
				
				try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS) )  {
					
					//prepare statement
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, name);
					ResultSet rs = pstmt.executeQuery();
					
					
					//html output
					
					out.println("<!DOCTYPE HMTL><hml><body>");
					
					
				    //table and column 
					out.println("<table>");
					out.println("<tr>");
					out.println("<th>code</th><th>name</th>");
					out.println("<th>Pokemon's name</th>");
					out.println("</tr>");
										
				} // end of try
				
				
	          
	           catch (SQLException e) {
		       e.printStackTrace();
	           } //end of catch
	} // doPost
	 
} // public class
