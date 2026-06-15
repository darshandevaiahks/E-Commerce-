<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Management - Amesing Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="admin-dashboard">
        <h2>Product Management</h2>
        <a href="${pageContext.request.contextPath}/admin/editProduct" class="btn" style="margin-bottom:20px;">+ Add New Product</a>

        <table class="admin-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.name}</td>
                        <td>₹ ${p.price}</td>
                        <td>${p.stock}</td>
                        <td>${p.categoryName}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/editProduct?id=${p.id}" class="btn btn-sm">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/deleteProduct?id=${p.id}" class="btn btn-sm btn-danger" onclick="return confirm('Delete this product?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty products}">
                    <tr><td colspan="6">No products found.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>