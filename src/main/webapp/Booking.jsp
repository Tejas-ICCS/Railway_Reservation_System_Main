<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 15-03-2025
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="BookingStyle.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

</head>
<body>
<header>
    <div class="nav1">
        <img src="Image/railway_Logo.png" alt="Railway logo">
        <a href="index.jsp" target="_parent">HOME</a>
        <!-- <a href="login.jsp">LOGIN</a> -->
        <!-- <a href="agent.jsp">AGENT LOGIN</a> -->

        <!-- <a href="register.jsp">REGISTER</a> -->
        <a href="train-schedule.jsp">TRAIN SCHEDULE</a>
        <a href="contact.jsp">CONTACT US</a>
        <a href="help.jsp">HELP & SUPPORT</a>
    </div>
</header>


<div class="serchDetails">
    <form action="" method="post">
        <h2>Train Ticket Booking</h2>
        <input type="text" id="source" name="source" placeholder="Source Station" required>
        <input type="text" id="destination" name="destination" placeholder="Destination Station" required>
        <input type="date" id="date" required name="dateOfJourney" pattern="">

        <button type="submit" name="submit"  class="btn1" id="sumbit" value="Search Train">Search Train</button>
    </form>
</div>


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
