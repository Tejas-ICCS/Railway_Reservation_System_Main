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
<%--    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>--%>

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


<div class="container">
    <br><br>
    <div class="header" style="letter-spacing: 10px;font-weight: 800">Train Ticket Booking</div>
    <div class="sub-header"></div>
    <form action="bookTickit" method="POST">
        <div class="booking-box">
            <div>
                <span class="icon">🚆</span>
                <div style="font-size: 20px;font-weight: 600;letter-spacing: 5px">From</div>
                <input type="text" name="from" <%
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
                <input type="text" name="to"
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
                <input type="date" name="departure" <%
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
        <tr>
            <th>Train No</th>
            <th>Train Name</th>
            <th>From</th>
            <th>To</th>
            <th>Departure Time</th>
            <th>Frequency</th>
            <th>Total Coach</th>

        </tr>
        <% for (String[] train : trainList) { %>
        <tr>
            <td><%= train[0] %></td>
            <td><%= train[1] %></td>
            <td><%= train[2] %></td>
            <td><%= train[3] %></td>
            <td><%= train[4] %></td>
            <td><%= train[5] %></td>
            <td><%= train[6] %></td>
            <td><button style="background-color: orangered" class="btn3"><a>View</a></button></td>
            <td><button style="background-color: green" class="btn3">Book</button></td>
        </tr>
        <% } %>
    </table>
    <%
        // Clear train list after displaying results to prevent persistence on refresh
        session.removeAttribute("trainList");
    %>
    <% } else if (request.getAttribute("trainList") != null) { %>
    <p>No trains available for the selected route and date.</p>
    <% } %>

    <br><br>
</div>


<%
    // Clear train list after displaying results to prevent persistence on refresh
    session.removeAttribute("trainList");
%>
</body>
</html>
