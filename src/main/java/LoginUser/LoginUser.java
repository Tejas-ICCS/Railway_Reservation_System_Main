package LoginUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.management.Notification;

import DatabaseConnection.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/loginUserNew")
public class LoginUser extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		 DatabaseConnection databaseConnection = new DatabaseConnection();
		 Connection con = databaseConnection.getConnection();

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("select firstName,lastName from new_user where emailID = ? AND password = ? ");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			String fname;
			String lname;
			HttpSession session = request.getSession();
			if(rs.next()) {
				 fname = rs.getString("firstName");
				 lname = rs.getString("lastName");

				String name = fname + " " + lname;
				session.setAttribute("FirstName", name);
				response.sendRedirect("/Railway_Reservation_System/index.jsp");
			}
			else {
				session.setAttribute("error", "User Not Found");
				response.sendRedirect("login.jsp");
//				response.sendRedirect("login.jsp?error= User Not Found ");

			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
