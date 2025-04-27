<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("FirstName");
    String admin = (String) session.getAttribute("adminName");
    if (user == null){
        response.sendRedirect("/Railway_Reservation_System/login.jsp");
    }
%>

<%
    String trainNo = request.getParameter("train_no");
%>
<%--selectedDate--%>
<%
    String Success = (String) session.getAttribute("selectedDate");
    if (Success != null) {
%>
<script>
    alert("<%= Success %>");
</script>
<%
        session.removeAttribute("selectedDate");
    }
%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking</title>
    <link rel="icon" href="Image/logo.png">
    <link rel="stylesheet" href="BookingPassengerStyle.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>

<script>
    function generatePassenger() {
        let passenger = document.getElementById("passenger").value;
        let container = document.getElementById("details");
        let payment = document.getElementById("payment");
        container.innerHTML = ""; // Clear previous entries

        // Create table structure
        if (passenger == "") {
            alert("Total Number Of Passenger are Empty");
        }
        else if (passenger > 0) {
            let table = `
        <table border="1" style="width: 100%; border-collapse: collapse; text-align: left;">
            <thead>
                <tr style="background-color:lightblue;text-align:center;height:40px;letter-spacing:4px">
                    <th>Passenger No</th>
                    <th>Passenger Name</th>
                    <th>Passenger Age</th>
                    <th>Passenger Gender</th>
                </tr>
            </thead>
            <tbody>
    `;

            for (let i = 1; i <= passenger; i++) {
                table += `
            <tr style="height:60px">
                <td style="text-align:center;font-weight:700">Passenger ${i}</td>
                <td style="text-align:center;">
                    <input type="text" name="passengerName${i}" style="text-align:center;width:95%;height:50px;border-radius:10px;border:none;background-color:#d6dce2" required>
                </td>
                <td style="text-align:center;">
                    <input type="number" name="passengerAge${i}" min="1" required style="text-align:center;width:95%;height:50px;border-radius:10px;border:none;background-color:#d6dce2">
                </td>
                <td style="text-align:center;" colspan=2>
                    <input type="radio" name="gender${i}" value="Male" id="male${i}" required>
                    <label for="male${i}">Male</label>
                    <input type="radio" name="gender${i}" value="Female" id="female${i}" required>
                    <label for="female${i}">Female</label>
                </td>
            </tr>
        `;
            }

            table += `
            </tbody>
        </table>
        <br>
    `;

            container.innerHTML = table;
            payment.innerHTML =  '<a href="payment.jsp"><button>Make Payment</button></a>';
        } else {
            alert("Enter Passenger Values more than 0");
        }}

</script>


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
        <a href="train-schedule.jsp">TRAIN SCHEDULE</a>
        <a href="contact.jsp">CONTACT US</a>
        <a href="help.jsp">HELP & SUPPORT</a>
    </div>


</header>

<div class="container">
    <h1>Train Tickit Booking</h1>
    <!-- value of boarding station is passed from booking.jsp -->
</div>

<div class="bordingDetails">
    <h3>Bording Station</h3>
    <div class="bording">
        <!-- fetch details from the sessions -->
    </div>
</div>

<%
    String journeyDate = (String) session.getAttribute("jDate");

    String journeyTime = request.getParameter("train_time");
    session.setAttribute("jTime",journeyTime);

    String totalSeats = request.getParameter("total_seats");
    session.setAttribute("totalSeats",totalSeats);
%>

<form action="passengerDetails?trainNo=<%=trainNo%>&journeyDate=<%= journeyDate %>&journeyTime=<%= journeyTime%>" method="post">
    <div class="passengerDetails">
        <h2>Total Number Of Passenger's</h2>
        <br>
        <input type="number" name="totalPassenger" min="1" max="5" id="passenger" required>

        <br><br><br>
    </div>
    <div class="btn">
        <button onclick="generatePassenger()" class="search-button">Add Passsenger</button>
    </div>
    <div id="details"></div>

    <!-- if the total no of passsenger is > 1 then display -->
    <div class="payment" id="payment">

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

    <!-- Copyright Section -->
    <div style="text-align: center; border-top: 1px solid #555; padding: 10px 0;">
        <p>&copy; 2024 Railway Reservation System. All rights reserved.</p>
    </div>
</footer>

</body>
</html>