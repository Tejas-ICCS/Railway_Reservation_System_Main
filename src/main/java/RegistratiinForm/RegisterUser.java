package RegistratiinForm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import DatabaseConnection.DatabaseConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/registerUser")
public class RegisterUser extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();
    
		String fname = request.getParameter("firstName");
		fname = fname.trim();
		String lname = request.getParameter("lastName");
		lname = lname.trim();
		String email = request.getParameter("email");
		email = email.trim();
		String mobile = request.getParameter("mobile");
		String gender = request.getParameter("gender");
		String dateOfBirth = request.getParameter("dateOfBirth");
		String password = request.getParameter("pass");

		boolean exists = false;

		HttpSession session = request.getSession();

		try {

			DatabaseConnection db = DatabaseConnection.getInstance();
			Connection con = db.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;

			if (con == null) {
				System.out.print("Connection Failed....");
			}
			else {
				System.out.print("Connection Created....");
				String query = "SELECT COUNT(*) FROM new_user WHERE emailID = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, email);
				rs = ps.executeQuery();
				if (rs.next() && rs.getInt(1) > 0) {
					exists = true;
				}

				if (exists){
					session.setAttribute("Error", "Email Already Exists");
					response.sendRedirect("register.jsp");
				}
				else {
					ps = con.prepareStatement("insert into new_user values(?,?,?,?,?,?,?,?)");
					ps.setString(1, fname);
					ps.setString(2, lname);
					ps.setString(3, email);
					ps.setString(4, mobile);
					ps.setString(5, password);
					ps.setString(6, gender);
					ps.setString(7, dateOfBirth);
					ps.setString(8,"demo_admin");

					int result = ps.executeUpdate();

					if (result > 0) {

						System.out.println("User Registered Successfully");
						session.setAttribute("Success", "User Registered Successfully");
						response.sendRedirect("/Railway_Reservation_System/index.jsp");

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
