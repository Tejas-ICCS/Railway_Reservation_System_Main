package Payment;

import ForgotDetails.ForgotDetails;
import SendEmail.SendEmail;
import SendOTP.GenerateOtp;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;


@WebServlet("/submitPayment")
public class Payment extends HttpServlet{
    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    private int otp;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String cardHolderName = req.getParameter("cardHolderName");
        String cardNumber = req.getParameter("cardNumber");
        String month = req.getParameter("month");
        String year = req.getParameter("year");
        String cvv = req.getParameter("cvv");
        String email = req.getParameter("email");
        HttpSession session = req.getSession();

        int amount = 1000;
        StringBuilder sb = new StringBuilder();
        SendEmail se = new SendEmail();

        ArrayList<String > cardDetails = new ArrayList<String>();
        cardDetails.add(cardHolderName);
        cardDetails.add(cardNumber);
        cardDetails.add(String.valueOf(1000));
        cardDetails.add(email);

        /*cardDetails.add(String.valueOf(amount));
        Setting the hardCoded Value of amount*/

        session.setAttribute("cardDetails", cardDetails);


        int otp ;
        GenerateOtp generateOtp = new GenerateOtp();
        otp = generateOtp.getOpt();
        setOtp(otp);
        String from = "railway.reservationproject12@gmail.com";
        session.setAttribute("otp",String.valueOf(otp));

        String htmlContent = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<div style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
                + "<h2 style='color: #2c3e50;'>Payment Authorization Request</h2>"
                + "<p>Hi <strong>" + cardHolderName + "</strong>,</p>"
                + "<p>We received a request to process a payment.</p>"
                + "<p><strong>Card Number:</strong> XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4) + "</p>"
                + "<p><strong>Payment Amount:</strong> ₹" + amount + "</p>"
                + "<p>Your One-Time Password (OTP) to authorize the payment is:</p>"
                + "<div style='background-color: #f1f1f1; padding: 15px; border-radius: 5px; text-align: center;'>"
                + "<h2 style='color: #e74c3c;'>" + otp + "</h2>"
                + "</div>"
                + "<p>If you didn’t initiate this payment, please contact support immediately.</p>"
                + "<br><p>Thank you,<br><strong>Railway Reservation System Team</strong></p>"
                + "</div></body></html>";


        try{
           se.sendEmail("Request Of Payment",email,from,htmlContent);
           resp.sendRedirect("/Railway_Reservation_System/submitOtp.jsp?mode=payment");
       }
       catch(Exception e){
           e.printStackTrace();
       }
    }
}
