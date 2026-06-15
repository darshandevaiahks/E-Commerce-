package com.amesingstore.controller;

import com.amesingstore.dao.CartDAO;
import com.amesingstore.dao.OrderDAO;
import com.amesingstore.dao.UserDAO;
import com.amesingstore.model.CartItem;
import com.amesingstore.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/placeOrder")
public class PlaceOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        String paymentMethod = request.getParameter("paymentMethod");
        // Build shipping address string from form inputs (they will be submitted with the address fields even if unchanged)
        // The checkout.jsp will have hidden/editable fields for address that are submitted with placeOrder.
        // We'll read them here to get the possibly updated address.
        String phone = request.getParameter("phone");
        String street = request.getParameter("street");
        String village = request.getParameter("village");
        String city = request.getParameter("city");
        String landmark = request.getParameter("landmark");
        String pincode = request.getParameter("pincode");

        // Also update the user's address in DB if the user edited it (optional, but we'll do it for consistency)
        user.setPhone(phone);
        user.setStreet(street);
        user.setVillage(village);
        user.setCity(city);
        user.setLandmark(landmark);
        user.setPincode(pincode);

        // Update user address in DB
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.updateAddress(user);
        } catch (SQLException e) {
            e.printStackTrace();
            // continue anyway? Maybe show error.
        }

        // Build address string for order snapshot
        String shippingAddress = user.getFullname() + ", " + phone + ", " +
                street + ", " + village + ", " + city + ", " + landmark + ", " + pincode;

        CartDAO cdao = new CartDAO();
        OrderDAO odao = new OrderDAO();
        try {
            List<CartItem> cartItems = cdao.getCartItems(user.getId());
            if (cartItems.isEmpty()) {
                response.sendRedirect("cart");
                return;
            }
            int orderId = odao.placeOrder(user.getId(), cartItems, paymentMethod, shippingAddress);
            response.sendRedirect("orderConfirmation.jsp?orderId=" + orderId);
        } catch (SQLException e) {
            request.setAttribute("error", "Order failed: " + e.getMessage());
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
    }
}