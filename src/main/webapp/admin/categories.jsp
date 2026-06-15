<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Category Management - Amesing Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="admin-dashboard">
        <h2>Category Management</h2>

        <!-- ================= ADD CATEGORY FORM ================= -->
        <!-- MANDATORY: enctype must be multipart/form-data for file upload -->
        <form action="${pageContext.request.contextPath}/admin/categories" 
              method="post" 
              enctype="multipart/form-data" 
              class="admin-form" 
              style="margin-bottom:30px;">
            <h4>Add New Category</h4>

            <label>Category Name:</label>
            <input type="text" name="name" required>

            <label>Category Image (optional):</label>
            <input type="file" name="image" accept="image/*">

            <button type="submit" class="btn">Add Category</button>
        </form>

        <!-- ================= EXISTING CATEGORIES ================= -->
        <h3>Existing Categories</h3>
        <table class="admin-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cat" items="${categories}">
                    <tr>
                        <td>${cat.id}</td>
                        <td>${cat.name}</td>
                        <td>
                            <c:if test="${not empty cat.image}">
                                <img src="${pageContext.request.contextPath}/images/${cat.image}" 
                                     alt="${cat.name}" style="width:40px; height:40px; border-radius:4px;">
                            </c:if>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/categories?action=delete&id=${cat.id}" 
                               class="btn btn-sm btn-danger" 
                               onclick="return confirm('Delete this category?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>