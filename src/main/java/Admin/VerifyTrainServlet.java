package Admin;

import DatabaseConnection.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/VerifyTrainServlet")
public class VerifyTrainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String trainNo = request.getParameter("trainNo");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            Connection con = db.getConnection();
            String query = "SELECT * FROM train WHERE train_no = ? and is_cancelled= 'false'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, trainNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.print("{");
                out.print("\"status\":\"found\",");
                out.print("\"trainNo\":\"" + rs.getString("train_no") + "\",");
                out.print("\"trainName\":\"" + rs.getString("train_name") + "\",");
                out.print("\"trainSource\":\"" + rs.getString("train_source") + "\",");
                out.print("\"trainDestination\":\"" + rs.getString("train_destination") + "\",");
                out.print("\"trainFrequency\":\"" + rs.getString("train_frequency") + "\"");
                out.print("}");
            } else {
                out.print("{\"status\":\"not_found\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\"}");
        }
    }
}
