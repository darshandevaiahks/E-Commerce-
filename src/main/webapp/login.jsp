<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="form-container">
        <h2>Login to Amesing Store</h2>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${param.registered eq 'true'}">
            <p style="color:green;">Registration successful! Please log in.</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <label>Email:</label><br>
            <input type="email" name="email" required><br>
            <label>Password:</label><br>
            <input type="password" name="password" required><br><br>
            <button type="submit" class="btn">Login</button>
        </form>
        <p style="margin-top:15px;">Don't have an account? <a href="${pageContext.request.contextPath}/register.jsp">Register here</a></p>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>