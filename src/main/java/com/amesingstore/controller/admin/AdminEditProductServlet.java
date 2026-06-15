package com.amesingstore.controller.admin;

import com.amesingstore.dao.CategoryDAO;
import com.amesingstore.dao.ProductDAO;
import com.amesingstore.model.Category;
import com.amesingstore.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/editProduct")
public class AdminEditProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            ProductDAO pdao = new ProductDAO();
            try {
                Product p = pdao.getProductById(id);
                request.setAttribute("product", p);
            } catch (SQLException e) { e.printStackTrace(); }
        }
        CategoryDAO cdao = new CategoryDAO();
        try {
            List<Category> categories = cdao.getAllCategories();
            request.setAttribute("categories", categories);
        } catch (SQLException e) { e.printStackTrace(); }
        request.getRequestDispatcher("/admin/editProduct.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        Product p = new Product();
        p.setName(request.getParameter("name"));
        p.setDescription(request.getParameter("description"));
        p.setPrice(Double.parseDouble(request.getParameter("price")));
        p.setImage(request.getParameter("image"));
        p.setStock(Integer.parseInt(request.getParameter("stock")));
        p.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));

        ProductDAO dao = new ProductDAO();
        try {
            if (idParam == null || idParam.isEmpty()) {
                dao.addProduct(p);
            } else {
                p.setId(Integer.parseInt(idParam));
                dao.updateProduct(p);
            }
            response.sendRedirect("products");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}