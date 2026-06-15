<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container" style="text-align:center; margin:60px auto;">
        <h2>Oops! Something went wrong.</h2>
        <p>We encountered an unexpected error. Please try again later.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn">Go to Home</a>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>