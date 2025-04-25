package SendOTP;

import DatabaseConnection.DatabaseConnection;
import ForgotDetails.ForgotDetails;
import SendEmail.SendEmail;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/verifyOtp")
public class submitOTP extends HttpServlet {
    private int otp;
    private String password ;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String otp = request.getParameter("otp");
        String mode = request.getParameter("mode");
        String otp2 = (String) session.getAttribute("otp");
        ForgotDetails forgotDetails = new ForgotDetails();
        int otp1 = ForgotDetails.getOtp();

        String from = "railway.reservationproject12@gmail.com";

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement ps = null;
//        ResultSet rs = null;
        Statement stmt = null;


        if (otp != null && otp.equals(String.valueOf(otp1)) || otp != null && otp.equals(String.valueOf(otp2))) {
            if ("forgot".equals(mode)) {
                GeneratePassword generatePassword = new GeneratePassword();
                String password = generatePassword.getPassword();

                ArrayList<String> user = (ArrayList) session.getAttribute("users");
                String fname = (String) user.get(0);
                String lname = (String) user.get(1);
                String to = (String) user.get(2);
                String htmlContent = "<html><body>"
                        + "<h3>Hi " + fname + " " + lname + ",</h3>"
                        + "<p>Your password has been successfully reset.</p>"
                        + "<p>Your new password is:</p>"
                        + "<h3>" + password + "</h3>"
                        + "<p>Please log in using this new password and consider changing it after login for better security.</p>"
                        + "<p>Thank you,<br>Railway Reservation System</p>"
                        + "</body></html>";

                try {
                    String updatePassword = "UPDATE new_user SET password = ? WHERE emailID = ?";
                    ps = connection.prepareStatement(updatePassword);
                    ps.setString(1, password);
                    ps.setString(2, to); // `to` is the email ID
                    int x = ps.executeUpdate();
                    if(x<0)
                        System.out.println("Successfully updated password");
                    else
                        System.out.println("Failed to update password");

                    SendEmail sendEmail = new SendEmail();
                    sendEmail.sendEmail("Change Password", to, from,htmlContent);
//                    session.removeAttribute("users");
                    session.setAttribute("otpVerificationError","Payment Successful");
                    response.sendRedirect("/Railway_Reservation_System/login.jsp");


                }catch(Exception e) {
                    System.out.println("Catch Exception");
                    e.printStackTrace();
                }

            } else if ("payment".equals(mode)) {
//                response.sendRedirect("paymentConfirmation.jsp");
                ArrayList<String > cardDetails = (ArrayList) session.getAttribute("cardDetails");
                String cardHolderName = (String) cardDetails.get(0);
                String cardNumber = (String) cardDetails.get(1);
                String amount = (String) cardDetails.get(2);
                String to  = (String) cardDetails.get(3);

                GenerateTransactionID generateTransactionID = new GenerateTransactionID();
                String transactionID = generateTransactionID.getTransactionID();

                String htmlContent = "<html><body>"
                        + "<h3>Hi " + cardHolderName + ",</h3>"
                        + "<p>Your payment has been successfully processed.</p>"
                        + "<p><strong>Card Number:</strong> XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4) + "</p>"
                        + "<p><strong>Payment Amount:</strong> ₹" + amount + "</p>"
                        + "<p><strong>Transaction ID:</strong> " + transactionID + "</p>"
                        + "<p>Thank you for your payment. We appreciate your business!</p>"
                        + "<p>If you have any questions or issues, please contact our support team.</p>"
                        + "<p>Thank you,<br>Railway Reservation System</p>"
                        + "</body></html>";

                try{

                    SendEmail sendEmail = new SendEmail();
                    sendEmail.sendEmail("Payment Successful", to, from,htmlContent);
//                    session.removeAttribute("users");
                    System.out.println("Payment Successful");
                    session.setAttribute("otpVerificationError","Payment Successful");
                    response.sendRedirect("/Railway_Reservation_System/Booking.jsp");

                }catch(Exception e) {
                    System.out.println("Catch Exception");
                    e.printStackTrace();
                }
            }
        } else {
//            response.sendRedirect("submitOtp.jsp?mode=" + mode + "&error=invalid");
            response.sendRedirect("/Railway_Reservation_System/login.jsp");
            session.setAttribute("otpVerificationError","Enter Correct OTP");
        }
    }
}