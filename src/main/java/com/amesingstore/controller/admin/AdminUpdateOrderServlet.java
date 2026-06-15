package com.amesingstore.controller.admin;

import com.amesingstore.dao.OrderDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/admin/updateOrder")
public class AdminUpdateOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        OrderDAO dao = new OrderDAO();
        try {
            dao.updateOrderStatus(orderId, status);
            response.sendRedirect("orders");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}