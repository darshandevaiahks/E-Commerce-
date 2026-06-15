<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmed - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container" style="text-align:center; margin:60px auto;">
        <h2>Thank You for Your Order!</h2>
        <p>Your order <strong>(#<%= request.getParameter("orderId") %>)</strong> has been placed successfully.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn">Continue Shopping</a>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>