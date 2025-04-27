package Payment;

import DatabaseConnection.DatabaseConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet("/passengerDetails")
public class PassengerDetails extends HttpServlet {

    private boolean isTrainRunningOnSelectedDay(int trainNo, String journeyDate) {
        // Get the day of the week for the selected journey date
        LocalDate date = LocalDate.parse(journeyDate);
        String dayOfWeek = date.getDayOfWeek().toString(); // "MONDAY", "TUESDAY", "WEDNESDAY", ...

        // Convert to abbreviated form ("MON", "TUE", "WED", etc.)
        String formattedDay = dayOfWeek.substring(0, 3).toUpperCase(); // "MON", "TUE", "WED", ...

        boolean isTrainRunning = false;

        // Fetch train frequency from the database
        String frequencyQuery = "SELECT train_frequency FROM Train WHERE train_no = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(frequencyQuery)) {
            pstmt.setInt(1, trainNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String frequency = rs.getString("train_frequency");

                    // Split the camel case string into individual days
                    String[] days = splitCamelCase(frequency);

                    // Check if the selected day is in the frequency list
                    for (String day : days) {
                        day=day.substring(0,3);
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

    private int getLastAllocatedSeatNumber(int trainNo) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        java.sql.ResultSet rs = null;

        try {
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            conn = databaseConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM train_passenger WHERE train_no = ? AND is_cancelled = false";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, trainNo);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int totalPassenger = Integer.parseInt(request.getParameter("totalPassenger"));
        String emailID = (String) session.getAttribute("loginedEmailId");
        int trainNo = Integer.parseInt(request.getParameter("trainNo"));
        int amount = Integer.parseInt(session.getAttribute("FixedAmount").toString());
        String journeyDate = (String) session.getAttribute("jDate");
        String journeyTime = (String) session.getAttribute("jTime");
        String bookingDate = LocalDate.now().toString();
        String bookingTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String pnrNumber = generatePNR();
//        String pnrNumber = "PNR" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();

        if(isTrainRunningOnSelectedDay(trainNo, journeyDate)) {
            List<Passenger> passengerList = new ArrayList<>();
            for (int i = 1; i <= totalPassenger; i++) {
                String name = request.getParameter("passengerName" + i);
                int age = Integer.parseInt(request.getParameter("passengerAge" + i));
                String gender = request.getParameter("gender" + i);
                passengerList.add(new Passenger(name, age, gender));
            }
            session.setAttribute("passengerList", passengerList);
            session.setAttribute("trainNo", trainNo);
            session.setAttribute("fixedAmount", (amount * totalPassenger));
            session.setAttribute("journeyDate", journeyDate);
            session.setAttribute("journeyTime", journeyTime);
            session.setAttribute("passEmailId", emailID);
            session.setAttribute("totalPassengers", totalPassenger);

            response.sendRedirect("/Railway_Reservation_System/payment.jsp");
        }
        else {
            session.setAttribute("selectedDate","Train Not for the selected date");
            response.sendRedirect("/Railway_Reservation_System/BookingPassenger.jsp");
        }
    }
}
