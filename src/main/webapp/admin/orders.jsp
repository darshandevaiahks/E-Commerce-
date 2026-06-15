<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Management - Amesing Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="admin-dashboard">
        <h2>Order Management</h2>

        <table class="admin-table">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer</th>               <!-- changed -->
                    <th>Total</th>
                    <th>Status</th>
                    <th>Date</th>
                    <th>Update Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${orders}">
                    <tr>
                        <td>${o.id}</td>
                        <td>${o.customerName}</td>   <!-- shows the name -->
                        <td>₹ <fmt:formatNumber value="${o.total}" pattern="#,##0.00"/></td>
                        <td>
                            <span class="status status-${o.status.toLowerCase()}">${o.status}</span>
                        </td>
                        <td>${o.orderDate}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/updateOrder" method="post" style="display:inline;">
                                <input type="hidden" name="orderId" value="${o.id}">
                                <select name="status" class="form-select">
                                    <option ${o.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                    <option ${o.status == 'Shipped' ? 'selected' : ''}>Shipped</option>
                                    <option ${o.status == 'Delivered' ? 'selected' : ''}>Delivered</option>
                                    <option ${o.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                                </select>
                                <button type="submit" class="btn btn-sm">Update</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty orders}">
                    <tr><td colspan="6">No orders found.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>