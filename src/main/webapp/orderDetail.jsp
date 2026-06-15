<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order #${order.id} - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container" style="max-width:900px; margin:30px auto;">
        <h2>Order #${order.id}</h2>

        <!-- Order Information -->
        <div style="background:white; padding:20px; border-radius:8px; box-shadow:0 2px 5px rgba(0,0,0,0.1); margin-bottom:20px;">
            <p><strong>Date:</strong> ${order.orderDate}</p>
            <p><strong>Status:</strong> ${order.status}</p>
            <p><strong>Payment Method:</strong> ${order.paymentMethod}</p>
            <p><strong>Total:</strong> ₹ ${order.total}</p>
            <p><strong>Shipping Address:</strong><br>
                ${order.shippingAddress}
            </p>
        </div>

        <!-- Items Table -->
        <h3>Items</h3>
        <table>
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Subtotal</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${order.items}">
                    <tr>
                        <td>${item.productName}</td>
                        <td>${item.quantity}</td>
                        <td>₹ ${item.price}</td>
                        <td>₹ ${item.price * item.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a href="${pageContext.request.contextPath}/orderHistory" class="btn" style="margin-top:20px;">Back to Orders</a>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>