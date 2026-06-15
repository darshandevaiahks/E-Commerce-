package com.amesingstore.controller;

import com.amesingstore.dao.ProductDAO;
import com.amesingstore.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String q = request.getParameter("q");
        ProductDAO dao = new ProductDAO();
        try {
            List<Product> products = dao.searchProducts(q);
            request.setAttribute("products", products);
            request.setAttribute("keyword", q);
            request.getRequestDispatcher("searchResults.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}