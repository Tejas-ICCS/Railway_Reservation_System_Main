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

@WebServlet("/LoginAdmin")
public class AdminLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection con = databaseConnection.getConnection();
        HttpSession session = request.getSession();
        try{
            String query = "select * from admin where username=? and password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                session.setAttribute("adminName", rs.getString("name"));
                response.sendRedirect("/Railway_Reservation_System/index.jsp");
                System.out.println("Admin logged in");
            }
            else {
                session.setAttribute("Adminerror", "Admin Not Found");
                response.sendRedirect("agent.jsp");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
