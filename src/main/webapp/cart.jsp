<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container" style="max-width:1000px; margin:30px auto;">
        <h2>Your Cart</h2>
        <c:if test="${empty cartItems}">
            <p>Your cart is empty. <a href="${pageContext.request.contextPath}/home">Continue shopping</a></p>
        </c:if>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <c:if test="${not empty cartItems}">
            <table>
                <tr>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="item" items="${cartItems}">
                    <tr>
                        <td>${item.productName}</td>
                        <td>₹ ${item.price}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/updateCart" method="post" style="display:inline;">
                                <input type="hidden" name="cartId" value="${item.id}">
                                <input type="number" name="quantity" value="${item.quantity}" min="0" style="width:60px;" onchange="this.form.submit()">
                            </form>
                        </td>
                        <td>₹ <fmt:formatNumber value="${item.price * item.quantity}" pattern="#,##0.00"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/removeFromCart?cartId=${item.id}" class="btn" style="background:#FF7F50;" onclick="return confirm('Remove this item?')">Remove</a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3" style="text-align:right;"><strong>Total:</strong></td>
                    <td colspan="2"><strong>₹ <fmt:formatNumber value="${total}" pattern="#,##0.00"/></strong></td>
                </tr>
            </table>
            <div style="text-align:right; margin-top:20px;">
                <a href="${pageContext.request.contextPath}/checkout" class="btn">Proceed to Checkout</a>
            </div>
        </c:if>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
