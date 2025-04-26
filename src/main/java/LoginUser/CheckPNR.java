package LoginUser;

import DatabaseConnection.DatabaseConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/CheckPNR")
public class CheckPNR extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response){
        String pnrNo = request.getParameter("pnrNo");
        HttpSession session = request.getSession();
        DatabaseConnection db = DatabaseConnection.getInstance();
        Connection con = db.getConnection();

        try{
//            id = pnrNo
            String query = "select * from train_passenger where id = ?";
            PreparedStatement ps = null;
            ResultSet rs1 = null;
            ResultSet rs2 = null;
            ps = con.prepareStatement(query);
            ps.setString(1, pnrNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String email = rs.getString("emailID");
                String trainNo = rs.getString("train_no");
                String BookingDate = rs.getString("booking_date");
                String BookingTime = rs.getString("booking_time");
                String is_cancelled = rs.getString("is_cancelled");
                String status = rs.getString("status");
                String seatNumber = rs.getString("seat_number");


                String query1 = "select * from payment where email_ID = ?";
                ps = con.prepareStatement(query1);
                ps.setString(1, email);
                rs1 = ps.executeQuery();
                if(rs1.next()){
                    String paymentMode = rs.getString("payment_mode");
                    String transactionDate = rs.getString("transaction_date");
                    String transactionTime = rs.getString("transaction_time");
                    String transactionAmount = rs.getString("transaction_amount");

                    String query3 = "select * from Train where train_no = ?";
                    ps = con.prepareStatement(query3);
                    ps.setString(1, trainNo);
                    rs2 = ps.executeQuery();
                    if(rs2.next()){
                        String trainName = rs.getString("train_name");
                        String source = rs.getString("train_source");
                        String destination = rs.getString("train_destination");
                        String trainTime = rs.getString("train_time");

                        ArrayList<String> pnrDetails = new ArrayList<>();
                        pnrDetails.add(pnrNo);
                        pnrDetails.add(trainNo);
                        pnrDetails.add(trainName);
                        pnrDetails.add(source);
                        pnrDetails.add(destination);
                        pnrDetails.add(trainTime);
                        pnrDetails.add(seatNumber);
                        pnrDetails.add(transactionAmount);
                        pnrDetails.add(paymentMode);
                        pnrDetails.add(transactionDate);
                        pnrDetails.add(transactionTime);
                        pnrDetails.add(BookingDate);
                        pnrDetails.add(BookingTime);
                        pnrDetails.add(is_cancelled);
                        pnrDetails.add(status);

                        session.setAttribute("pnrDetails", pnrDetails);
                    }

                }

            }
            else{
                session.setAttribute("pnrDetails", "No PNR Record Found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
