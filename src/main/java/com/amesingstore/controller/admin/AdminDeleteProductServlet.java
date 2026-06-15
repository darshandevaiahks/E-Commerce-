package com.amesingstore.controller.admin;

import com.amesingstore.dao.ProductDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/deleteProduct")
public class AdminDeleteProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductDAO dao = new ProductDAO();
        try {
            dao.deleteProduct(id);
        } catch (SQLException e) { e.printStackTrace(); }
        response.sendRedirect("products");
    }
}