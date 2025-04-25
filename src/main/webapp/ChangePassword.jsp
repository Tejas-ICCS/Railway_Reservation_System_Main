<%--
  Created by IntelliJ IDEA.
  User: Tejas
  Date: 26-04-2025
  Time: 00:37
  To change this template use File | Settings | File Templates.
--%>
<%
    String  user = (String) session.getAttribute("FirstName");
    String  admin = (String) session.getAttribute("adminName");
    String status = (String) session.getAttribute("PasswordChange");
    if (status != null) {
%>
<script>
    alert("<%= status %>");
</script>
<%
        session.removeAttribute("PasswordChange");
    }
%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Password</title>
    <link rel="icon" href="Image/logo.png">
    <link type="text/css" href="changePassword.css" rel="stylesheet">
</head>
<body>
<form action="ChangePassword" method="post">
    <div class="container">
        <div class="login-box">
            <h1>Change Password</h1>
            <label for="email">Username or Email ID</label>
            <input type="email" id="email" name="username" placeholder="Enter your Username or Email ID" required>
            <label for="oldPass">Old Password</label>
            <input type="password" id="oldPass" name="oldPass" placeholder="Enter your old Password" required>
            <label for="newPass">New Password</label>
            <div class="password-field">
                <input type="password" id="newPass" name="newPass" placeholder="Enter New password" required>
            </div>
            <label for="confPass"> Password</label>
            <div class="password-field">
                <input type="password" id="confPass" name="confirmPass" placeholder="Confirm New password" required>
            </div>
            <label for="#"> You are ?</label>
            <div class="radio-field" id="#">
                <%
                    if(user != null){
                %>
                    <input type="hidden" id="user" name="user" value="user" required>
                <%
                    }else if(admin != null){
                %>
                    <input type="hidden" id="admin" name="user" value="admin" required>
                <%
                    }
                %>
            </div>



            <button type="submit" class="login-btn">Change Password</button>

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
                <a href="forgotPassword.jsp">Forgot password?</a>
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
