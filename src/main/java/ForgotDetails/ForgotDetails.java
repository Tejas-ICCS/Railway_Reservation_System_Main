package ForgotDetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeBodyPart;
//import jakarta.mail.internet.MimeMultipart;


import DatabaseConnection.DatabaseConnection;
import SendEmail.SendEmail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		DatabaseConnection database = new DatabaseConnection();
		//Connection con = database.getDataBaseConnection();
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
				
				ps.setString(1, email);
				ps.setString(2, DOB);
				
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					String fname = rs.getString("firstName");
					String lname = rs.getString("lastName");
					ForgotDetails.emailId = rs.getString("emailID");
					String dateOfBirth = rs.getString("Date_Of_Birth");
					
					String subject = "Change Password ";
					String from = "railway.reservationproject12@gmail.com";

					int otp = ThreadLocalRandom.current().nextInt(100000,1000000);
					setOtp(otp);

					String htmlContent = "<html><body><h3>Hi "+ fname +" "+ lname +",</h3><p>There was a request to change your password!</p>" +
							"<p> Your One Time Password for Change You Password is <h3>"+ otp
							+"</h3></p>" + "<p>Thank You </p></body></html>";

					String textContent = "There was a request to change your password!\n" +
							"Your One Time Password for Change You Password is :"+ otp +"\n" +
							"\nThank You \nRailway System";
					

					//Send OTP on Registered Email ID
					
					try {

						SendEmail sendEmail = new SendEmail();
						sendEmail.sendEmail(subject, emailId, from, ForgotDetails.getOtp(), fname, lname,htmlContent,textContent);
  						response.sendRedirect("/Railway_Reservation_System/submitOtp.jsp");

					
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
