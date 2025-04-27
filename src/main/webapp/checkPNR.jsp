<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: tejas
  Date: 26/04/25
  Time: 1:42 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map<String, String> ticketInfo = (Map<String, String>) request.getAttribute("ticketInfo");
    List<Map<String, String>> passengerList = (List<Map<String, String>>) request.getAttribute("passengerList");
    String pnrNo = (String) request.getAttribute("pnrNo");
    String pnrError = (String) request.getAttribute("pnrError");

    boolean showTableData = (ticketInfo != null && passengerList != null) || (pnrError != null);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Check PNR Status</title>
    <link rel="stylesheet" href="pnrStatus.css">
</head>

<%--<%
    Map<String, String> ticketInfo = (Map<String, String>) session.getAttribute("ticketInfo");
    List<Map<String, String>> passengerList = (List<Map<String, String>>) session.getAttribute("passengerList");
    String pnrNo = (String) session.getAttribute("pnrNo");
    String pnrError = (String) session.getAttribute("pnrError");
    boolean showTableData = (ticketInfo != null && passengerList != null) || (pnrError != null);
    // After reading, clear session attributes so after refresh they are gone
   /* session.removeAttribute("ticketInfo");
    session.removeAttribute("passengerList");
    session.removeAttribute("pnrNo");
    session.removeAttribute("pnrError");*/
%>--%>


<body>
<div class="nav">
    <h1>Railway Reservation System</h1>
</div>

<div class="container">

    <h2>Check PNR Status</h2>
    <form action="CheckPNR" method="post">
        <label for="pnrNumber">Enter PNR Number:</label>
        <br>
        <input type="text" id="pnrNumber" name="pnrNumber"
               placeholder="Enter your PNR..." maxlength="9" minlength="9" required
               value="<%= pnrNo != null ? pnrNo : "" %>">
        <br>
        <button type="submit">CHECK STATUS</button>
    </form>
</div>




<% if (showTableData) { %>

<div class="tableData">
    <%
        ticketInfo = (Map<String, String>) request.getAttribute("ticketInfo");
        passengerList = (List<Map<String, String>>) request.getAttribute("passengerList");
        pnrNo = (String) request.getAttribute("pnrNo");
        pnrError = (String) request.getAttribute("pnrError");
//        boolean showTableData = (ticketInfo != null && passengerList != null) || (pnrError != null);

        if (pnrError != null) {
    %>
    <p style="  color: red;text-align: center"><%= pnrError %></p>
    <%
    } else if (ticketInfo != null && passengerList != null) {
    %>
    <h2>PNR Status for: <%= pnrNo %></h2>
    <div style="font-size: 16px; margin-bottom: 20px;">
        <p><strong>Email:</strong> <%= ticketInfo.get("emailID") %></p>
        <p><strong>Train Number:</strong> <%= ticketInfo.get("train_no") %></p>
        <p><strong>Total Amount:</strong> ₹<%= ticketInfo.get("total_amount") %></p>
        <p><strong>Booking Date:</strong> <%= ticketInfo.get("booking_date") %> at <%= ticketInfo.get("booking_time") %></p>
        <p><strong>Status:</strong> <%= ticketInfo.get("status") %></p>
        <p><strong>Journey Date:</strong> <%= ticketInfo.get("journy_date") %> at <%= ticketInfo.get("journy_time") %></p>
        <p><strong>From:</strong> <%= ticketInfo.get("source") %> ➔ <strong>To:</strong> <%= ticketInfo.get("destination") %></p>
    </div>

    <h3>Passenger Details:</h3>
    <table border="1" cellpadding="10" cellspacing="1" style="width:100%; text-align:center;">
        <tr style="    background: linear-gradient(to right, #36d1dc, #5b86e5);letter-spacing: 2px; /* teal-blue */
 color: #222121;">
            <th>Seat Number</th>
            <th>Passenger Name</th>
            <th>Passenger Age</th>
            <th>Passenger Gender</th>
        </tr>
        <%
            for (Map<String, String> passenger : passengerList) {
        %>
        <tr style="letter-spacing: 2px;font-weight: 500;">
            <td><%= passenger.get("seat_number") %></td>
            <td><%= passenger.get("passenger_name") %></td>
            <td><%= passenger.get("passenger_age") %></td>
            <td><%= passenger.get("passenger_gender") %></td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <% }
        session.removeAttribute("ticketInfo");
        session.removeAttribute("passengerList");
        session.removeAttribute("pnrNo");
        session.removeAttribute("pnrError");
    %>

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
