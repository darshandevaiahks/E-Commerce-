package com.amesingstore.controller;

import com.amesingstore.dao.CartDAO;
import com.amesingstore.dao.ProductDAO;
import com.amesingstore.model.CartItem;
import com.amesingstore.model.Product;
import com.amesingstore.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        CartDAO cdao = new CartDAO();
        try {
            List<CartItem> items = cdao.getCartItems(user.getId());
            if (items.isEmpty()) {
                response.sendRedirect("cart");
                return;
            }
            // Re-check stock
            ProductDAO pdao = new ProductDAO();
            for (CartItem item : items) {
                Product p = pdao.getProductById(item.getProductId());
                if (p == null || p.getStock() < item.getQuantity()) {
                    request.setAttribute("error", "Some items are no longer available.");
                    request.setAttribute("cartItems", items);
                    double total = 0;
                    for (CartItem it : items) total += it.getPrice() * it.getQuantity();
                    request.setAttribute("total", total);
                    request.getRequestDispatcher("cart.jsp").forward(request, response);
                    return;
                }
            }
            double total = 0;
            for (CartItem it : items) total += it.getPrice() * it.getQuantity();
            request.setAttribute("cartItems", items);
            request.setAttribute("total", total);
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}