<%
    String mode = request.getParameter("mode");
    String otpVerificationError = (String) session.getAttribute("otpVerificationError");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="paymentStyle.css">
</head>
<body>
    <header>
        <h1>Quick Payment</h1>
    </header>
    <div class="main">
        <div class="container">
            <form action="submitPayment" method="POST">
                <h3>Card Holder Name</h3>
                <input type="text" name="cardHolderName" class="card" required>
                <h3>Card Number</h3>
                <input type="text" name="cardNumber" id="cardNumber" class="card" required maxlength="19" oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(.{4})/g, '$1 ').trim()" placeholder="XXXX XXXX XXXX XXXX">
                <h3>Expiry Date</h3>
                <input type="text" maxlength="2" name="month" id="month" required placeholder="MM" class="Date" oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/^(0[1-9]|1[0-2])$/, '$1').replace(/^(0[1-9]|1[0-2])[^0-9]*$/, '$1')">
                <input type="text" name="year" id="year" maxlength="4" required placeholder="YYYY" class="Date1" oninput="this.value = this.value.replace(/[^0-9]/g, '')">
                <input type="password" name="cvv" id="year" maxlength="3" required placeholder="CVV" class="Date1" oninput="this.value = this.value.replace(/[^0-9]/g, '')">

<%--                <input type="hidden" name="mode" value="<%= mode %>">--%>
                <input type="hidden" name="mode" value="<%= request.getParameter("mode") %>">

                <h3>Email ID</h3>
                <input type="email" name="email" id="" placeholder="Email For verification" required class="card">
                <h3>Total Amount</h3>
                <h2 id="fair"><%=session.getAttribute("fixedAmount")%></h2>
                <button type="submit" name="submit" id="submit">Make Payment</button>
                <%
                    //                    String trainmsg = (String) session.getAttribute("trainmsg");
                    if (otpVerificationError != null) {
                %>

                <script>
                    alert("<%= otpVerificationError %>");
                </script>
                <%
                        session.removeAttribute("trainmsg");
                    }
                %>

            </form>
        </div>
        <div class="imageDiv">
            <img src="Image/payment.jpg" alt="Payment Type">
        </div>
    </div>

    <div class="timer">
        <h2>Make Payment In</h2>
        <h2 id="timer">00:00:00</h2>
    </div>

    <script>
        let countdownDuration = 120; // 2 minutes  in seconds
        let timerElement = document.getElementById('timer');

        function startCountdown(duration) {
            let timer = duration, hours, minutes, seconds;
            setInterval(function () {
                hours = parseInt(timer / 3600, 10);
                minutes = parseInt((timer % 3600) / 60, 10);
                seconds = parseInt(timer % 60, 10);

                hours = hours < 10 ? "0" + hours : hours;
                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                timerElement.textContent = hours + ":" + minutes + ":" + seconds;

                if (--timer < 0) {
                    timer = 0; // Prevent negative countdown
                    window.location.href = "BookingPassenger.jsp";
                    <%
                        session.setAttribute("payment","Payment Time Ends\nTry Again...");
                    %>
                }
            }, 1000);
        }

        window.onload = function () {
            startCountdown(countdownDuration);
        };
    </script>
</body>
</html>
