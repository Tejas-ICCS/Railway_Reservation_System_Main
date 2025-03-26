package RegistratiinForm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DatabaseConnection.DatabaseConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerUser")
public class RegisterUser extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();
    
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String gender = request.getParameter("gender");
		String dateOfBirth = request.getParameter("dateOfBirth");
		String password = request.getParameter("pass");

		/*
		 * DatabaseConnection database = new DatabaseConnection(); Connection con =
		 * database.getDataBaseConnection();
		 */

		final String url = "jdbc:mysql://localhost:3306/railway_reservation_system";
		final String pass = "Tejas172304@";
		final String username = "root";

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			final Connection con = DriverManager.getConnection(url, username, pass);

			if (con == null) {
				System.out.print("Connection Failed....");
			} else {
				System.out.print("Connection Created....");
				PreparedStatement ps = con.prepareStatement("insert into new_user values(?,?,?,?,?,?,?)");
				ps.setString(1, fname);
				ps.setString(2, lname);
				ps.setString(3, email);
				ps.setString(4, mobile);
				ps.setString(5, password);
				ps.setString(6, gender);
				ps.setString(7, dateOfBirth);

				int result = ps.executeUpdate();

				if (result > 0) {

					System.out.println("User Registered Successfully");
					response.sendRedirect("/Railway_Reservation_System/index.jsp");

				} else {
					/*response.setContentType("text/html");
					out.print("<h3 style='color:Red'> User Register Failed  </h3>");*/

					System.out.printf("User %s already exists.\n",fname);
					response.sendRedirect("/railway_reservation_System/index.jsp");
//					RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
//					rd.include(request, response);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
