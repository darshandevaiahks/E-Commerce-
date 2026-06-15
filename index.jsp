<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Amesing Store - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <!-- ==== BIG PROMOTIONAL BANNERS ==== -->
    <div class="banner-section">
        <div class="banner-slider">
            <div class="banner-item">
                <img src="${pageContext.request.contextPath}/images/banner1.jpg" alt="New Arrivals">
                <div class="banner-text">New Arrivals – Up to 30% Off</div>
            </div>
            <div class="banner-item">
                <img src="${pageContext.request.contextPath}/images/banner2.jpg" alt="Festival Sale">
                <div class="banner-text">Festival Mega Sale – Flat 50% Off</div>
            </div>
            <div class="banner-item">
                <img src="${pageContext.request.contextPath}/images/banner3.jpg" alt="Clearance">
                <div class="banner-text">Clearance – Starting from $9.99</div>
            </div>
        </div>
    </div>

    <div class="main-container">
        <!-- Sidebar with Category Cards -->
        <div class="sidebar">
            <h3>Categories</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/home">All</a></li>
                <c:forEach var="cat" items="${categories}">
                    <li>
                        <a href="${pageContext.request.contextPath}/home?category=${cat.id}" class="category-link">
                            <c:if test="${not empty cat.image}">
                                <img src="${pageContext.request.contextPath}/images/${cat.image}" alt="${cat.name}" class="category-icon">
                            </c:if>
                            ${cat.name}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- Product Grid -->
        <div class="content">
            <h2>Featured Products</h2>
            <div class="product-grid">
                <c:forEach var="product" items="${products}">
                    <div class="product-card">
                        <c:if test="${product.discountPercent > 0}">
                            <span class="discount-badge">-${product.discountPercent}%</span>
                        </c:if>
                        <img src="${pageContext.request.contextPath}/images/${product.image}" alt="${product.name}">
                        <h3>${product.name}</h3>
                        <p class="price">
₹ ${product.price}
                            <c:if test="${product.originalPrice > 0 && product.originalPrice > product.price}">
                                <span class="original-price">₹  ${product.originalPrice}</span>
                            </c:if>
                        </p>
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
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>