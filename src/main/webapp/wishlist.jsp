<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Wishlist - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container" style="max-width:1200px; margin:30px auto;">
        <h2>My Wishlist</h2>

        <c:if test="${empty wishlistItems}">
            <p>Your wishlist is empty. <a href="${pageContext.request.contextPath}/home">Browse products</a></p>
        </c:if>

        <div class="product-grid">
            <c:forEach var="item" items="${wishlistItems}">
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/images/${item.image}" alt="${item.productName}">
                    <h3>${item.productName}</h3>
                    <p class="price">₹ ${item.price}</p>
                    <a href="${pageContext.request.contextPath}/product?id=${item.productId}" class="btn">View Details</a>
                    <a href="${pageContext.request.contextPath}/wishlist/remove?productId=${item.productId}" 
                       class="btn" style="background:#FF7F50;" 
                       onclick="return confirm('Remove from wishlist?')">Remove</a>
                </div>
            </c:forEach>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>