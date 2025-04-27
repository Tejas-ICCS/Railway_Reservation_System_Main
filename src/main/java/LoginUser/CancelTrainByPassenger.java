package LoginUser;

import DatabaseConnection.DatabaseConnection;
import SendEmail.SendEmail;
import SendOTP.GenerateTransactionID;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cancelTrainByPassenger")
public class CancelTrainByPassenger extends HttpServlet {

    int amount1 = 0;
    String trainName = null;
    String source = null;
    String destination = null;
    private boolean refundAmount(String pnr, int ticketAmount, String jDate, String jTime, int totalPassengers, int trainNo, int seats) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        Connection con = null;
        PreparedStatement psRefund = null;
        PreparedStatement psCheck = null;
        ResultSet rs = null;

        try {
            con = DatabaseConnection.getInstance().getConnection();

            // Step 1: Check if ticket already refunded
            String checkQuery = "SELECT is_refunded FROM train_passenger WHERE id = ?";
            psCheck = con.prepareStatement(checkQuery);
            psCheck.setString(1, pnr);
            rs = psCheck.executeQuery();

            if (rs.next()) {
                boolean isRefunded = rs.getBoolean("is_refunded");
                if (isRefunded) {
                    System.out.println("Ticket already refunded for PNR: " + pnr);
                    return false;
                }
            } else {
                System.out.println("No ticket found for PNR: " + pnr);
                return false;
            }

            // Step 2: Check journey date and time
            LocalDate journeyDate = LocalDate.parse(jDate); // format yyyy-MM-dd
            LocalTime journeyTime = LocalTime.parse(jTime); // format HH:mm:ss

            // Combine Date and Time into a single LocalDateTime
            LocalDateTime journeyDateTime = LocalDateTime.of(journeyDate, journeyTime);
            LocalDateTime currentDateTime = LocalDateTime.now();

            Duration duration = Duration.between(currentDateTime, journeyDateTime);
            long hoursBeforeJourney = duration.toHours();

            int refundPercentage;

            if (hoursBeforeJourney > 24) {
                refundPercentage = 100;
            } else if (hoursBeforeJourney > 12) {
                refundPercentage = 50;
            } else if (hoursBeforeJourney > 0) {
                refundPercentage = 25;
            } else {
                System.out.println("Journey already started or completed. No refund allowed.");
                return false;
            }

            int refundAmount = (ticketAmount * refundPercentage) / 100;
            this.amount1 = refundAmount;
            // Step 3: Update refund info in database
            String refundQuery = "UPDATE train_passenger SET is_refunded = ?, refunded_amount = ?, status = ? WHERE id = ?";
            psRefund = con.prepareStatement(refundQuery);
            psRefund.setBoolean(1, true);
            psRefund.setInt(2, refundAmount);
            psRefund.setString(3, "Tickit Canceled By Itself");
            psRefund.setString(4, pnr);

            int rowsUpdated = psRefund.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Refund successful. Refund amount: " + refundAmount);

                // update the train Seats
                String trainSeatUpdate = "UPDATE Train SET total_seats = ? WHERE train_no = ?";
                PreparedStatement trainUpdate = con.prepareStatement(trainSeatUpdate);
                int total = seats + totalPassengers;
                trainUpdate.setInt(1, total);
                trainUpdate.setInt(2, trainNo);
                int x = trainUpdate.executeUpdate();
                if(x > 0) {
                    System.out.println("Train Seats successfully updated. Seat no: " + seats);
                }
                else {
                    System.out.println("Train Seats not updated. Seat no: " + seats);
                }

                return true;
            } else {
                System.out.println("Refund failed.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pnrNo = request.getParameter("pnrNo");
        String trainNo = request.getParameter("trainNo");
        String reason = request.getParameter("reason");

        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        int totalPassengers = 0;
        int seats = 0;

        String passengerName = "";
        String email = "";
        String from = "railway.reservationproject12@gmail.com";
        boolean overallSuccess = true; // Flag to track overall refund

        try {
            con = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM train_passenger WHERE id = ?";

            String total = "SELECT COUNT(*) AS totalPassengers FROM train_passenger WHERE id = ?;";
            Connection conn = DatabaseConnection.getInstance().getConnection();
            ps2 = conn.prepareStatement(total);
            ps2.setString(1, pnrNo);
            rs2 = ps2.executeQuery();

            if (rs2.next()) {
                totalPassengers = rs2.getInt("totalPassengers");
            }

            String totalSeats = "select total_seats, train_Name, train_source, train_destination from Train where train_no = ?";
            ps3 = con.prepareStatement(totalSeats);
            ps3.setInt(1, Integer.parseInt(trainNo));
            rs3 = ps3.executeQuery();
            if (rs3.next()) {
                seats = rs3.getInt("total_seats");
                this.trainName = rs3.getString("train_Name");
                this.source = rs3.getString("train_source");
                this.destination = rs3.getString("train_destination");
            }
            Connection conn2 = DatabaseConnection.getInstance().getConnection();
            ps = conn2.prepareStatement(query);
            ps.setString(1, pnrNo);
            rs = ps.executeQuery();

            boolean pnrExists = false;

            while (rs.next()) {
                pnrExists = true;
                String pnr = rs.getString("id");
                String status = rs.getString("status");
                int amount = rs.getInt("total_amount"); // Total ticket amount for this passenger
                String jDate = rs.getString("journy_date");
                String jTime = rs.getString("journy_time");
                 email = rs.getString("emailID");
                 passengerName = rs.getString("passenger_name");
//                int trainNo = rs.getInt("train_no");

                if ("Booked".equalsIgnoreCase(status)) {
                    boolean refundSuccess = refundAmount(pnr, amount, jDate, jTime, totalPassengers, Integer.parseInt(trainNo), seats);
                    if (!refundSuccess) {
                        overallSuccess = false; // Mark failure if any refund fails
                    }
                } else {
                    System.out.println("Ticket already cancelled or invalid for PNR: " + pnr);
                }
            }

            if (!pnrExists) {
                request.setAttribute("message", "Invalid PNR number. No records found.");
                session.setAttribute("overallSuccess", "No records found For Given PNR Number.");
            } else if (overallSuccess) {
                request.setAttribute("message", "All tickets under PNR " + pnrNo + " cancelled and refunded successfully!");
                session.setAttribute("overallSuccess","All tickets under PNR " + pnrNo + " refunded successfully!");

                try{
                    SendEmail se = new SendEmail();
                    // Send Email for Your Tickets Was cancelled

                    String htmlContent = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                            + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                            + "<h2 style='color: #2c3e50;'>Ticket Cancellation Confirmation</h2>"
                            + "<p>Dear Passenger,</p>"
                            + "<p>Your cancellation request for PNR Number <strong>" + pnrNo + "</strong> has been successfully processed.</p>"
                            + "<p>Train Number: <strong>" + trainNo + "</strong></p>"
                            + "<p>Train Name: <strong>" + this.trainName + "</strong></p>"
                            + "<p>From Source : <strong>" + this.source + "</strong></p>"
                            + "<p>To Destination: <strong>" + this.destination + "</strong></p>"
                            + "<p>The total amount of <strong>" + this.amount1 + " RS/-</strong> has been refunded to your account. Please allow up to 24 hours for the refund to reflect.</p>"
                            + "<p>We hope to serve you again in the future and make your journeys smooth and comfortable.</p>"
                            + "<br><p>If you have any queries or need further assistance, please feel free to contact our support team.</p>"
                            + "<br><p>Thank you for using our services!<br><strong>Railway Reservation System Team</strong></p>"
                            + "</div></body></html>";

                    se.sendEmail("Tickets Cancelled",email,from,htmlContent);

                    // Send Email for Amount Was Refund.
                    GenerateTransactionID gt = new GenerateTransactionID();
                    String htmlContent1 = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                            + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                            + "<h2 style='color: #2c3e50;'>Refund Credited to Your Account</h2>"
                            + "<p>Dear <strong>" + passengerName + "</strong>,</p>"
                            + "<p>We are pleased to inform you that an amount of <strong>" + this.amount1 + " RS/-</strong> has been successfully credited to your account.</p>"
                            + "<p>Transaction Reference Number: <strong>" + gt.getTransactionID() + "</strong></p>"
                            + "<p>The amount will reflect in your account within 24 hours. Please retain this reference number for future reference.</p>"
                            + "<p>If you have any questions, feel free to contact our support team.</p>"
                            + "<br><p>Thank you for choosing our services,<br><strong>Railway Reservation System</strong></p>"
                            + "</div></body></html>";
                    se.sendEmail("Amount Refund",email,from,htmlContent1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                request.setAttribute("message", "Some tickets could not be cancelled. Please check details.");
                session.setAttribute("overallSuccess", "Some tickets could not be cancelled. Please check details.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred. Please try again.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("cancelTrainByPassenger.jsp");
        dispatcher.forward(request, response);


    }
}
