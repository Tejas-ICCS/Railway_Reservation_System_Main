<%@ page session="true" %>
<%
    String user = (String) session.getAttribute("FirstName");
    String admin = (String) session.getAttribute("adminName");
    session.setAttribute("from","railway.reservationproject12@gmail.com");
%>

<%
    String status = (String) session.getAttribute("PasswordChange");
    if (status != null) {
%>
<script>
    alert("<%= status %>");
</script>
<%
        session.removeAttribute("PasswordChange");
    }
%>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <link rel="icon" href="Image/logo.png">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <title>Railway Reservation System</title>
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
       <%-- <div class="nav1">
            <img src="Image/logo.png" alt="Railway logo">
            <a href="index.jsp" target="_parent">HOME</a>
            <% if(user == null && admin ==null ) {%>
                <a href="login.jsp">USER LOGIN</a>
                <a href="agent.jsp">AGENT LOGIN</a>
            <%}else if(admin == null && user==null){%>
                <a href="login.jsp">USER LOGIN</a>
                <a href="agent.jsp">AGENT LOGIN</a>
            <%}%>


            <% if(admin==null){%>
            <a href="register.jsp">REGISTER</a>
            <%}%>
&lt;%&ndash;            <a href="submitOtp.jsp">Submit OTP</a>&ndash;%&gt;

            <a href="train-schedule.jsp">TRAIN SCHEDULE</a>
            <a href="contact.jsp">CONTACT US</a>
            <a href="help.jsp">HELP & SUPPORT</a>

            <% if(admin!=null){%>
            <a href="adminPage.jsp">ADMIN PAGE</a>
            <%}%>
        </div>--%>

        <div class="nav1">
            <img src="Image/logo.png" alt="Railway logo">
            <a href="index.jsp" target="_parent">HOME</a>
            <%-- Show login/register links only if no user or admin is logged in --%>
            <% if (user == null && admin == null) { %>
            <a href="login.jsp">USER LOGIN</a>
            <a href="agent.jsp">ADMIN LOGIN</a>
            <a href="register.jsp">REGISTER</a>
            <% } %>

            <%-- Show ADMIN PAGE only if admin is logged in --%>
            <% if (admin != null) { %>
            <a href="adminPage.jsp">ADMIN PAGE</a>
            <a href="ChangePassword.jsp">CHANGE PASSWORD</a>
            <% } %>
            <% if (user != null) { %>
            <a href="ChangePassword.jsp">CHANGE PASSWORD</a>
            <% } %>

            <a href="Train_Schedule.jsp">TRAIN SCHEDULE</a>
            <a href="payment.jsp"><PAYMENT></PAYMENT></a>
            <a href="contact.jsp">CONTACT US</a>
            <a href="help.jsp">HELP & SUPPORT</a>

        </div>



    </header>

    <div class="main">
        <h1 id="mh1">"Your journey, our mission" </h1>
        <br>
        <h3 id="mh3">Welcome to Railway Reservation System</h3>
        <br>

        <% if(admin == null){%>
        <span>BOOK YOUR TICKETS HERE</span>
        <br><br>
        <a href="Booking.jsp"><button class="btn" >BOOK TICKET</button></a>
        <%}%>
    </div>

    <div class="main1">
        <div class="i1">
            <img src="Image/mumbai.jpg" alt="Mumbai">
            <h3>CSMT Station</h3>
        </div>
        <div class="i1">
            <img src="Image/pune.avif" alt="pune">
            <h3>Pune Station</h3>
        </div>
        <div class="i1">
            <img src="Image/benglore.jpg" alt="banglore">
            <h3>Bengaluru Station</h3>
        </div>
        <div class="i1">
            <img src="Image/gujrat.webp" alt="Gujrat">
            <h3>Valsad Station</h3>
        </div>
        <div class="i1">
            <img src="Image/Delhi.jpg" alt="Delhi">
            <h3>Delhi Station</h3>
        </div>
        <div class="i1">
            <img src="Image/kolkatta.jpg" alt="kolkatta">
            <h3>Kolkatta Station</h3>
        </div>
        <div class="i1">
            <img src="Image/havada.jpg" alt="havada">
            <h3>Hawada Station</h3>
        </div>
        <div class="i1">
            <img src="Image/chenai.jpg" alt="chenai">
            <h3>Chennai Station</h3>
        </div>    
    </div>
    <div class="line"></div>

    <div class="info">
        <div class="left">
            <p>A <i><b>Railway Reservation System </b></i>project is a software application designed to manage and automate the ticket booking and scheduling processes for railway services. It typically includes features for passengers to book tickets, manage reservations, and access train schedules.</p>
            <br>
            <h2>Project Overview</h2>
            <br>
            <p>The Railway Reservation System aims to streamline the process of reserving train tickets and managing passenger details, making it more efficient and user-friendly. The system can be developed as a desktop, web, or mobile application.</p>
        </div>
        <div class="right">
                <img src="Image/dhudhsagar.jpg" alt="Waterfall">
        </div>

    </div>
    <div class="line"></div>
        <div class="team1">
            <h1>Team Members</h1>
        </div>
        <div class="team">
            
            <div class="t1">
                <img src="Image/Tejas.jpg" alt="Tejas Devgharkar" >
                <h3 id="tag">Tejas Devgharkar</h3>
                <span id="tag">C 90</span>
            </div>
            <div class="t1">
                <img src="Image/IMG_20241221_202000.jpg" alt="Pradnya mahadik" >
                <h3 id="tag">Pradnya Mahadik</h3>
                <span id="tag">C 11</span>
            </div>
            <div class="t1">
                <img src="Image/IMG_20241221_201707.jpg" alt="Anisa Pathan" >
                <h3 id="tag">Anisa Pathan</h3>
                <span id="tag">C 86</span>
            </div>
        </div>
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