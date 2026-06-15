<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout - Amesing Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container" style="max-width:900px; margin:30px auto;">
        <h2>Checkout</h2>
        <c:if test="${not empty error}"><p class="error">${error}</p></c:if>

        <!-- Order Summary -->
        <h3>Order Summary</h3>
        <table>
            <tr><th>Product</th><th>Qty</th><th>Price</th><th>Subtotal</th></tr>
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td>${item.productName}</td>
                    <td>${item.quantity}</td>
                    <td>$ ${item.price}</td>
                    <td>₹ <fmt:formatNumber value="${item.price * item.quantity}" pattern="#,##0.00"/></td>
                </tr>
            </c:forEach>
            <tr><td colspan="3" align="right"><strong>Total</strong></td><td><strong>₹ <fmt:formatNumber value="${total}" pattern="#,##0.00"/></strong></td></tr>
        </table>

        <!-- Address Section -->
        <h3>Shipping Address</h3>
        <c:set var="user" value="${sessionScope.user}" />
        <div id="addressDisplay">
            <p><strong>${user.fullname}</strong></p>
            <p>${user.street}, ${user.village}, ${user.city}</p>
            <p>Landmark: ${user.landmark}</p>
            <p>Phone: ${user.phone}</p>
            <p>Pincode: ${user.pincode}</p>
            <button type="button" class="btn" onclick="editAddress()">Edit Address</button>
        </div>

        <div id="addressForm" style="display:none;">
            <form action="${pageContext.request.contextPath}/updateAddress" method="post" onsubmit="return confirm('Update address?')">
                <input type="text" name="phone" value="${user.phone}" required placeholder="Phone"><br>
                <input type="text" name="street" value="${user.street}" required placeholder="Street"><br>
                <input type="text" name="village" value="${user.village}" required placeholder="Village"><br>
                <input type="text" name="city" value="${user.city}" required placeholder="City"><br>
                <input type="text" name="landmark" value="${user.landmark}" placeholder="Landmark"><br>
                <input type="text" name="pincode" value="${user.pincode}" required placeholder="Pincode"><br>
                <button type="submit" class="btn">Save Address</button>
                <button type="button" class="btn" onclick="cancelEdit()">Cancel</button>
            </form>
        </div>

        <!-- Payment Method -->
        <h3>Payment Method</h3>
        <form action="${pageContext.request.contextPath}/placeOrder" method="post" id="paymentForm">
            <!-- Include hidden address fields to send along with order -->
            <input type="hidden" name="phone" value="${user.phone}">
            <input type="hidden" name="street" value="${user.street}">
            <input type="hidden" name="village" value="${user.village}">
            <input type="hidden" name="city" value="${user.city}">
            <input type="hidden" name="landmark" value="${user.landmark}">
            <input type="hidden" name="pincode" value="${user.pincode}">

            <div>
                <input type="radio" id="cod" name="paymentMethod" value="Cash on Delivery" checked>
                <label for="cod">Cash on Delivery</label><br>
                <input type="radio" id="upi" name="paymentMethod" value="UPI">
                <label for="upi">UPI</label><br>
                <input type="radio" id="card" name="paymentMethod" value="Credit/Debit Card">
                <label for="card">Credit/Debit Card</label><br>
            </div>
            <button type="submit" class="btn" style="margin-top:20px;">Place Order</button>
        </form>
    </div>
    <script>
        function editAddress() {
            document.getElementById("addressDisplay").style.display = "none";
            document.getElementById("addressForm").style.display = "block";
            // Also update hidden fields when saving address? They will be saved via updateAddress, then page reloads.
            // But for immediate order, the hidden fields still have old values. To handle that we'll let the updateAddress redirect back to checkout.
        }
        function cancelEdit() {
            document.getElementById("addressForm").style.display = "none";
            document.getElementById("addressDisplay").style.display = "block";
        }
        // After the address form is submitted, it goes to /updateAddress which redirects back to checkout.
        // That will show the updated address in display and refresh the hidden inputs with the new values.
    </script>
    <%@ include file="footer.jsp" %>
</body>
</html>