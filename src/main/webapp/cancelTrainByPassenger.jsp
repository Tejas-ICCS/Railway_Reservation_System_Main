<%--
  Created by IntelliJ IDEA.
  User: tejas
  Date: 27/04/25
  Time: 8:20 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    String status = (String) session.getAttribute("overallSuccess");
    if (status != null) {
%>
<script>
    alert("<%= status %>");
</script>
<%
        session.removeAttribute("overallSuccess");
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cancel Tickit</title>
    <link rel="stylesheet" href="cancelTickit.css">
</head>
<body>

<div class="container">
    <h1>Cancel Train Ticket</h1>
    <form action="cancelTrainByPassenger" method="post">
        <label for="ticketId">PNR Number:</label>
        <input type="text" id="ticketId" name="pnrNo" maxlength="9" required>

        <label for="trainNumber">Train Number:</label>
        <input type="text" id="trainNumber" name="trainNo" maxlength="6" required>

        <label for="reason">Reason for Cancellation:</label>
        <textarea id="reason" name="reason" rows="4" placeholder="Optional..."></textarea>

        <button type="submit">Cancel Ticket</button>
    </form>
</div>

</body>

<footer style="background-color: #313131; color: #fff; padding: 20px 0;">
    <div style="display: flex; justify-content: space-around; flex-wrap: wrap; padding: 20px;">

        <!-- About Section -->
        <div style="max-width: 300px;">
            <h4>About Us</h4>
            <p>We aim to provide a seamless railway ticket booking experience, offering real-time updates, schedule management, and customer support.</p>
        </div>

        <!-- Quick Links -->
        <div style="max-width: 200px;">
            <h4>Quick Links</h4>
            <ul style="list-style-type: none; padding: 0;">
                <li><a href="index.jsp" style="color: #fff; text-decoration: none;">Home</a></li>
                <li><a href="Booking.jsp" style="color: #fff; text-decoration: none;">Book Ticket</a></li>
                <li><a href="train-schedule.html" style="color: #fff; text-decoration: none;">Train Schedule</a></li>
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
                <li><a href="feedback.html" style="color: #fff; text-decoration: none;">Submit Feedback</a></li>
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

</html>
