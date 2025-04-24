package Payment;

import SendEmail.SendEmail;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/submitPayment")
public class Payment extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String cardHolderName = req.getParameter("cardHolderName");
        String cardNumber = req.getParameter("cardNumber");
        String month = req.getParameter("month");
        String year = req.getParameter("year");
        String cvv = req.getParameter("cvv");
        String email = req.getParameter("email");
        HttpSession session = req.getSession();
        int amount = (int) session.getAttribute("amount");
        StringBuilder sb = new StringBuilder();
        SendEmail se = new SendEmail();

        String htmlContent = "";
        String textContent = "";

       try{
           se.sendEmail("Request Of Payment",email,null,amount,cardHolderName,null,htmlContent,textContent);
       }
       catch(Exception e){
           e.printStackTrace();
       }
    }
}
