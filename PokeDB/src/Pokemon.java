
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

		// SQL statements
		String sql = "SELECT number, name, type, hp, attack, defense from pokemon where name = ?";

		response.setContentType("text/html"); // Set response content type
		PrintWriter out = response.getWriter();

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
			
			// get input data from form
			String name = request.getParameter("name");
			
			// get data from form and convert to integer values

			// prepare sql select
			PreparedStatement pstmt =  conn.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			out.println("<!DOCTYPE HTML><html><body>");
			out.println("<img src=\"Pokemon.jpeg\" height=\"137\" width=\"368\" />");
			out.println("<table border = \"1\" width = \"100%\">");
			out.println("<col style = \"width:16%\"> <col style = \"width:16%\"> <col style = \"width:16%\">"
					+ "<col style = \"width:16%\"> <col style = \"width:16%\"> <col style = \"width:16%\">");
			out.println("<thead><tr><th>Number</th><th>Name</th> <th>Type</th> <th>HP</th> <th>Attack</th> <th>Defense</th></tr></thead>");
			while (rs.next()) {
				out.println("<tbody><tr>");
				out.println("<td>" + rs.getInt("number") + "</td>");
				out.println("<td>" + rs.getString("name") + "</td>");
				out.println("<td>" + rs.getString("type") + "</td>");
				out.println("<td>" + rs.getInt("hp") + "</td>");
				out.println("<td>" + rs.getInt("attack") + "</td>");
				out.println("<td>" + rs.getInt("defense") + "</td>");
				out.println("</tr><tbody>");
			}
			rs.close();
			out.println("</table>");
			out.println("</body></html>");
		} catch (SQLException e) {
			// Handle errors
			e.printStackTrace();
		}  
	}
}
