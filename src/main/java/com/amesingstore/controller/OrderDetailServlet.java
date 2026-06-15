package com.amesingstore.controller;

import com.amesingstore.dao.OrderDAO;
import com.amesingstore.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/orderDetail")
public class OrderDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        OrderDAO dao = new OrderDAO();
        try {
            Order order = dao.getOrderById(orderId);
            if (order != null) {
                request.setAttribute("order", order);
                request.getRequestDispatcher("orderDetail.jsp").forward(request, response);
            } else {
                response.sendRedirect("orderHistory");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}