<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="form-container" style="max-width:550px;">
        <h2>Create Account</h2>
        <c:if test="${not empty error}"><p class="error">${error}</p></c:if>

        <form action="${pageContext.request.contextPath}/register" 
              method="post" 
              enctype="multipart/form-data" 
              class="admin-form">
            <label>Full Name:</label>
            <input type="text" name="fullname" required>

            <label>Email:</label>
            <input type="email" name="email" required>

            <label>Password:</label>
            <input type="password" name="password" required>

            <label>Phone:</label>
            <input type="text" name="phone" required>

            <label>Street:</label>
            <input type="text" name="street" required>

            <label>Village:</label>
            <input type="text" name="village" required>

            <label>City:</label>
            <input type="text" name="city" required>

            <label>Landmark:</label>
            <input type="text" name="landmark">

            <label>Pincode:</label>
            <input type="text" name="pincode" required>

            <label>Profile Picture (optional):</label>
            <input type="file" name="profileImage" accept="image/*">

            <button type="submit" class="btn">Register</button>
        </form>

        <p style="margin-top:15px;">Already have an account? <a href="${pageContext.request.contextPath}/login.jsp">Login</a></p>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>