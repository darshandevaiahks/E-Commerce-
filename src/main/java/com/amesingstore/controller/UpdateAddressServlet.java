package com.amesingstore.controller;

import com.amesingstore.dao.UserDAO;
import com.amesingstore.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/updateAddress")
public class UpdateAddressServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        // update fields from form
        user.setPhone(request.getParameter("phone"));
        user.setStreet(request.getParameter("street"));
        user.setVillage(request.getParameter("village"));
        user.setCity(request.getParameter("city"));
        user.setLandmark(request.getParameter("landmark"));
        user.setPincode(request.getParameter("pincode"));
        UserDAO dao = new UserDAO();
        try {
            dao.updateAddress(user);
            // update session object with new address
            session.setAttribute("user", user);
            response.sendRedirect("checkout");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}