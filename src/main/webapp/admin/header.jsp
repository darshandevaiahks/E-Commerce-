<%@ page import="com.amesingstore.model.User" %>

<%
    User user = (User) session.getAttribute("user");
    String contextPath = request.getContextPath();
    String avatar = (user != null && user.getProfileImage() != null && !user.getProfileImage().isEmpty())
                    ? user.getProfileImage() : "default-avatar.png";
%>
<div class="admin-bar">
    <div style="display:flex; align-items:center; gap:20px;">
   
        <a href="<%= contextPath %>/admin/dashboard" style="color:white; font-weight:bold; text-decoration:none;">Amesing Admin</a>
        <a href="<%= contextPath %>/admin/products" style="color:white; text-decoration:none;">Products</a>
        <a href="<%= contextPath %>/admin/orders" style="color:white; text-decoration:none;">Orders</a>
        <a href="<%= contextPath %>/admin/categories" style="color:white; text-decoration:none;">Categories</a>
        <a href="<%= contextPath %>/home" style="color:#FF7F50; text-decoration:none;">Store</a>
    </div>

    <div class="user-avatar-dropdown" onclick="toggleDropdown(event)">
        <img src="<%= contextPath %>/images/<%= avatar %>" 
             alt="Profile" class="user-avatar">
        <div class="dropdown-menu" id="adminDropdown">
            <a href="<%= contextPath %>/account">Account Settings</a>
            <a href="<%= contextPath %>/logout">Logout</a>
            <a href="<%= contextPath %>/admin/dashboard">Admin Dashboard</a>
        </div>
    </div>
</div>

<script>
    function toggleDropdown(event) {
        event.stopPropagation();
        var menu = document.getElementById('adminDropdown');
        menu.style.display = (menu.style.display === 'block') ? 'none' : 'block';
    }
    document.addEventListener('click', function(e) {
        var menu = document.getElementById('adminDropdown');
        if (menu) {
            var avatar = document.querySelector('.admin-bar .user-avatar-dropdown');
            if (avatar && !avatar.contains(e.target)) {
                menu.style.display = 'none';
            }
        }
    });
</script>