package Admin;

import DatabaseConnection.DatabaseConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet("/CancelTrain")
public class CancelTrain extends HttpServlet {
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
            DatabaseConnection db = DatabaseConnection.getInstance();
            Connection con = db.getConnection();
            Statement stmt = null;
            PreparedStatement ps = null;
            HttpSession session = request.getSession();
            String selectTrain = "SELECT * FROM Train WHERE train_no = " + trainNo +" AND is_cancelled= 'false'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectTrain);
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
