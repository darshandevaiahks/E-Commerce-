<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${product.name} - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="product-container">
        <!-- Error messages -->
        <c:if test="${not empty sessionScope.cartError}">
            <p class="error">${sessionScope.cartError}</p>
            <c:remove var="cartError" scope="session" />
        </c:if>

        <div class="product-detail">
            <!-- Product Image -->
            <div class="product-image">
                <img src="${pageContext.request.contextPath}/images/${product.image}"
                     alt="${product.name}">
            </div>

            <!-- Product Info -->
            <div class="product-info">
                <h1>${product.name}</h1>
                <p class="price">
                   ₹ ${product.price}
                    <c:if test="${product.originalPrice > 0 && product.originalPrice > product.price}">
                        <span class="original-price">₹ ${product.originalPrice}</span>
                        <span class="discount-badge">-${product.discountPercent}%</span>
                    </c:if>
                </p>
                <p class="description">${product.description}</p>
                <p><strong>Category:</strong> ${product.categoryName}</p>
                <p><strong>In Stock:</strong> ${product.stock}</p>

                <!-- Action Buttons -->
                <div class="action-buttons">
                    <c:choose>
                        <c:when test="${sessionScope.user == null}">
                            <a href="${pageContext.request.contextPath}/login.jsp" class="btn">Login to Buy</a>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${product.stock > 0}">
                                    <a href="${pageContext.request.contextPath}/addToCart?productId=${product.id}" class="btn">Add to Cart</a>
                                    <a href="${pageContext.request.contextPath}/buyNow?productId=${product.id}" class="btn buy-now">Buy Now</a>
                                </c:when>
                                <c:otherwise>
                                    <span class="out-of-stock">Out of Stock</span>
                                </c:otherwise>
                            </c:choose>

                            <!-- Wishlist -->
                            <c:choose>
                                <c:when test="${inWishlist}">
                                    <a href="${pageContext.request.contextPath}/wishlist/remove?productId=${product.id}" class="btn wishlist-remove">Remove from Wishlist</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/wishlist/add?productId=${product.id}" class="btn">Add to Wishlist</a>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Reviews Section -->
        <div class="reviews-section">
            <h2>Customer Reviews</h2>
            <c:forEach var="rev" items="${reviews}">
                <div class="review">
                    <strong>${rev.userName}</strong> - Rating: ${rev.rating}/5
                    <p>${rev.comment}</p>
                </div>
            </c:forEach>
            <c:if test="${empty reviews}">
                <p>No reviews yet. Be the first to review!</p>
            </c:if>
        </div>

        <!-- Write Review (only for purchasers) -->
        <c:if test="${sessionScope.user != null && canReview}">
            <div class="write-review">
                <h3>Write a Review</h3>
                <form action="${pageContext.request.contextPath}/submitReview" method="post">
                    <input type="hidden" name="productId" value="${product.id}">
                    Rating:
                    <select name="rating">
                        <option>5</option><option>4</option><option>3</option><option>2</option><option>1</option>
                    </select><br><br>
                    Comment:<br>
                    <textarea name="comment" rows="4" style="width:100%;"></textarea><br><br>
                    <button type="submit" class="btn">Submit Review</button>
                </form>
            </div>
        </c:if>

        <!-- Suggested Products -->
        <c:if test="${not empty suggestedProducts}">
            <div class="suggested-section">
                <h2>Suggested Products</h2>
                <div class="product-grid">
                    <c:forEach var="sug" items="${suggestedProducts}">
                        <div class="product-card">
                            <c:if test="${sug.discountPercent > 0}">
                                <span class="discount-badge">-${sug.discountPercent}%</span>
                            </c:if>
                            <img src="${pageContext.request.contextPath}/images/${sug.image}" alt="${sug.name}">
                            <h3>${sug.name}</h3>
                            <p class="price">
                                ₹ ${sug.price}
                                <c:if test="${sug.originalPrice > 0 && sug.originalPrice > sug.price}">
                                    <span class="original-price">$ ${sug.originalPrice}</span>
                                </c:if>
                            </p>
                            <c:choose>
                                <c:when test="${sug.stock > 0}">
                                    <a href="${pageContext.request.contextPath}/product?id=${sug.id}" class="btn">View</a>
                                </c:when>
                                <c:otherwise>
                                    <span class="out-of-stock">Out of Stock</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>