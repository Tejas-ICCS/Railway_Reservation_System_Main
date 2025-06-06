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
public class    AddTrain extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
            HttpSession session = request.getSession();
            String trainNo = request.getParameter("TrainNo");
            String trainName = request.getParameter("TrainName");
            String trainTime = request.getParameter("departure");
            String source = request.getParameter("source");
            String destination = request.getParameter("destination");

//            int stops = Integer.parseInt(request.getParameter("stops"));


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
                days.append("Daily");
            } else {
                // Add individual days if not null
                if (request.getParameter("mon") != null) days.append("Mon,");
                if (request.getParameter("tue") != null) days.append("Tue,");
                if (request.getParameter("wed") != null) days.append("Wed,");
                if (request.getParameter("thu") != null) days.append("Thu,");
                if (request.getParameter("fri") != null) days.append("Fri,");
                if (request.getParameter("sat") != null) days.append("Sat,");
                if (request.getParameter("sun") != null) days.append("Sun,");

                // Remove trailing dash if any days were added
                if (days.length() > 0 && days.charAt(days.length() - 1) == ',') {
                    days.setLength(days.length() - 1);
                }
            }


            String select = "select train_no from Train where train_no = "+Integer.parseInt(trainNo);

            String selectedDays = days.toString(); // Final result

            String insertTrain = "insert into Train(train_no, train_name, train_source, " +
                    "train_destination, train_time, train_frequency, total_coach,username,total_seats,is_cancelled) values(?,?,?,?,?,?,?,?,?,?)";

            String routeInsert = "";

            DatabaseConnection db = DatabaseConnection.getInstance();
            Connection con = db.getConnection();
            PreparedStatement ps = null;
            Statement stmt = null;

            try {
                stmt = con.createStatement();
                int temp = 0;
                ResultSet rs =stmt.executeQuery(select);
                while (rs.next()) {

                    temp = rs.getInt("train_no");
                }

                if (temp==Integer.parseInt(trainNo)){
                    session.setAttribute("trainmsg", "Train already exists");
                    response.sendRedirect("/Railway_Reservation_System/adminPage.jsp");
                }
                else{
                    ps = con.prepareStatement(insertTrain);
                    ps.setInt(1, Integer.parseInt(trainNo));
                    ps.setString(2, trainName);
                    ps.setString(3,source);
                    ps.setString(4,destination);
                    ps.setString(5,trainTime);
                    ps.setString(6,selectedDays);
                    ps.setInt(7,Integer.parseInt(coach));
                    ps.setString(8,"demo_admin");
                    ps.setInt(9,100);
                    ps.setBoolean(10,false);


                    int row = ps.executeUpdate();
                    if(row>0){
                        System.out.println("Inserted Train");
                        session.setAttribute("trainmsg", "Train added Successfully");
                        response.sendRedirect("/Railway_Reservation_System/adminPage.jsp");
                    }
                    else{
                        session.setAttribute("trainmsg", "Something went wrong");
                        response.sendRedirect("/Railway_Reservation_System/adminPage.jsp");
                    }
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }
}
