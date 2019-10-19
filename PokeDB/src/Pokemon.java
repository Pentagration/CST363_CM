
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Pokemon")
public class Pokemon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// database URL
	static final String DB_URL = "jdbc:mysql://localhost:3306/pokedex?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html"); // Set response content type
		PrintWriter out = response.getWriter();

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
			
			// get input data from form
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String param1 = request.getPrarameter("param1");
			String param2 = request.getPrarameter("param2");
			String param3 = request.getPrarameter("param3");

			if (name.length() > 0)
			{
				String sql = "SELECT p.name, p.type, p.hp, p.attack, p.defense, m.primary_attack, m.secondary_attack\n" + 
						"FROM pokedex.pokemon p\n" + 
						"	JOIN pokedex.moveset m\n" + 
						"		ON p.number = m.number\n" + 
						"WHERE p.name = ?";
				PreparedStatement pstmt =  conn.prepareStatement(sql);
				pstmt.setString(1, name);
				ResultSet rs = pstmt.executeQuery();

				out.println("<!DOCTYPE HTML><html><body>");
				out.println("<img src=\"Pokemon.jpeg\" height=\"137\" width=\"368\" />");
				out.println("<table border = \"1\" width = \"100%\">");
				out.println("<col style = \"width:14%\"> <col style = \"width:14%\"> <col style = \"width:14%\">"
						+ "<col style = \"width:14%\"> <col style = \"width:14%\"> <col style = \"width:14%\"><col style = \"width:14%\">");
				out.println("<thead><tr><th>Name</th> <th>Type</th> <th>HP</th> <th>Attack</th> <th>Defense</th><th>Primary Attack</th><th>Secondary Attack</th></tr></thead>");
				while (rs.next()) 
				{
					out.println("<tbody><tr>");
					out.println("<td>" + rs.getString("name") + "</td>");
					out.println("<td>" + rs.getString("type") + "</td>");
					out.println("<td>" + rs.getInt("hp") + "</td>");
					out.println("<td>" + rs.getInt("attack") + "</td>");
					out.println("<td>" + rs.getInt("defense") + "</td>");
					out.println("<td>" + rs.getString("primary_attack") + "</td>");
					out.println("<td>" + rs.getString("secondary_attack") + "</td>");
					out.println("</tr><tbody>");
				}
				rs.close();
				
				out.println("</table>");
				out.println("</body></html>");
			}
			
			else if (type.length() > 0)
			{
				String sql = "SELECT p.name, m.primary_attack, m.secondary_attack\n" + 
						"FROM pokedex.moveset m\n" + 
						"	JOIN pokedex.pokemon p\n" + 
						"		ON m.number = p.number\n" + 
						"WHERE \n" + 
						"	primary_attack in \n" + 
						"		(SELECT name\n" + 
						"		FROM pokedex.attack\n" + 
						"		WHERE type = ?) OR\n" + 
						"	secondary_attack IN\n" + 
						"		(SELECT name\n" + 
						"		FROM pokedex.attack\n" + 
						"		WHERE type = ?)";
				PreparedStatement pstmt =  conn.prepareStatement(sql);
				pstmt.setString(1, type);
				pstmt.setString(2, type);
				ResultSet rs = pstmt.executeQuery();

				out.println("<!DOCTYPE HTML><html><body>");
				out.println("<img src=\"Pokemon.jpeg\" height=\"137\" width=\"368\" />");
				out.println("<table border = \"1\" width = \"100%\">");
				out.println("<col style = \"width:14%\"> <col style = \"width:14%\"> <col style = \"width:14%\">");
				out.println("<thead><tr><th>Name</th><th>Primary Attack</th><th>Secondary Attack</th></tr></thead>");
				while (rs.next()) 
				{
					out.println("<tbody><tr>");
					out.println("<td>" + rs.getString("name") + "</td>");
					out.println("<td>" + rs.getString("primary_attack") + "</td>");
					out.println("<td>" + rs.getString("secondary_attack") + "</td>");
					out.println("</tr><tbody>");
				}
				rs.close();
				out.println("</table>");
				out.println("</body></html>");
			}

			else if (param1.length() > 0)
			{
				String sql = "SELECT t1.primary_attack AS attack, t1.count AS primary_attack_count, t2.count AS secondary_attack_count\n" +
							"    FROM\n" +
							"        (SELECT\n" +
							"            secondary_attack, COUNT(*) AS count\n" +
							"        FROM\n"
							"            pokedex.moveset\n" +
							"        GROUP BY secondary_attack\n"
							"        ORDER BY COUNT(*) DESC) t2\n"
							"        ON t1.primary_attack = t2.secondary_attack\n" +
							"        WHERE t1.primary_attack = ?"
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setstring(1,param1);
				ResultSet rs = pstmt.executeQuery();
				
				out.println("<!DOCTYPE HTML><html><body>");
				out.println("<img src=\"Pokemon.jpeg\" height=\"137\" width=\"368\" />");
				out.println("<table border = \"1\" width = \"100%\">");
				out.println("<col style = \"width:14%\"> <col style = \"width:14%\"> <col style = \"width:14%\">");
				out.println("<thead><tr><th>Attack</th><th>Primary Count</th><th>Secondary Count</th></tr></thead>");
				out.println("<tbody><tr>");
				out.println("<td>" + rs.getString("attack") + "</td>");
				out.println("<td>" + rs.getString("primary_attack_count") + "</td>");
				out.println("<td>" + rs.getString("secondary_attack_count") + "</td>");
				out.println("</tr><tbody>");
				
				rs.close();
				out.println("</table>");
				out.println("</body></html>");
			}
			
			else if (param2.length() > 0)
			{
				String sql = ""
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setstring(1,param1);
				ResultSet rs = pstmt.executeQuery();
				
				out.println("<!DOCTYPE HTML><html><body>");
				out.println("<img src=\"Pokemon.jpeg\" height=\"137\" width=\"368\" />");
				out.println("<table border = \"1\" width = \"100%\">");
				out.println("<col style = \"width:14%\"> <col style = \"width:14%\"> <col style = \"width:14%\">");
				//out.println("<thead><tr><th>Name</th><th>Primary Attack</th><th>Secondary Attack</th></tr></thead>");
				while (rs.next())
				{
					out.println("<tbody><tr>");
					//out.println("<td>" + rs.getString("name") + "</td>");
					//out.println("<td>" + rs.getString("primary_attack") + "</td>");
					//out.println("<td>" + rs.getString("secondary_attack") + "</td>");
					out.println("</tr><tbody>");
				}
				rs.close();
				out.println("</table>");
				out.println("</body></html>");
			}

			else if (param3.length() > 0)
			{
				String sql = ""
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setstring(1,param1);
				ResultSet rs = pstmt.executeQuery();
				
				out.println("<!DOCTYPE HTML><html><body>");
				out.println("<img src=\"Pokemon.jpeg\" height=\"137\" width=\"368\" />");
				out.println("<table border = \"1\" width = \"100%\">");
				out.println("<col style = \"width:14%\"> <col style = \"width:14%\"> <col style = \"width:14%\">");
				//out.println("<thead><tr><th>Name</th><th>Primary Attack</th><th>Secondary Attack</th></tr></thead>");
				while (rs.next())
				{
					out.println("<tbody><tr>");
					//out.println("<td>" + rs.getString("name") + "</td>");
					//out.println("<td>" + rs.getString("primary_attack") + "</td>");
					//out.println("<td>" + rs.getString("secondary_attack") + "</td>");
					out.println("</tr><tbody>");
				}
				rs.close();
				out.println("</table>");
				out.println("</body></html>");
			}

			else 
			{
				out.println("<!DOCTYPE HTML><html><body>");
				out.println("<img src=\"Pokemon.jpeg\" height=\"137\" width=\"368\" />");
				out.println("<H1>NO CONTENT TO DISPLAY<H1>");
				out.println("</table>");
				out.println("</body></html>");
			}

		} catch (SQLException e) {
			// Handle errors
			e.printStackTrace();
		}  
	}
}
