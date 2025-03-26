<!DOCTYPE html>
<html>
<head>
    <title>Registration Form</title>
    <link rel="stylesheet" type="text/css" href="style_register.css">
    <link rel="icon" href="Image/logo.png">
</head>
<body>

    <div class="container">
        <h1>Registration Form</h1>
        <form action="registerUser" method="post">
            <span>First Name</span>
            <div class="form-group">
                <input type="text" placeholder="Enter First Name" id="firstName" name="firstName" required>
            </div>
            <span>Last Name</span>
            <div class="form-group">
                
                <input type="text" id="lastName"  placeholder ="Enter Last Name" name="lastName" required>
            </div>
            <span>Email</span>
            <div class="form-group">
                
                <input type="email" id="email" name="email" required placeholder="Enter Email">
            </div>
            <span>Date Of Birth</span>
            <div class="form-group">
                
                <input type="date" id="age" name="dateOfBirth" min="18" required placeholder="Enter Age">
            </div>
            <span>Mobile Number</span>
            <div class="form-group">
                
                <input type="tel" id="mobile" name="mobile" pattern="[0-9]{10}" placeholder="Enter Mobile Number" required>
            </div>
            <span>Password</span>
            <div class="form-group">
                <input type="password" placeholder="Enter password" name="pass" required id="p">
                <!-- <input type="password" placeholder="Re-enter password" name="pass2" required id="p"> -->
            </div>
            <div class="form-group">
                <select id="gender" name="gender" required id="gender" class="gender">
                    <option value="">Select Gender</option>
                    <option value="male" name="male">Male</option>
                    <option value="female" name="female">Female</option>
                    <option value="other" name="other">Other</option>
                </select>
                    
            
            </div>
            <div class="tag">
                <label for="log" >Already Registered?</label>
                <a id="log" href="login.jsp">Login</a>
            </div>
            <button type="submit" class="btn" >Register</button>
        </form>
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
              <li><a href="bookjsp" style="color: #fff; text-decoration: none;">Book Ticket</a></li>
              <li><a href="train-schedule.jsp" style="color: #fff; text-decoration: none;">Train Schedule</a></li>
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
              <li><a href="feedback.jsp" style="color: #fff; text-decoration: none;">Submit Feedback</a></li>
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