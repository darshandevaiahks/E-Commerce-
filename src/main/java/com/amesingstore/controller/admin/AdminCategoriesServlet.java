package com.amesingstore.controller.admin;

import com.amesingstore.dao.CategoryDAO;
import com.amesingstore.model.Category;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/categories")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1 MB
    maxFileSize = 1024 * 1024 * 5,     // 5 MB
    maxRequestSize = 1024 * 1024 * 10  // 10 MB
)
public class AdminCategoriesServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "images";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ===== DELETE CATEGORY =====
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            CategoryDAO dao = new CategoryDAO();
            try {
                dao.deleteCategory(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect("categories");
            return;
        }

        // ===== NORMAL LIST =====
        CategoryDAO dao = new CategoryDAO();
        try {
            List<Category> categories = dao.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/categories.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        Part filePart = request.getPart("image");

        String fileName = "";
        if (filePart != null && filePart.getSize() > 0) {
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + File.separator + UPLOAD_DIR;
            File uploadDir = new File(savePath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            filePart.write(savePath + File.separator + fileName);
        }

        CategoryDAO dao = new CategoryDAO();
        try {
            dao.addCategory(name, fileName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("categories");
    }
}