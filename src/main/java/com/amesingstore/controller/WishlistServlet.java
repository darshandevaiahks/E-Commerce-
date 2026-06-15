package com.amesingstore.controller;

import com.amesingstore.dao.WishlistDAO;
import com.amesingstore.model.User;
import com.amesingstore.model.WishlistItem;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        WishlistDAO dao = new WishlistDAO();
        try {
            List<WishlistItem> items = dao.getWishlistByUser(user.getId());
            request.setAttribute("wishlistItems", items);
            request.getRequestDispatcher("wishlist.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}