<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: tejas
  Date: 26/04/25
  Time: 1:42 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PNR STATUS</title>
</head>
<body>
<form action="CheckPNR" method="post">
    <div class="container">
        <div class="inputDetails">
            <label for="pnr">PNR NUMBER</label>
            <input type="text" name="pnrNo" id="pnr" placeholder="Enter PNR NUMBER">
        </div>


    </div>
    <div class="btn">
        <button type="submit">CHECK STATUS</button>
    </div>
    <div class="pnrDetails">
        <%
            // Retrieve the pnrDetails ArrayList from the session
            ArrayList<String> pnrDetails = (ArrayList<String>) session.getAttribute("pnrDetails");
            if (pnrDetails != null && !pnrDetails.isEmpty()) {
        %>
        <h2>PNR Details</h2>
        <table border="1" style="width: 100%">
            <tr style="background-color: lightskyblue; height: 60px; letter-spacing: 3px">
                <th>PNR No</th>
                <th>Train No</th>
                <th>Train Name</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Train Time</th>
                <th>Seat Number</th>
                <th>Transaction Amount</th>
                <th>Payment Mode</th>
                <th>Transaction Date</th>
                <th>Transaction Time</th>
                <th>Booking Date</th>
                <th>Booking Time</th>
                <th>Is Cancelled</th>
                <th>Status</th>
            </tr>
            <tr>
                <td><%= pnrDetails.get(0) %></td>
                <td><%= pnrDetails.get(1) %></td>
                <td><%= pnrDetails.get(2) %></td>
                <td><%= pnrDetails.get(3) %></td>
                <td><%= pnrDetails.get(4) %></td>
                <td><%= pnrDetails.get(5) %></td>
                <td><%= pnrDetails.get(6) %></td>
                <td><%= pnrDetails.get(7) %></td>
                <td><%= pnrDetails.get(8) %></td>
                <td><%= pnrDetails.get(9) %></td>
                <td><%= pnrDetails.get(10) %></td>
                <td><%= pnrDetails.get(11) %></td>
                <td><%= pnrDetails.get(12) %></td>
                <td><%= pnrDetails.get(13) %></td>
            </tr>
        </table>
        <% } else { %>
        <p><%= pnrDetails%></p>
        <% } %>
    </div>

</form>

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
    </div>

    <!-- Copyright Section -->
    <div style="text-align: center; border-top: 1px solid #555; padding: 10px 0;">
        <p>&copy; 2024 Railway Reservation System. All rights reserved.</p>
    </div>
</footer>
</body>
</html>
