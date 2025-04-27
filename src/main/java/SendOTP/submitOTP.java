package SendOTP;

import DatabaseConnection.DatabaseConnection;
import ForgotDetails.ForgotDetails;
import Payment.Passenger;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet("/verifyOtp")
public class submitOTP extends HttpServlet {
    private int otp;
    private String password ;


    private static final int TOTAL_SEATS = 300;
    private static final int TOTAL_COACHES = 12;
    private static final int SEATS_PER_COACH = TOTAL_SEATS / TOTAL_COACHES;

    private boolean isTrainRunningOnSelectedDay(int trainNo, String journeyDate) {
        // Get the day of the week for the selected journey date
        LocalDate date = LocalDate.parse(journeyDate);
        String dayOfWeek = date.getDayOfWeek().toString(); // "MONDAY", "TUESDAY", "WEDNESDAY", ...

        // Convert to abbreviated form ("MON", "TUE", "WED", etc.)
        String formattedDay = dayOfWeek.substring(0, 3).toUpperCase(); // "MON", "TUE", "WED", ...

        boolean isTrainRunning = false;

        // Fetch train frequency from the database
        String frequencyQuery = "SELECT frequency FROM train WHERE train_no = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(frequencyQuery)) {
            pstmt.setInt(1, trainNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String frequency = rs.getString("frequency");

                    // Split the camel case string into individual days
                    String[] days = splitCamelCase(frequency);

                    // Check if the selected day is in the frequency list
                    for (String day : days) {
                        if (day.equalsIgnoreCase(formattedDay)) {
                            isTrainRunning = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isTrainRunning;
    }

    private String[] splitCamelCase(String camelCaseString) {
        // Split the camel case string into individual day abbreviations
        return camelCaseString.split("(?=[A-Z])");
    }




    private String generatePNR() {
        Random random = new Random();
        int randomDigits = 100000 + random.nextInt(900000);
        return "PNR" + randomDigits;
    }

    private int getLastAllocatedSeatNumber(int trainNo, String journeyDate) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM train_passenger WHERE train_no = ? AND is_cancelled = false AND booking_date = ?";

        try (
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(1, trainNo);
            psmt.setString(2, journeyDate);

            try (ResultSet rs2 = psmt.executeQuery()) {
                if (rs2.next()) {
                    count = rs2.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }


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
//        Connection connection1 = databaseConnection.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
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
                String htmlContent = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                        + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                        + "<h2 style='color: #2c3e50;'>Password Reset Successful</h2>"
                        + "<p>Hi <strong>" + fname + " " + lname + "</strong>,</p>"
                        + "<p>Your password has been successfully reset.</p>"
                        + "<p>Your new password is:</p>"
                        + "<div style='background-color: #f1f1f1; padding: 15px; border-radius: 5px; text-align: center;'>"
                        + "<h2 style='color: #3498db;'>" + password + "</h2>"
                        + "</div>"
                        + "<p>Please log in using this new password and consider changing it after login for better security.</p>"
                        + "<br><p>Thank you,<br><strong>Railway Reservation System Team</strong></p>"
                        + "</div></body></html>";


                try {
                    String updatePassword = "UPDATE new_user SET password = ? WHERE emailID = ?";
                    ps = connection.prepareStatement(updatePassword);
                    ps.setString(1, password);
                    ps.setString(2, to); // `to` is the email ID
                    int x = ps.executeUpdate();
                    if(x>0)
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

                String htmlContent = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                        + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                        + "<h2 style='color: #2c3e50;'>Payment Successful</h2>"
                        + "<p>Hi <strong>" + cardHolderName + "</strong>,</p>"
                        + "<p>Your payment has been successfully processed.</p>"
                        + "<p><strong>Card Number:</strong> XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4) + "</p>"
                        + "<p><strong>Payment Amount:</strong>" + amount + " RS/-</p>"
                        + "<p><strong>Transaction ID:</strong> " + transactionID + "</p>"
                        + "<p>Thank you for your payment. We appreciate your business!</p>"
                        + "<p>If you have any questions or issues, please contact our support team.</p>"
                        + "<br><p>Thank you,<br><strong>Railway Reservation System Team</strong></p>"
                        + "</div></body></html>";

                try{
                    String source = (String) session.getAttribute("source");
                    String destination = (String) session.getAttribute("destination");

                    SendEmail sendEmail = new SendEmail();
                    sendEmail.sendEmail("Payment Successful", to, from,htmlContent);

                    session.getAttribute("passengerList");
                    int trainNo = (int) session.getAttribute("trainNo");
                    session.getAttribute("fixedAmount");
                    String emailID = (String) session.getAttribute("passEmailId");
                    String journeyDate = (String) session.getAttribute("journeyDate");
                    String journeyTime = (String) session.getAttribute("journeyTime");
                    int totalPassengers = (int) session.getAttribute("totalPassengers");
                    int lastSeatNumber = getLastAllocatedSeatNumber(trainNo, journeyDate);
                    String bookingDate = LocalDate.now().toString();
                    String bookingTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    String pnrNumber = generatePNR();

                    try{
                        String insertPassenger = "INSERT INTO train_passenger " +
                                "(id, emailID, train_no, total_amount, booking_date, booking_time, is_cancelled, is_refunded, " +
                                "status, seat_number, passenger_name, passenger_age, passenger_gender,journy_date,journy_time,source,destination) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        Connection connection1 = DatabaseConnection.getInstance().getConnection();
                        ps1 = connection1.prepareStatement(insertPassenger);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    List<Passenger> passengerList = (List<Passenger>) session.getAttribute("passengerList");
                    boolean allPassengersInserted = true;
                    for (int i = 1; i <= totalPassengers; i++) {
                        String name = passengerList.get(i - 1).getName();
                        int age = passengerList.get(i - 1).getAge();
                        String gender = passengerList.get(i - 1).getGender();

                        int seatIndex = lastSeatNumber + i;
                        String coach = String.valueOf((char) ('A' + (seatIndex - 1) / SEATS_PER_COACH)); // A, B, C, D... L
                        int seatInCoach = ((seatIndex - 1) % SEATS_PER_COACH) + 1;
                        String seatNumber = coach + seatInCoach;

                        try {
                            ps1.setString(1, pnrNumber);
                            ps1.setString(2, emailID);
                            ps1.setInt(3, trainNo);
                            ps1.setInt(4, Integer.parseInt(amount));
                            ps1.setString(5, bookingDate.toString());
                            ps1.setString(6, bookingTime.format(String.valueOf(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                            ps1.setBoolean(7, false); // is_cancelled
                            ps1.setBoolean(8, false); // is_refunded
                            ps1.setString(9, "Booked"); // status
                            ps1.setString(10, seatNumber);
                            ps1.setString(11, name);
                            ps1.setInt(12, age);
                            ps1.setString(13, gender);
                            ps1.setString(14, journeyDate);
                            ps1.setString(15, journeyTime);
                            ps1.setString(16, source);
                            ps1.setString(17, destination);

                            int x = ps1.executeUpdate();
                            if (x > 0) {
                                System.out.printf("Passenger %s has been successfully added to the database.\n", name);
                            } else {
                                System.out.printf("Passenger %s could not be added to the database.\n", name);
                                allPassengersInserted = false;  // Set flag to false if any passenger fails to insert
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            allPassengersInserted = false;
                        }
                    }

                    if (allPassengersInserted) {
                        // Ticket booking success, set session attribute and redirect to booking page
                        session.setAttribute("TicketBooked", "Ticket Booked Successfully");
                        response.sendRedirect("/Railway_Reservation_System/Booking.jsp");

                        // Create HTML table for email content
                        StringBuilder htmlTable = new StringBuilder();
                        htmlTable.append("<h2 style='text-align:center; color: #4CAF50;'>Passenger Booking Details</h2>");
                        htmlTable.append("<div style='font-size: 16px; margin-bottom: 10px;'>");
                        htmlTable.append("<p><strong>PNR Number:</strong> ").append(pnrNumber).append("</p>");
                        htmlTable.append("<p><strong>Train Number:</strong> ").append(trainNo).append("</p>");
                        htmlTable.append("<p><strong>Source:</strong> ").append(source).append("</p>");
                        htmlTable.append("<p><strong>Destination:</strong> ").append(destination).append("</p>");
                        htmlTable.append("<p><strong>Journey Date:</strong> ").append(journeyDate).append("</p>");
                        htmlTable.append("<p><strong>Journey Time:</strong> ").append(journeyTime).append("</p>");
                        htmlTable.append("<p><strong>Total Amount:</strong> ").append(amount).append(" RS/</p>");
                        htmlTable.append("<p><strong>Booking Date:</strong> ").append(bookingDate).append("</p>");
                        htmlTable.append("<p><strong>Booking Time:</strong> ").append(bookingTime).append("</p>");
                        htmlTable.append("</div>");

                        // Create the table for passenger details
                        htmlTable.append("<table border='1' cellpadding='10' cellspacing='0' style='border-collapse:collapse; width: 100%; margin-top: 20px; text-align: left; background-color: #f9f9f9;'>");
                        htmlTable.append("<thead style='background-color: #4CAF50; color: white;'>");
                        htmlTable.append("<tr>");
                        htmlTable.append("<th style='padding: 10px;'>Passenger Name</th>");
                        htmlTable.append("<th style='padding: 10px;'>Age</th>");
                        htmlTable.append("<th style='padding: 10px;'>Gender</th>");
                        htmlTable.append("<th style='padding: 10px;'>Seat Number</th>");
                        htmlTable.append("<th style='padding: 10px;'>Status</th>"); // Added status column
                        htmlTable.append("</tr>");
                        htmlTable.append("</thead>");
                        htmlTable.append("<tbody>");

                        // Loop through the passengers and add their details to the table
                        for (int i = 1; i <= totalPassengers; i++) {
                            String name1 = passengerList.get(i - 1).getName();
                            int age1 = passengerList.get(i - 1).getAge();
                            String gender1 = passengerList.get(i - 1).getGender();

                            // Assuming you have logic to get the seat number, like lastSeatNumber
                            int seatNumber1 = lastSeatNumber + i;  // You may need to adjust this based on your logic

                            // Example status (this should be dynamic based on your logic, e.g., "Booked", "Cancelled")
                            String status1 = "Booked";  // Modify based on the actual status logic (e.g., check from DB)

                            htmlTable.append("<tr>");
                            htmlTable.append("<td style='padding: 8px; text-align:center;'>").append(name1).append("</td>");
                            htmlTable.append("<td style='padding: 8px; text-align:center;'>").append(age1).append("</td>");
                            htmlTable.append("<td style='padding: 8px; text-align:center;'>").append(gender1).append("</td>");
                            htmlTable.append("<td style='padding: 8px; text-align:center;'>").append(seatNumber1).append("</td>");
                            htmlTable.append("<td style='padding: 8px; text-align:center;'>").append(status1).append("</td>"); // Status cell
                            htmlTable.append("</tr>");
                        }

                        htmlTable.append("</tbody>");
                        htmlTable.append("</table>");

                        // Send the email with the generated HTML content
                        String htmlContent1 = htmlTable.toString();
                        sendEmail.sendEmail("Ticket Booked", emailID, from, htmlContent1);

                        System.out.println("Booking details email sent.");

                        // update the total seats from the train table for the given train number logic

                        String updateTrain = "update Train set total_seats = ? where train_no = ?";
                        int ts = (int) session.getAttribute("totalSeats");
                        int totalSeats = ts - totalPassengers;
                        connection = DatabaseConnection.getInstance().getConnection();
                        ps = connection.prepareStatement(updateTrain);
                        ps.setInt(1, totalSeats);
                        ps.setInt(2, trainNo);
                        int x = ps.executeUpdate();
                        if (x > 0) {
                            System.out.println("train updated");

                        }
                        else {
                            System.out.println("train not updated");
                        }

                    } else {
                        // If any passenger failed to insert, inform the user
                        System.out.println("One or more passengers could not be added to the database. Please try again.");
                        // You can redirect the user to a failure page or show an error message
                        response.sendRedirect("/Railway_Reservation_System/Booking.jsp");
                    }



                    // Database logc to store the payment details into a table

                    LocalDate localDate = LocalDate.now();
                    LocalTime localTime = LocalTime.now();
                    String loginedEmailId = (String) session.getAttribute("loginedEmailId");
                    String insertPayment = "insert into payment(payment_ID,emailID, payment_mode, transaction_date, transaction_time,transaction_amount)" +
                            " values(?,?,?,?,?,?)";
                    connection = DatabaseConnection.getInstance().getConnection();
                    ps = connection.prepareStatement(insertPayment);
                    ps.setString(1, transactionID);
                    ps.setString(2,loginedEmailId);
                    ps.setString(3, "online");
                    ps.setString(4, localDate.toString());
                    ps.setString(5, localTime.toString());
                    ps.setString(6, amount);

                    int x = ps.executeUpdate();
                    if(x>0){
                        System.out.printf("Successfully stored into the database");
                        session.removeAttribute("loginedEmailId");
                        session.removeAttribute("passengerList");
                        session.removeAttribute("fixedAmount");
                        session.removeAttribute("journeyDate");
                        session.removeAttribute("journeyTime");
                        session.removeAttribute("totalSeats");
                        session.removeAttribute("destination");
                        session.removeAttribute("source");
//                        session.removeAttribute("totalPassengers");

                    }
                    else{
                        System.out.println("Failed to store into the database");
                    }
//                    session.removeAttribute("users");
                    System.out.println("Payment Successful");
                    session.setAttribute("otpVerificationError","Payment Successful");
//                    response.sendRedirect("/Railway_Reservation_System/Booking.jsp");

                }catch(Exception e) {
                    System.out.println("Catch Exception");
                    e.printStackTrace();
                }
            }
        } else {
//            response.sendRedirect("submitOtp.jsp?mode=" + mode + "&error=invalid");
//            response.sendRedirect("/Railway_Reservation_System/login.jsp");
//            session.setAttribute("otpVerificationError","Enter Correct OTP");
            response.sendRedirect("/Railway_Reservation_System/submitOtp.jsp?otpVerificationError=wrongOtp");

        }
    }
}