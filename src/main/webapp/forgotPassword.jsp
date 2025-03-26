<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>

    <link rel="stylesheet" href="ForgotPassword.css">
    <link rel="icon" href="Image/logo.png">
</head>
<body>
<form action="forgotDetails" method="post">
    <div class="container">
        <div class="login-box">
            <h1>Forgot Password</h1>
            <p id="tag">Need a User account? <a href="register.jsp">Create an account</a></p>
            <form>
                <label for="emailID">Email ID</label>
                <input type="text" id="emailID" name="emailID" placeholder="Enter your Register Email ID" required>

                <label for="DateOfBirth"></label>
                <div class="password-field">
                    <label for="DateOfBirth"></label>
                    <input type="date" id="DateOfBirth" name="DateOfBirth" placeholder="Enter your Date Of Birth" required>
                </div>

                <button type="submit" class="submit-btn">Submit</button>

                <div class="help-links">
                    <!-- <a href="#">Forgot Account Details</a>-->
                    <a href="#"></a>
                    <a href="login.jsp">Login</a>
                    <a href="index.jsp">Home</a>
                </div>
            </form>
        </div>
    </div>
</form>

<footer style="background-color: #222; color: #fff; padding: 50px 20px 20px 20px 0; height:300px; margin-top:70px" >
    <div style="display: flex; justify-content: space-around; flex-wrap: wrap; padding: 50px 20px 20px 20px;">

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