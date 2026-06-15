<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty product ? 'Add' : 'Edit'} Product - Amesing Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="admin-dashboard">
        <h2>${empty product ? 'Add New Product' : 'Edit Product'}</h2>

        <form action="${pageContext.request.contextPath}/admin/editProduct" method="post" class="admin-form">
            <c:if test="${not empty product}">
                <input type="hidden" name="id" value="${product.id}">
            </c:if>

            <label>Name:</label>
            <input type="text" name="name" value="${product.name}" required>

            <label>Description:</label>
            <textarea name="description" rows="3">${product.description}</textarea>

            <label>Price:</label>
            <input type="number" step="0.01" name="price" value="${product.price}" required>

            <label>Original Price (for discount):</label>
            <input type="number" step="0.01" name="originalPrice" value="${product.originalPrice}">

            <label>Image filename (e.g., myproduct.jpg):</label>
            <input type="text" name="image" value="${product.image}">

            <label>Stock:</label>
            <input type="number" name="stock" value="${product.stock}" required>

            <label>Category:</label>
            <select name="categoryId" required>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.id}" ${product.categoryId == cat.id ? 'selected' : ''}>${cat.name}</option>
                </c:forEach>
            </select>

            <button type="submit" class="btn">Save Product</button>
            <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-cancel">Cancel</a>
        </form>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>