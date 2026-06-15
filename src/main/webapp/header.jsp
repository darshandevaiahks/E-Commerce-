<%@ page import="com.amesingstore.model.User" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    User user = (User) session.getAttribute("user");
    String contextPath = request.getContextPath();
    String avatar = (user != null && user.getProfileImage() != null && !user.getProfileImage().isEmpty())
                    ? user.getProfileImage() : "default-avatar.png";
%>
<div class="header">
    <div class="logo"><a href="<%= contextPath %>/home">Amesing Store</a></div>
    <div class="search-bar">
        <form action="<%= contextPath %>/search" method="get">
            <input type="text" name="q" placeholder="Search products...">
            <button type="submit">🔍</button>
        </form>
    </div>
    <div class="nav-links">
        <a href="<%= contextPath %>/home">Home</a>
        <% if (user != null) { %>
            <a href="<%= contextPath %>/wishlist">Wishlist</a>
            <a href="<%= contextPath %>/cart">Cart</a>
            <a href="<%= contextPath %>/orderHistory">Orders</a>

            <!-- Avatar dropdown (click to open) -->
            <div class="user-avatar-dropdown" onclick="toggleDropdown(event)">
                <img src="<%= contextPath %>/images/<%= avatar %>" 
                     alt="Profile" class="user-avatar">
                <div class="dropdown-menu" id="userDropdown">
                    <a href="<%= contextPath %>/account">Account Settings</a>
                    <a href="<%= contextPath %>/logout">Logout</a>
                    <% if ("admin".equals(user.getRole())) { %>
                        <a href="<%= contextPath %>/admin/dashboard">Admin Dashboard</a>
                    <% } %>
                </div>
            </div>
        <% } else { %>
            <a href="<%= contextPath %>/login.jsp">Login</a>
            <a href="<%= contextPath %>/register.jsp">Register</a>
        <% } %>
    </div>
</div>

<script>
    function toggleDropdown(event) {
        event.stopPropagation();
        var menu = document.getElementById('userDropdown');
        menu.style.display = (menu.style.display === 'block') ? 'none' : 'block';
    }
    // Close dropdown when clicking outside
    document.addEventListener('click', function(e) {
        var menu = document.getElementById('userDropdown');
        if (menu) {
            var avatar = document.querySelector('.user-avatar-dropdown');
            if (avatar && !avatar.contains(e.target)) {
                menu.style.display = 'none';
            }
        }
    });
</script>