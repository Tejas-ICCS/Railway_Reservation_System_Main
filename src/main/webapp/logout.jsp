<%--
  Created by IntelliJ IDEA.
  User: Tejas
  Date: 26-03-2025
  Time: 21:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%
    session.invalidate();
    response.sendRedirect("/Railway_Reservation_System/index.jsp");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
