package com.amesingstore.controller;

import com.amesingstore.dao.WishlistDAO;
import com.amesingstore.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/wishlist/remove")
public class RemoveFromWishlistServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        int productId = Integer.parseInt(request.getParameter("productId"));
        try {
            new WishlistDAO().removeFromWishlist(user.getId(), productId);
        } catch (Exception e) { e.printStackTrace(); }
        response.sendRedirect(request.getHeader("Referer") != null ? request.getHeader("Referer") : "wishlist");
    }
}