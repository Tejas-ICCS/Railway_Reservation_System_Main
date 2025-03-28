<%@ page import="javax.swing.text.html.HTML" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>IRCTC Login Page</title>
    <link rel="stylesheet" href="style_Login.css">
    <link rel="icon" href="Image/logo.png">
</head>
<body> 
    <form action="loginUserNew" method="post">
      <div class="container">
        <div class="login-box">



            <h1>Log in</h1>
            <p id="tag">Need a User account? <a href="register.jsp">Create an account</a></p>
                <label for="username">Username or Email</label>
                <input type="text" id="username" name="username" placeholder="Enter your email or username" required>
                <label for="password">Password</label>
                <div class="password-field">
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                    <span class="show-password">Show</span>
                </div>
                
                <div class="remember-me">
                    <input type="checkbox" id="keep-logged-in">
                    <label for="keep-logged-in">Keep me logged in</label>
                </div>
                
                <button type="submit" class="login-btn">Log in</button>

            <%
                String error = (String) session.getAttribute("error");
                if (error != null) {
            %>

                <script>
                    alert("<%= error %>");
                </script>
                <%
                    session.removeAttribute("error");
                    }
                %>



        <%--                <c:if test="${not empty param.error}">--%>
<%--        			<script>--%>
<%--            				alert("${param.error}");--%>
<%--            				--%>
<%--        			</script>--%>
<%--   				 </c:if>--%>

                <div class="help-links">
                    <a href="agent.jsp">System Login</a>
                    <a href="forgotPassword.jsp">Forgot password?</a>
                    <a href="#">Change Password</a>
                    <a href="index.jsp">Home</a>
                </div>
        </div>
    </div>
    </form>

    <footer style="background-color: #222; color: #fff; padding: 20px 0;">
        <div style="display: flex; justify-content: space-around; flex-wrap: wrap; padding: 20px;">
          
          <!-- About Section -->
          <div style="max-width: 300px;">
            <h4>About Us</h4>
            <p >We aim to provide a seamless railway ticket booking experience, offering real-time updates, schedule management, and customer support.</p>
          </div>
      
          <!-- Quick Links -->
          <div style="max-width: 200px;">
            <h4>Quick Links</h4>
            <ul style="list-style-type: none; padding: 0;">
              <li><a href="index.jsp" style="color: #fff; text-decoration: none;">Home</a></li>
              <li><a href="book.jsp" style="color: #fff; text-decoration: none;">Book Ticket</a></li>
              <li><a href="train-schedule.jsp" style="color: #fff; text-decoration: none;">Train Schedule</a></li>
              <li><a href="contact.jsp" style="color: #fff; text-decoration: none;">Contact Us</a></li>
              <li><a href="help.jsp" style="color: #fff; text-decoration: none;">FAQs</a></li>
            </ul>
          </div>
      
          <!-- Customer Support -->
          <div style="max-width: 200px;">
            <h4>Customer Support</h4>
            <ul style="list-style-type: none; padding: 0;">
              <li>Helpline: 7666300048</li>
              <li>Email: tejasdevgharkar12@gmail.com</li>
              <li><a href="feedback.jsp" style="color: #fff; text-decoration: none;">Submit Feedback</a></li>
            </ul>
          </div>
      
          <!-- Social Media Links -->
          <div style="max-width: 200px;">
            <h4>Follow Us</h4>
            <a href="" target="_blank" style="color: #fff; margin-right: 10px;">Facebook</a>
            <a href="" target="_blank" style="color: #fff; margin-right: 10px;">Twitter</a>
            <a href="" target="_blank" style="color: #fff;">Instagram</a>
          </div>
      
        </div>
        
        <!-- Copyright Section -->
        <div style="text-align: center; border-top: 1px solid #555; padding: 10px 0;">
          <p>&copy; 2024 Railway Reservation System. All rights reserved.</p>
        </div>
      </footer>
</body>
</html>
