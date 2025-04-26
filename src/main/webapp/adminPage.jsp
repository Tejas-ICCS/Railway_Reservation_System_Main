<%@ page import="java.util.List" %>
<%@ page import="DatabaseConnection.DatabaseConnection" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("FirstName");
    String admin = (String) session.getAttribute("adminName");
//    String addTrain = (String) session.getAttribute("addTrainError");
    String trainmsg = (String) session.getAttribute("trainmsg");
    String cancelTrain = (String) session.getAttribute("cancelTrain");
//    String trainList = (String) session.getAttribute("trainDetails");

    List<String[]> trainAL = (List<String[]>) session.getAttribute("trainDetails");
%>

<%

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
<script>
    function generateStops() {
        let stops = document.getElementById("stops").value;
        let container = document.getElementById("stationDetails");
        container.innerHTML = ""; // Clear previous entries

        for (let i = 1; i <= stops; i++) {
            container.innerHTML += `
                    <h3>Stop ${i}</h3>
                    <label>STATION CODE</label><br>
                    <input type="text" name="stationCode${i}" required><br>
                    <label>STATION NAME</label><br>
                    <input type="text" name="stationName${i}" required><br>
                    <label>ARRIVAL TIME</label><br>
                    <input type="time" name="arrival${i}"><br>
                    <label>DEPARTURE TIME</label><br>
                    <input type="time" name="departure${i}"><br>
                    <label>PLATFORM NUMBER</label><br>
                    <input type="number" name="platform${i}"><br><br>
                `;
        }
    }



    function verifyTrain(event) {
        event.preventDefault(); // Prevent form submit

        var trainNo = document.getElementById("tno").value.trim();

        if (trainNo === "") {
            document.getElementById("trainDetails").innerHTML = "";
            alert("Train number cannot be empty!");
            return;
        }

        fetch("VerifyTrainServlet?trainNo=" + trainNo)
            .then(response => response.json())
            .then(data => {
                const div = document.getElementById("trainDetails");
                div.innerHTML = "";

                if (data.status === "not_found") {
                    div.innerHTML = "<p style='color:red;'>Train not found.</p>";
                } else {
                    div.innerHTML = `
    <div style="text-align: center;">
        <h3>Train Details</h3>
        <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse; margin: 0 auto;">
            <tr><th>Train Number</th><td>${data.trainNo}</td></tr>
            <tr><th>Train Name</th><td>${data.trainName}</td></tr>
            <tr><th>Source</th><td>${data.trainSource}</td></tr>
            <tr><th>Destination</th><td>${data.trainDestination}</td></tr>
            <tr><th>Frequency</th><td>${data.trainFrequency}</td></tr>
        </table>
    </div>
`;


                }
            })
            .catch(err => {
                console.error("Error verifying train:", err);
            });
    }

    document.getElementById("tno").addEventListener("input", function () {
        if (this.value.trim() === "") {
            document.getElementById("trainDetails").innerHTML = "";
        }
    });
</script>
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

    <a href="Train_Schedule.jsp">TRAIN SCHEDULE</a>
    <a href="contact.jsp">CONTACT US</a>
    <a href="help.jsp">HELP & SUPPORT</a>

    <% if(admin!=null){%>
    <a href="adminPage.jsp">ADMIN PAGE</a>
    <%}%>
</div>

<div class="container">
    <button type="submit" popovertarget="AddTrain" class="btn btn-1">Add Train</button>
    <button type="submit" popovertarget="cancleTrain" class="btn btn-2">Cancel Train</button>
<%--    <button type="submit" popovertarget="UpdateTrain" class="btn btn-3">Update Train</button>--%>
<%--    <button type="submit" popovertarget="DeleteTrain" class="btn btn-4">Delete Train</button>--%>


    <div popover id="AddTrain" class="AddTrain">
            <h1>ADD TRAIN</h1>
        <form action="AddTrain" method="post">
            <label for="add1">Train Number</label>
            <br>
            <input type="text" name="TrainNo" id="add1" maxlength="6" required>
            <br>
            <label for="add2">Train Name</label>
            <br>
            <input type="text" name="TrainName" required id="add2">
            <br>
            <h2>ROUTE DETAILS</h2>
            <!-- <br> -->
            <!-- for loop for total no of stops -->

            <%--<div class="sourceDetails">
                <!-- <label for="source">SOURCE</label> -->
                <br>
                <input type="text" name="source" placeholder="SOURCE" required id="source" >
                <br>
                <!-- <label for="dest">DESTINATION</label> -->
                <br>
                <input type="text" name="destination" placeholder="DESTINATION" required id="dest">
                <br>
                <input type="text" name="coach" placeholder="Total Coach" required id="coach">
                <br>
                <input type="text" name="seats" placeholder="Total Seats" required id="seat">
                <br>
                <input type="time" name="departure" placeholder="Departure Time" required id="departure">
            </div>--%>
            <br>
            <input type="number" name="stops" id="stops"  placeholder="Total NO. OF STOPS" style="text-align: center;width: 300px;">
            <br><br>
            <button type="button" class="btn-generate" onclick="generateStops()">Generate</button>
            <div id="stationDetails"></div>

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
                <%
//                    String trainmsg = (String) session.getAttribute("trainmsg");
                    if (trainmsg != null) {
                %>

                <script>
                    alert("<%= trainmsg %>");
                </script>
                <%
                        session.removeAttribute("trainmsg");
                    }
                %>
            </div>
        </form>
    </div>
    <div popover id="cancleTrain">
        <h2>Cancel Train</h2>

<%--        <div class="cancle1">--%>
            <form action="CancelTrain" method="post">
                <label for="tno">Train Number</label>
                <br>
                <input type="text" name="trainNo" id="tno" maxlength="6" minlength="6" required>
                <button onclick="verifyTrain(event)" class="btn11">VERIFY TRAIN</button>
                <br>
                <div id="trainDetails"></div>
                <br>
                <label for="reason">Reason</label>
                <br>
                <textarea name="reason" id="reason" required></textarea>
                <br>
                <button type="submit" class="btn22">CANCLE TRAIN</button>
                <%
                    if (cancelTrain != null) {
                %>

                <script>
                    alert("<%= cancelTrain %>");
                </script>
                <%
                        session.removeAttribute("cancelTrain");
                    }
                %>
            </form>
<%--        </div>--%>
    </div>
    <div popover id="UpdateTrain">Update Train</div>


</div>



</body>
</html>