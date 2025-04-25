package ForgotDetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeBodyPart;
//import jakarta.mail.internet.MimeMultipart;


import DatabaseConnection.DatabaseConnection;
import SendEmail.SendEmail;
import SendOTP.GenerateOtp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet("/forgotDetails")
public class ForgotDetails extends HttpServlet{
	private static int otp;
	private static String emailId;

	public String getEmailId() {
		return emailId;
	}

	public static int getOtp() {
		return otp;
	}

	public static void setOtp(int otp) {
		ForgotDetails.otp = otp;
	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		DatabaseConnection database = DatabaseConnection.getInstance();
		Connection con = database.getConnection();

		if(con == null ) {
			System.out.print("Connection Failed......");
		}
		else {
			try {
				String email = request.getParameter("emailID");
				String DOB = request.getParameter("DateOfBirth");
				
				PreparedStatement ps = con.prepareStatement("Select firstName,lastName,emailID,Date_Of_Birth "
						+ "from new_user where emailID = ? AND Date_Of_Birth = ?");
				HttpSession session = request.getSession();

				ps.setString(1, email);
				ps.setString(2, DOB);
				
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {

					String fname = rs.getString("firstName");
					String lname = rs.getString("lastName");
					ForgotDetails.emailId = rs.getString("emailID");
					String dateOfBirth = rs.getString("Date_Of_Birth");

					ArrayList<String > users = new ArrayList<>();
					users.add(fname);
					users.add(lname);
					users.add(ForgotDetails.emailId);

					session.setAttribute("users", users);
					String subject = "Change Password ";
					String from = "railway.reservationproject12@gmail.com";
					GenerateOtp go = new GenerateOtp();
					setOtp(go.getOpt());


					String htmlContent = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
							+ "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
							+ "<h2 style='color: #2c3e50;'>Password Reset Request</h2>"
							+ "<p>Hi <strong>" + fname + " " + lname + "</strong>,</p>"
							+ "<p>We received a request to reset your password.</p>"
							+ "<p>Your One-Time Password (OTP) to change your password is:</p>"
							+ "<div style='background-color: #f1f1f1; padding: 15px; border-radius: 5px; text-align: center;'>"
							+ "<h2 style='color: #e74c3c;'>" + ForgotDetails.otp + "</h2>"
							+ "</div>"
							+ "<p>If you didn’t request this, please ignore this message.</p>"
							+ "<br><p>Thank you,<br><strong>Railway Reservation System Team</strong></p>"
							+ "</div></body></html>";

					//Send OTP on Registered Email ID
					
					try {

						SendEmail sendEmail = new SendEmail();
						sendEmail.sendEmail(subject, emailId, from,htmlContent);
						response.sendRedirect("/Railway_Reservation_System/submitOtp.jsp?mode=forgot");

					
					}catch(Exception e) {
						System.out.println("Catch Exception");
						e.printStackTrace();
					}
					
				}
				else {
 					response.sendRedirect("/Railway_Reservation_System/login.jsp");
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} /*
				 * catch (MessagingException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
		}
	}
}
