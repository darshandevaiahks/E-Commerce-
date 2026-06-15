<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>   <!-- admin header -->

    <div class="admin-dashboard">
        <!-- WELCOME ROW -->
        <div class="admin-welcome">
            <div class="admin-avatar">
                <img src="<%= contextPath %>/images/<%= avatar %>" alt="Profile" class="user-avatar">
            </div>
            <div class="admin-greeting">
                <h1>Welcome back, ${adminUser.fullname}!</h1>
                <p>You’re logged in as Administrator</p>
            </div>
        </div>

       <!-- Statistics Cards -->
<div class="stats-row">
    <div class="stat-card">
        <h3>Total Orders</h3>
        <span class="stat-number">${totalOrders}</span>
    </div>
    <div class="stat-card">
        <h3>Total Products</h3>
        <span class="stat-number">${totalProducts}</span>
    </div>
    <div class="stat-card">
        <h3>Pending Shipments</h3>
        <span class="stat-number">${pendingCount}</span>
    </div>
</div>

        <!-- RECENT ORDERS TABLE -->
        <div class="section">
            <h2>Recent Orders</h2>
            <table>
                <thead>
                    <tr>
                        <th>Order #</th>
                        <th>Customer ID</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ord" items="${recentOrders}">
                        <tr>
                            <td>${ord.id}</td>
                            <td>${ord.userId}</td>
                            <td>₹ <fmt:formatNumber value="${ord.total}" pattern="#,##0.00"/></td>
                            <td>
                                <span class="status status-${fn:toLowerCase(ord.status)}">${ord.status}</span>
                            </td>
                            <td>${ord.orderDate}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty recentOrders}">
                        <tr><td colspan="5">No orders yet.</td></tr>
                    </c:if>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn">View All Orders</a>
        </div>

        <!-- RECENT PRODUCTS TABLE -->
        <div class="section">
            <h2>Recent Products</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Category</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="prod" items="${recentProducts}">
                        <tr>
                            <td>${prod.id}</td>
                            <td>${prod.name}</td>
                            <td>₹ ${prod.price}</td>
                            <td>${prod.stock}</td>
                            <td>${prod.categoryName}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty recentProducts}">
                        <tr><td colspan="5">No products added yet.</td></tr>
                    </c:if>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/admin/products" class="btn">Manage Products</a>
        </div>
    </div>

    <%@ include file="footer.jsp" %>   <!-- admin footer (might be empty, fine) -->
</body>
</html>