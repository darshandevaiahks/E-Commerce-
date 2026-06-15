<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container" style="max-width:1200px; margin:30px auto;">
        <h2>Search results for "${keyword}"</h2>
        <c:if test="${empty products}">
            <p>No products found.</p>
        </c:if>
        <div class="product-grid">
            <c:forEach var="product" items="${products}">
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/images/${product.image}" alt="${product.name}">
                    <h3>${product.name}</h3>
                    <p class="price">₹ ${product.price}</p>
                    <c:choose>
                        <c:when test="${product.stock > 0}">
                            <a href="${pageContext.request.contextPath}/product?id=${product.id}" class="btn">View</a>
                        </c:when>
                        <c:otherwise>
                            <span class="out-of-stock">Out of Stock</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>