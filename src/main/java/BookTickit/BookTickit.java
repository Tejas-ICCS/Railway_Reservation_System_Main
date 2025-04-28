package BookTickit;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/bookTickit")
public class BookTickit extends HttpServlet {



    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response){
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String date = request.getParameter("departure");

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection con = databaseConnection.getConnection();

        List<String[]> trainList = new ArrayList<>();

        try{
            HttpSession session = request.getSession();
            session.setAttribute("jDate", date);
            String sql = "SELECT * FROM Train WHERE train_source = ? AND train_destination = ? AND is_cancelled ='false'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, from);
            ps.setString(2, to);

            ResultSet rs = ps.executeQuery();


            while(rs.next()){
                trainList.add(new String[]{
                        rs.getString("train_no"),
                        rs.getString("train_Name"),
                        rs.getString("train_source"),
                        rs.getString("train_destination"),
                        rs.getString("train_time"),
                        rs.getString("train_frequency"),
                        String.valueOf(rs.getInt("total_coach")),
                        rs.getString("total_seats")
                });
            }

            request.setAttribute("trainList", trainList);
    //            RequestDispatcher dispatcher = request.getRequestDispatcher("Booking.jsp");
    //            dispatcher.forward(request, response);
            // Store trainList in session to persist it across the redirect
            session.setAttribute("from", from);
            session.setAttribute("to", to);

            session.setAttribute("source", from);
            session.setAttribute("destination",to);

            session.setAttribute("date", date);
            session.setAttribute("trainList", trainList);
            response.sendRedirect("Booking.jsp");

            session.setAttribute("source",from);
            session.setAttribute("destination",to);
//            session.setAttribute("journeyTime",);

        }catch (Exception e){
            System.out.println("Exception is "+e);
        }
    }
}
