package LoginUser;

import DatabaseConnection.DatabaseConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/CheckPNR")
public class CheckPNR extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String pnrNo = request.getParameter("pnrNumber");
        DatabaseConnection db = DatabaseConnection.getInstance();
        Connection con = db.getConnection();

        String query = "SELECT * FROM train_passenger WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        Map<String, String> ticketInfo = new HashMap<>();
        List<Map<String, String>> passengerList = new ArrayList<>();

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, pnrNo);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (ticketInfo.isEmpty()) {
                    ticketInfo.put("emailID", rs.getString("emailID"));
                    ticketInfo.put("train_no", rs.getString("train_no"));
                    ticketInfo.put("total_amount", rs.getString("total_amount"));
                    ticketInfo.put("booking_date", rs.getString("booking_date"));
                    ticketInfo.put("booking_time", rs.getString("booking_time"));
                    ticketInfo.put("status", rs.getString("status"));
                    ticketInfo.put("journy_date", rs.getString("journy_date"));
                    ticketInfo.put("journy_time", rs.getString("journy_time"));
                    ticketInfo.put("source", rs.getString("source"));
                    ticketInfo.put("destination", rs.getString("destination"));
                }

                Map<String, String> passengerData = new HashMap<>();
                passengerData.put("seat_number", rs.getString("seat_number"));
                passengerData.put("passenger_name", rs.getString("passenger_name"));
                passengerData.put("passenger_age", rs.getString("passenger_age"));
                passengerData.put("passenger_gender", rs.getString("passenger_gender"));
                passengerList.add(passengerData);
            }

            if (!ticketInfo.isEmpty()) {
                request.setAttribute("ticketInfo", ticketInfo);
                request.setAttribute("passengerList", passengerList);
                request.setAttribute("pnrNo", pnrNo);
            } else {
                request.setAttribute("pnrError", "Invalid PNR Number. Please try again.");
            }

            RequestDispatcher rd = request.getRequestDispatcher("checkPNR.jsp");
            rd.forward(request, response);

//            response.sendRedirect("/Railway_Reservation_System/checkPNR.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                request.setAttribute("pnrError", "Something went wrong while fetching PNR details.");
                RequestDispatcher rd = request.getRequestDispatcher("checkPNR.jsp");
                rd.forward(request, response);

//                response.sendRedirect("/Railway_Reservation_System/checkPNR.jsp");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
