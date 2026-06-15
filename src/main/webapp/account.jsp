<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Account Settings - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container" style="max-width:600px; margin:30px auto;">
        <h2>Account Settings</h2>

        <c:if test="${not empty success}">
            <p style="color: green;">${success}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <c:set var="user" value="${sessionScope.user}" />

        <!-- Profile Picture Preview -->
        <div style="text-align:center; margin-bottom:20px;">
            <img id="preview" 
                 src="${pageContext.request.contextPath}/images/${empty user.profileImage ? 'default-avatar.png' : user.profileImage}" 
                 alt="Profile" 
                 class="profile-avatar-large">
        </div>

        <form action="${pageContext.request.contextPath}/account" 
              method="post" 
              enctype="multipart/form-data" 
              class="admin-form">
            <label>Full Name:</label>
            <input type="text" name="fullname" value="${user.fullname}" required>

            <label>Phone:</label>
            <input type="text" name="phone" value="${user.phone}" required>

            <label>Street:</label>
            <input type="text" name="street" value="${user.street}" required>

            <label>Village:</label>
            <input type="text" name="village" value="${user.village}" required>

            <label>City:</label>
            <input type="text" name="city" value="${user.city}" required>

            <label>Landmark:</label>
            <input type="text" name="landmark" value="${user.landmark}">

            <label>Pincode:</label>
            <input type="text" name="pincode" value="${user.pincode}" required>

            <label>Profile Picture:</label>
            <input type="file" name="profileImage" accept="image/*" onchange="previewImage(this)">

            <button type="submit" class="btn">Save Changes</button>
        </form>
    </div>

    <%@ include file="footer.jsp" %>

    <script>
        function previewImage(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('preview').src = e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</body>
</html>