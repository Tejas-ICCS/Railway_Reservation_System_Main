package LoginUser;

import DatabaseConnection.DatabaseConnection;
import SendEmail.SendEmail;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        String confirmPass = request.getParameter("confirmPass");
        String userType = request.getParameter("user");

        int x=-1;
        HttpSession session = request.getSession();
        DatabaseConnection db = DatabaseConnection.getInstance();
        Connection con = db.getConnection();

        SendEmail se =new SendEmail();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String fname = "";
        String lname = "";
        String adminName = "";
        String adminEmail = "";

        String from = String.valueOf(session.getAttribute("from"));

        try{
            if(userType.equals("admin")){
                String adminSelect = "select * from admin where username=? and password=?";
                ps = con.prepareStatement(adminSelect);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if(rs.next()){
                    adminName = rs.getString("name");
                    adminEmail = rs.getString("admin_mail_id");
                    String usernameAdmin = rs.getString("username");
                    String adminPasswordChange = "UPDATE admin SET password = ? WHERE username = ? AND password = ?";
                   if(newPass.equals(confirmPass)){
                       ps = con.prepareStatement(adminPasswordChange);
                       ps.setString(1, newPass);
                       ps.setString(2, usernameAdmin);
                       ps.setString(3, password);
                       x = ps.executeUpdate();
                       session.setAttribute("AdminPasswordChange", "Password changed successfully!");

                       String htmlContent = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                               + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                               + "<h2 style='color: #2c3e50;'>Admin Password Changed Successfully</h2>"
                               + "<p>Hi <strong>"+adminName+"</strong>,</p>"
                               + "<p>We wanted to inform you that the password for your admin account in the Railway Reservation System has been successfully changed.</p>"
                               + "<p><strong>New Password:</strong> " + newPass + "</p>"
                               + "<p>For security reasons, we highly recommend changing your password periodically and using a strong, unique one to prevent unauthorized access.</p>"
                               + "<p>If you did not request this change, please contact our support team immediately.</p>"
                               + "<br><p>Thank you,<br><strong>Railway Reservation System Team</strong></p>"
                               + "</div></body></html>";

                       se.sendEmail("Admin Password changed",adminEmail,from,htmlContent);
                   }

                }
            }
            else
            {
                String query = "SELECT * FROM new_user WHERE emailID = ? AND password = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if(rs.next()){
                    lname = rs.getString("firstName");
                    fname = rs.getString("lastName");
                    String query1 = "UPDATE new_user SET password = ? WHERE emailID = ? AND password = ?";
                    if (newPass.equals(confirmPass)) {
                        ps = con.prepareStatement(query1);
                        ps.setString(1, newPass);
                        ps.setString(2, username);
                        ps.setString(3, password);
                        x = ps.executeUpdate();
                        session.setAttribute("PasswordChange", "Password changed successfully!");

                        String htmlContent = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                                + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                                + "<h2 style='color: #2c3e50;'>Password Changed Successfully</h2>"
                                + "<p>Hi <strong>" + fname + " " + lname + "</strong>,</p>"
                                + "<p>We're letting you know that your password has been successfully changed for your Railway Reservation System account.</p>"
                                + "<p><strong>New Password:</strong> " + newPass + "</p>"
                                + "<p>For your account's security, we recommend changing this password again after logging in, and making sure it’s a strong, unique one.</p>"
                                + "<p>If you did not make this change, please contact support immediately.</p>"
                                + "<br><p>Thank you,<br><strong>Railway Reservation System Team</strong></p>"
                                + "</div></body></html>";
//                        String from = (String) session.getAttribute("from");
                        se.sendEmail("Password Changed Successfully", username,from, htmlContent);

                    } else {
                        session.setAttribute("PasswordChange", "New password and Confirm password do not match!");
                        response.sendRedirect("/Railway_Reservation_System/ChangePassword.jsp");
                    }

                    if(x>0){
                        response.sendRedirect("/Railway_Reservation_System/index.jsp");
                    }
                    else {

                    }
                }
                else {
                    session.setAttribute("PasswordChange", "Invalid Username or Password");
                    response.sendRedirect("/Railway_Reservation_System/ChangePassword.jsp");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
