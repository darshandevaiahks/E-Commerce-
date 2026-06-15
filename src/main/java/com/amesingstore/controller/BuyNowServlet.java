package com.amesingstore.controller;

import com.amesingstore.dao.CartDAO;
import com.amesingstore.dao.ProductDAO;
import com.amesingstore.model.Product;
import com.amesingstore.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/buyNow")
public class BuyNowServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int productId = Integer.parseInt(request.getParameter("productId"));

        ProductDAO pdao = new ProductDAO();
        try {
            Product p = pdao.getProductById(productId);
            if (p == null || p.getStock() < 1) {
                session.setAttribute("cartError", "Product is out of stock.");
                response.sendRedirect("product?id=" + productId);
                return;
            }

            CartDAO cdao = new CartDAO();
            cdao.addToCart(user.getId(), productId, 1);  // adds 1 to cart
            response.sendRedirect("checkout");            // straight to checkout
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}