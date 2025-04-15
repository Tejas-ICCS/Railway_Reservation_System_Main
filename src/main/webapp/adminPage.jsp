<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("FirstName");
    String admin = (String) session.getAttribute("adminName");
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=, initial-scale=1.0">
    <title>Admin Login</title>
    <link rel="icon" href="Image/logo.png">
    <link rel="stylesheet" href="AdminPagestyle.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
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
    <img src="Image/logo.png" alt="Railway logo">
    <a href="index.jsp" target="_parent">HOME</a>
    <% if(user == null && admin ==null ) {%>
    <a href="login.jsp">USER LOGIN</a>
    <a href="agent.jsp">AGENT LOGIN</a>
    <%}else if(admin == null && user!=null){%>
    <a href="login.jsp">USER LOGIN</a>
    <a href="agent.jsp">AGENT LOGIN</a>
    <%}%>


    <% if(admin==null){%>
    <a href="register.jsp">REGISTER</a>
    <%}%>
    <%--            <a href="submitOtp.jsp">Submit OTP</a>--%>

    <a href="train-schedule.jsp">TRAIN SCHEDULE</a>
    <a href="contact.jsp">CONTACT US</a>
    <a href="help.jsp">HELP & SUPPORT</a>

    <% if(admin!=null){%>
    <a href="adminPage.jsp">ADMIN PAGE</a>
    <%}%>
</div>

<div class="container">
    <button type="submit" popovertarget="AddTrain" class="btn1">Add Train</button>
    <button type="submit" popovertarget="cancleTrain" class="btn2">Cancle Train</button>
    <button type="submit" popovertarget="UpdateTrain" class="btn3">Update Train</button>
    <button type="submit" popovertarget="DeleteTrain" class="btn4">Delete Train</button>


    <div popover id="AddTrain" class="AddTrain">
        <h1>ADD TRAIN</h1>
        <form action="" method="post">
            <label for="add1">Train Number</label>
            <br>
            <input type="text" name="TrainNo" id="add1" maxlength="6" required>
            <br>
            <label for="add2">Train Name</label>
            <br>
            <input type="text" name="trainName" required id="add2">
            <br>
            <h2>ROUTE DETAILS</h2>
            <!-- <br> -->
            <!-- for loop for total no of stops -->

            <div class="sourceDetails">
                <!-- <label for="source">SOURCE</label> -->
                <br>
                <input type="text" name="source" placeholder="SOURCE" required id="source" >
                <br>
                <!-- <label for="dest">DESTINATION</label> -->
                <br>
                <input type="text" name="destination" placeholder="DESTINATION" required id="dest">
            </div>
            <br>
            <input type="number" name="stops" id="stops"  placeholder="Total NO. OF STOPS" style="text-align: center;width: 300px;">
            <br><br>
            <label for="code">STATION CODE</label>
            <br>
            <input type="text" name="stationCode" required id="code">
            <br>
            <label for="name">STATION NAME</label>
            <br>
            <input type="text" name="stationName" required id="name">
            <br>
            <label for="t1">ARRIVAL TIME</label>
            <br>
            <input type="time" name="arrival" id="t1">
            <br>
            <label for="t2">DEPATURE TIME</label>
            <br>
            <input type="time" name="departure" id="t2">
            <br>

            <!-- Train Frequency  -->
            <h2>Train Frequency</h2>
            <div class="day1">
                <label for="mon">MON</label>
                <label for="tue">TUE</label>
                <label for="wed">WED</label>
                <label for="thu">THU</label>
                <label for="fri">FRI</label>
                <label for="sat">SAT</label>
                <label for="sun">SUN</label>
                <label for="daily">DAILY</label>
            </div>

            <div class="day">
                <input type="checkbox" name="mon" id="mon">
                <input type="checkbox" name="tue" id="tue">
                <input type="checkbox" name="wed" id="wed">
                <input type="checkbox" name="thu" id="thu">
                <input type="checkbox" name="fri" id="fri">
                <input type="checkbox" name="sat" id="sat">
                <input type="checkbox" name="sun" id="sun">
                <input type="checkbox" name="daily" id="daily">
            </div>

            <div class="btn">
                <button type="submit" class="b1">ADD</button>
                <button type="reset" class="b2">RESET</button>
            </div>
        </form>
    </div>
    <div popover id="cancleTrain">Cancle Train</div>
    <div popover id="UpdateTrain">Update Train</div>
    <div popover id="DeleteTrain">Delete Train</div>

</div>



</body>
</html>