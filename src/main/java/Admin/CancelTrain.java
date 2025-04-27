package Admin;

import DatabaseConnection.DatabaseConnection;
import SendEmail.SendEmail;
import SendOTP.GenerateTransactionID;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/CancelTrain")
public class CancelTrain extends HttpServlet {

    private boolean refundAmount(String pnr , int amount) {
        Connection con = DatabaseConnection.getInstance().getConnection();
        String refundQuery = "update train_passenger tp set is_refunded = ? , refunded_amount = ? where id = ?";
        try (PreparedStatement psRefund = con.prepareStatement(refundQuery)) {
            psRefund.setBoolean(1, true);
            psRefund.setInt(2, amount);
            psRefund.setString(3,pnr);
            psRefund.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        String trainNo = request.getParameter("trainNo");
        String reason = request.getParameter("reason");

        String trainName = "";
        String trainSource = "";
        String trainDestination = "";
        String Source = "";
        String trainFrequency = "";

        try{
            Connection con = DatabaseConnection.getInstance().getConnection();
            Statement stmt = null;
            PreparedStatement ps = null;
            HttpSession session = request.getSession();
            stmt = con.createStatement();
            ResultSet rs = null;
            //Remove passengers from the train and send there money return to them
            //logic

            String passenger = "select * from train_passenger where train_no = '"+trainNo+"'";
            Map<String, String> processedPNRs = new HashMap<>();
            rs = stmt.executeQuery(passenger);
            while (rs.next()) {
                String pnr = rs.getString("id"); // PNR number
                String emailID = rs.getString("emailID");
                String status = rs.getString("status"); // Ticket status
                int amount = rs.getInt("total_amount"); // Refund amount
                String passengerName = rs.getString("passenger_name");


                if (!processedPNRs.containsKey(pnr)) {
                    processedPNRs.put(pnr, "processed");

                    boolean refundSuccess = refundAmount(pnr, amount);
                    if (refundSuccess) {
                        // Update all passengers for this PNR to 'canceled'
                        String updateStatusQuery = "UPDATE train_passenger SET status = 'Train Canceled' WHERE id = ?";
                        try (PreparedStatement psUpdate = con.prepareStatement(updateStatusQuery)) {
                            psUpdate.setString(1, pnr);
                            psUpdate.executeUpdate();

                            SendEmail se = new SendEmail();

                            String htmlContent = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                                    + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                                    + "<h2 style='color: #2c3e50;'>Train Cancellation Notification</h2>"
                                    + "<p>PNR Number <strong>" + pnr + "</strong>,</p>"
                                    + "<p>We regret to inform you that your scheduled train, <strong>Train No. " + trainNo + "</strong>, has been canceled due to unforeseen circumstances.</p>"
                                    + "<p>We understand this may cause inconvenience, and we sincerely apologize for the disruption.</p>"
                                    + "<p>Your total booking amount of <strong>" + amount + " RS/-</strong> has been successfully refunded to your account. Please allow up to 24 hours for the amount to reflect in your account.</p>"
                                    + "<p>We appreciate your understanding and patience during this time.</p>"
                                    + "<br><p>If you have any further questions or need assistance, feel free to contact our support team.</p>"
                                    + "<br><p>Thank you,<br><strong>Railway Reservation System Team</strong></p>"
                                    + "</div></body></html>";

                            se.sendEmail("Train Canceled",emailID,"railway.reservationproject12@gmail.com",htmlContent);

                            GenerateTransactionID gt = new GenerateTransactionID();


                            String htmlContent1 = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                                    + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                                    + "<h2 style='color: #2c3e50;'>Refund Credited to Your Account</h2>"
                                    + "<p>Dear <strong>" + passengerName + "</strong>,</p>"
                                    + "<p>We are pleased to inform you that an amount of <strong>" + amount + " RS/-</strong> has been successfully credited to your account.</p>"
                                    + "<p>Transaction Reference Number: <strong>" + gt.getTransactionID() + "</strong></p>"
                                    + "<p>The amount will reflect in your account within 24 hours. Please retain this reference number for future reference.</p>"
                                    + "<p>If you have any questions, feel free to contact our support team.</p>"
                                    + "<br><p>Thank you for choosing our services,<br><strong>Railway Reservation System</strong></p>"
                                    + "</div></body></html>";

                            se.sendEmail("Amount Refunded in your account",emailID,"railway.reservationproject12@gmail.com",htmlContent1);

                        }catch (Exception e) {
                        e.printStackTrace();}
                    }
                }
            }

            //insert data into the train_cancellation table
            String selectTrain = "SELECT * FROM Train WHERE train_no = " + trainNo +" AND is_cancelled= 'false'";

            rs = stmt.executeQuery(selectTrain);
            if(rs.next()){
                String insertCancelTable = "INSERT INTO train_cancellations(train_no, reason, cancelled_at) VALUES (?, ?, CURRENT_TIMESTAMP)";
                ps = con.prepareStatement(insertCancelTable);
                ps.setString(1, trainNo);
                ps.setString(2, reason);
                int row = ps.executeUpdate();
                if (row > 0) {
                    System.out.println("Train cancelled by Admin");
                    session.setAttribute("cancelTrain", "Train cancelled by "+session.getAttribute("adminName"));
                    response.sendRedirect("/Railway_Reservation_System//adminPage.jsp");
                }
                else{
                    System.out.println("can't cancel train");
                    session.setAttribute("cancelTrain", "Something went wrong");
                    response.sendRedirect("/Railway_Reservation_System//adminPage.jsp");
                }

                String updateTrain = "UPDATE Train SET is_cancelled = TRUE WHERE train_no = ?";
                ps = con.prepareStatement(updateTrain);
                ps.setString(1, trainNo);
                row = ps.executeUpdate();
                if (row > 0) {
                    System.out.println("Updated train ");

                    response.sendRedirect("/Railway_Reservation_System/adminPage.jsp");
                }
                else{
                    System.out.println("can't Update train");
                    response.sendRedirect("/Railway_Reservation_System/adminPage.jsp");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
