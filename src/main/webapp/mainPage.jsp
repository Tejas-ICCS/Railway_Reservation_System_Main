<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>main page t</title>
</head>
<body>
    <header>
        <div class="left">
    		<h2>Welcome, ${param.firstName} ${param.lastName}!</h2> <!-- Access first and last name from URL parameters -->

        </div>
    </header>
</body>
</html>