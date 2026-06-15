package com.amesingstore.controller.admin;

import com.amesingstore.dao.CategoryDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/deleteCategory")
public class AdminDeleteCategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CategoryDAO dao = new CategoryDAO();
        try {
            dao.deleteCategory(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("categories");
    }
}