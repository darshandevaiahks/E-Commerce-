<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order History - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container" style="max-width:1000px; margin:30px auto;">
        <h2>My Orders</h2>

        <c:if test="${empty orders}">
            <p>You have no orders yet. <a href="${pageContext.request.contextPath}/home">Start shopping</a></p>
        </c:if>

        <c:if test="${not empty orders}">
            <table>
                <thead>
                    <tr>
                        <th>Order #</th>
                        <th>Date</th>
                        <th>Total</th>
                        <th>Payment</th>   <!-- New column -->
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.orderDate}</td>
                            <td>₹ ${order.total}</td>
                            <td>${order.paymentMethod}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.status == 'Pending'}">
                                        <span style="color: #FF7F50; font-weight: bold;">Pending</span>
                                    </c:when>
                                    <c:when test="${order.status == 'Shipped'}">
                                        <span style="color: #008080; font-weight: bold;">Shipped</span>
                                    </c:when>
                                    <c:when test="${order.status == 'Delivered'}">
                                        <span style="color: green; font-weight: bold;">Delivered</span>
                                    </c:when>
                                    <c:otherwise>
                                        ${order.status}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/orderDetail?id=${order.id}" class="btn" style="padding:6px 12px;">View Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>