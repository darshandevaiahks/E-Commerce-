package com.amesingstore.controller.admin;

import com.amesingstore.dao.OrderDAO;
import com.amesingstore.dao.ProductDAO;
import com.amesingstore.model.Order;
import com.amesingstore.model.Product;
import com.amesingstore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        request.setAttribute("adminUser", user);

        try {
            OrderDAO odao = new OrderDAO();
            ProductDAO pdao = new ProductDAO();

            // 1. Fetch all orders and all products (full lists)
            List<Order> allOrders = odao.getAllOrders();
            List<Product> allProducts = pdao.getAllProducts();

            // 2. Totals
            int totalOrders = allOrders.size();
            int totalProducts = allProducts.size();

            // 3. Pending shipments count (from full list)
            int pendingCount = 0;
            for (Order ord : allOrders) {
                if ("Pending".equals(ord.getStatus())) {
                    pendingCount++;
                }
            }

            // 4. Recent orders / products for the tables (last 5)
            int recentCount = Math.min(5, totalOrders);
            List<Order> recentOrders = allOrders.subList(0, recentCount);

            // Sort products by id descending for recent products
            allProducts.sort((p1, p2) -> Integer.compare(p2.getId(), p1.getId()));
            int recentProdCount = Math.min(5, allProducts.size());
            List<Product> recentProducts = allProducts.subList(0, recentProdCount);

            // 5. Set attributes
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("pendingCount", pendingCount);
            request.setAttribute("recentOrders", recentOrders);
            request.setAttribute("recentProducts", recentProducts);

        } catch (SQLException e) {
            e.printStackTrace();
            // even on error, set empty values to avoid null pointers
            request.setAttribute("totalOrders", 0);
            request.setAttribute("totalProducts", 0);
            request.setAttribute("pendingCount", 0);
        }

        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}