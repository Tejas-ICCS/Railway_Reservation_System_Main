<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 01-03-2025
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>


<%
    String mode = request.getParameter("mode");
//    String otpVerificationError = (String) session.getAttribute("otpVerificationError");
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="submitOtpStyle.css">
</head>
<body>
<form action="verifyOtp" method="post">

    <h2>OTP VERIFICATION</h2>
    <div class="section1">
        <label for="this">Please enter OTP to verify</label>
        <br>

        <input type="password" id="this" minlength="6" maxlength="6" name="otp">
        <input type="hidden" name="mode" value="<%= mode %>">
        <br>

    </div>
    <div class="butonDiv">
        <button type="submit" class="btn1">Verify OTP</button>
        <button type="reset" class="btn2">Reset</button>
        <%
            String otpVerificationError = request.getParameter("eroor");
            if (otpVerificationError != null) {
        %>

        <script>
            alert("<%= otpVerificationError %>");
        </script>
        <%
                session.removeAttribute("trainmsg");
            }
        %>
    </div>

</form>
</body>
</html>
