<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 15-03-2025
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>

<%@ page session="true" %>
<%
     String user = (String) session.getAttribute("FirstName");
     String admin = (String) session.getAttribute("adminName");
    if (user == null){
        response.sendRedirect("/Railway_Reservation_System/login.jsp");
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<style>

</style>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking </title>
    <link rel="stylesheet" href="Bookingstyle.css">
    <link rel="icon" href="Image/logo.png">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
<%--    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>--%>

</head>
<body>
<header>
    <div class="nav">
        <h2>Railway Reservation System</h2>
    </div>

    <div class="nav2">
        <% if(user != null) {%>
        <h4 style="text-align: right;"><i class='bx bx-user'></i> Welcome,  <%= user %> &emsp; </h4>
        <a href="logout.jsp"><i class='bx bx-log-out'></i></a>
        <%}else if(admin != null) {%>
        <h4 style="text-align: right;"><i class='bx bx-user'></i> Welcome,  <%= admin %> &emsp; </h4>
        <a href="logout.jsp"><i class='bx bx-log-out'></i></a>
        <%}else{%>
        <h4 style="text-align: right;"><i class='bx bx-user'></i> Welcome, Guest! </h4>
        <%}%>


        <%--            <i class='bx bx-log-out'><a href="logout.jsp"></a></i>--%>
    </div>
    <div class="nav1">
        <img src="Image/railway_Logo.png" alt="Railway logo">
        <a href="index.jsp" target="_parent">HOME</a>
        <!-- <a href="login.jsp">LOGIN</a> -->
        <!-- <a href="agent.jsp">AGENT LOGIN</a> -->

        <!-- <a href="register.jsp">REGISTER</a> -->
        <a href="Train_Schedule.jsp">TRAIN SCHEDULE</a>
        <a href="contact.jsp">CONTACT US</a>
        <a href="help.jsp">HELP & SUPPORT</a>
    </div>


</header>


<div class="container">
    <br><br>
    <div class="header" style="letter-spacing: 10px;font-weight: 800">Train Ticket Booking</div>
    <div class="sub-header"></div>
    <form action="bookTickit" method="POST">
        <div class="booking-box">
            <div>
                <span class="icon">🚆</span>
                <div style="font-size: 20px;font-weight: 600;letter-spacing: 5px">From</div>
                <input type="text" name="from" class="styled-input" <%
            String from = (String) session.getAttribute("from");
            if (from != null) {
        %>
                       value="<%= from %>"
                    <%
                    session.removeAttribute("from");
            }
        %> REQUIRED>
            </div>
            <%--<div>
                <span class="icon">🔁</span>
            </div>--%>
            <div>
                <span class="icon">🚆</span>
                <div style="font-size: 20px;font-weight: 600;letter-spacing: 5px">To</div>
                <input type="text" class="styled-input" name="to"
                    <%
            String to = (String) session.getAttribute("to");
            if (to != null) {
        %>
                       value="<%= to %>"
                    <%
                    session.removeAttribute("to");
            }
        %>
                       required>
            </div>

            <div>
                <span class="icon">📅</span>
                <div style="font-size: 20px;font-weight: 600;letter-spacing: 5px">Departure Date</div>
                <%
                    // Get today's date
                    java.time.LocalDate today = java.time.LocalDate.now();
                    // Get date 4 months from today
                    java.time.LocalDate maxDate = today.plusMonths(4);

                    // Format them to yyyy-MM-dd for HTML input
                    String minDate = today.toString();
                    String maxDateStr = maxDate.toString();
                %>
                <input type="date" class="styled-input" name="departure"  min="<%= today %>" max="<%= maxDateStr %>"<%
            String date = (String) session.getAttribute("date");
            if (date != null) {
        %>
                       value="<%= date %>"
                       <%
                       session.removeAttribute("date");
            }
        %>
                       required>
            </div>

        </div>

        <button type="submit" class="search-button">SEARCH</button>
    </form>


    <%
        List<String[]> trainList = (List<String[]>) session.getAttribute("trainList");
        if (trainList != null && !trainList.isEmpty()) {
    %>
    <h2>Available Trains</h2>
    <table border="1" style="width: 100%">
        <tr style="background-color: lightskyblue;height: 60px;letter-spacing: 3px">
            <th>Train No</th>
            <th>Train Name</th>
            <th>From</th>
            <th>To</th>
            <th>Departure Time</th>
            <th>Frequency</th>
            <th>Total Coach</th>
            <th>Total Seats</th>
            <th colspan="2">Actions</th>

        </tr>
        <% for (String[] train : trainList) { %>
        <tr>
            <td style="font-weight: 700;height: 30px;"><%= train[0] %></td>
            <td><%= train[1] %></td>
            <td><%= train[2] %></td>
            <td><%= train[3] %></td>
            <td><%= train[4] %></td>
            <td><%= train[5] %></td>
            <td><%= train[6] %></td>
            <td><%= train[7] %></td>
            <td><a href="showTrainDetails.jsp?train_no=<%= train[0]%>"><button  name="<%= train[0] %>" class="btn-one" >View</button></a></td>
            <td><a href="BookingPassenger.jsp?train_no=<%= train[0]%>"><button  class="btn-two"  name="<%= train[0] %>" >Book</button></a></td>
        </tr>
<%--        session.setAttribute("tno", trainList[i]);--%>
        <% } %>
    </table>



    <%
        // Clear train list after displaying results to prevent persistence on refresh
        session.removeAttribute("trainList");
    %>
    <% } else if (request.getAttribute("trainList") == null) { %>
    <p>No trains available for the selected route and date.</p>
    <% } %>

    <br><br>
</div>


<%
    // Clear train list after displaying results to prevent persistence on refresh
    session.removeAttribute("trainList");
%>

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
