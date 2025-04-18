<%
    String user = (String) session.getAttribute("FirstName");
    String admin = (String) session.getAttribute("adminName");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Railway Reservation System</title>
    <link rel="stylesheet" href="Train_Schedule.css">
    <link rel="icon" href="Image/logo.png">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <header>
        <div class="nav">
            <h2>Railway Reservation System</h2>
        </div>
    
        <!-- <div class="nav2">
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
        </div> -->
        <div class="nav1">
            <img src="Image/railway_Logo.png" alt="Railway logo">
            <a href="index.jsp" target="_parent">HOME</a>
            <a href="login.jsp">LOGIN</a>
            <!-- <a href="agent.jsp">AGENT LOGIN</a> -->
    
            <a href="register.jsp">REGISTER</a>
            <!-- <a href="train-schedule.jsp">TRAIN SCHEDULE</a> -->
            <a href="contact.jsp">CONTACT US</a>
            <a href="help.jsp">HELP & SUPPORT</a>
        </div>
    
    
    </header>

    <form action="Train_Schedule" method="post">
        <div class="container">
            <h2>Search Train</h2>
            <br>
            <div class="d">
                <div class="dd1">
                    <h3>Train Number</h3>
                </div>
                <div class="dd1">
                    <h3>Train Name</h3>
                </div>
            </div>
            <div class="d1">
                
                <input type="text" name="tNo" id="tNo" maxlength="6"> 
                <h1>OR</h1>
                
                <input type="text" name="tname" id="tname">
            </div>
    
            <div class="btn">
                
                <button type="reset" class="btn-one">Reset</button>
                <button type="submit" class="search-button">Search</button>
                <button onclick="" class="btn-two">All Trains</button>
            </div>
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