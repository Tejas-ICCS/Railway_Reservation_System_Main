package SendOTP;

import DatabaseConnection.DatabaseConnection;
import ForgotDetails.ForgotDetails;
import SendEmail.SendEmail;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/verifyOtp")
public class submitOTP extends HttpServlet {
    private int otp;
    private String password ;

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+<>?";
    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try{
            String  otp = request.getParameter("otp");
            ForgotDetails forgotDetails = new ForgotDetails();
//             ForgotDetails.setOtp(forgotDetails.getOtp());
            int otpNo = forgotDetails.getOtp();
            String otp1 = String.valueOf(otpNo);
            if(otp.equals(otp1)){

                SecureRandom random = new SecureRandom();
                StringBuilder password = new StringBuilder(8);

                for (int i = 0; i < 8; i++) {
                    int index = random.nextInt(ALL_CHARS.length());
                    password.append(ALL_CHARS.charAt(index));
                }
                this.password = password.toString();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection con =  databaseConnection.getConnection();
                String emailId = forgotDetails.getEmailId();
                String from = "railway.reservationproject12@gmail.com";
                String fname="";
                String lname="";
                String subject = "Password Change";
                String selectFnameLname = "select firstName,lastName from new_user where emailID='"+emailId+"'";
                ResultSet rs = con.createStatement().executeQuery(selectFnameLname);
                if (rs.next()){
                     fname = rs.getString("firstName");
                     lname = rs.getString("lastName");
                }

                String htmlContent = "<h2>Hie "+fname+" "+lname+"\n</h2>\n<p> Your new password is: <h3>"+this.password+"</h3></p>\n\n" +
                        "Railway Reservation  System";

                SendEmail sendEmail = new SendEmail();
                sendEmail.sendEmail(subject,emailId,from,0,fname,lname,htmlContent,"");
                String updatePassword = "update new_user set password=? where emailID=?";
                PreparedStatement ps = con.prepareStatement(updatePassword);
                ps.setString(1,this.password);
                ps.setString(2,emailId);
                ps.executeUpdate();
                System.out.println("Password updated");
                response.sendRedirect("/Railway_Reservation_System/login.jsp");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
