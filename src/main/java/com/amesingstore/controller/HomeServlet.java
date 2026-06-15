package com.amesingstore.controller;

import com.amesingstore.dao.*;
import com.amesingstore.model.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // same code as before
        String catParam = request.getParameter("category");
        ProductDAO pdao = new ProductDAO();
        CategoryDAO cdao = new CategoryDAO();
        try {
            List<Product> products;
            if (catParam != null && !catParam.isEmpty()) {
                products = pdao.getProductsByCategory(Integer.parseInt(catParam));
            } else {
                products = pdao.getAllProducts();
            }
            List<Category> categories = cdao.getAllCategories();
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}