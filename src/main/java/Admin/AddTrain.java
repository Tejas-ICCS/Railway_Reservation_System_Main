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

@WebServlet("/AddTrain")
public class AddTrain extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
            HttpSession session = request.getSession();
            String trainNo = request.getParameter("TrainNo");
            String trainName = request.getParameter("TrainName");
            String trainTime = request.getParameter("departure");
            String source = request.getParameter("source");
            String destination = request.getParameter("destination");

            int stops = Integer.parseInt(request.getParameter("stops"));


            String monday = request.getParameter("mon");
            String tuesday = request.getParameter("tue");
            String wednesday = request.getParameter("wed");
            String thursday = request.getParameter("thu");
            String friday = request.getParameter("fri");
            String saturday = request.getParameter("sat");
            String sun = request.getParameter("sun");
            String daily = request.getParameter("daily");
            String coach = request.getParameter("coach");

            StringBuilder days = new StringBuilder();

            if (daily != null) {
                days.append("daily");
            } else {
                // Add individual days if not null
                if (request.getParameter("mon") != null) days.append("mon-");
                if (request.getParameter("tue") != null) days.append("tue-");
                if (request.getParameter("wed") != null) days.append("wed-");
                if (request.getParameter("thu") != null) days.append("thu-");
                if (request.getParameter("fri") != null) days.append("fri-");
                if (request.getParameter("sat") != null) days.append("sat-");
                if (request.getParameter("sun") != null) days.append("sun-");

                // Remove trailing dash if any days were added
                if (days.length() > 0 && days.charAt(days.length() - 1) == '-') {
                    days.setLength(days.length() - 1);
                }
            }


            String select = "select train_no from Train where train_no = ?";

            String selectedDays = days.toString(); // Final result

            String insertTrain = "insert into Train(train_no, train_name, train_source, " +
                    "train_destination, train_time, train_frequency, total_coach,username) values(?,?,?,?,?,?,?,?)";

            DatabaseConnection db = DatabaseConnection.getInstance();
            Connection con = db.getConnection();
            PreparedStatement ps = null;

            try {
                ps = con.prepareStatement(select);
                ps.setString(1, trainNo);
//                ps.executeUpdate();
                int temp = 0;
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    temp = rs.getInt("train_no");
                }

                if (temp>=0){
                    session.setAttribute("trainAdd", "Train already exists");
                }
                else{
                    ps = con.prepareStatement(insertTrain);
                    ps.setInt(1, Integer.parseInt(trainNo));
                    ps.setString(2, trainName);
                    ps.setString(3,source);
                    ps.setString(4,destination);
                    ps.setString(5,trainTime);
                    ps.setString(6,selectedDays);
                    ps.setInt(7,Integer.valueOf(coach));
                    ps.setString(8,"demo_admin");

                    int row = ps.executeUpdate();
                    if(row>0){
                        System.out.println("Inserted Train");
                        response.sendRedirect("/adminPage.jsp");
                    }
                    else{
                        session.setAttribute("addTrainError", "Something went wrong");
                        response.sendRedirect("/adminPage.jsp");
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }
}
