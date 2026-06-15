package com.amesingstore.controller;

import com.amesingstore.dao.UserDAO;
import com.amesingstore.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet("/account")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1 MB
    maxFileSize = 1024 * 1024 * 5,     // 5 MB
    maxRequestSize = 1024 * 1024 * 10  // 10 MB
)
public class AccountSettingsServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "images";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        // The current user is in session; forward to JSP
        request.getRequestDispatcher("account.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Get form fields
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String street = request.getParameter("street");
        String village = request.getParameter("village");
        String city = request.getParameter("city");
        String landmark = request.getParameter("landmark");
        String pincode = request.getParameter("pincode");

        // Profile image file
        Part filePart = request.getPart("profileImage");
        String fileName = user.getProfileImage(); // keep old one if no new file
        if (filePart != null && filePart.getSize() > 0) {
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + File.separator + UPLOAD_DIR;
            File uploadDir = new File(savePath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            filePart.write(savePath + File.separator + fileName);
        }

        // Update user object
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setStreet(street);
        user.setVillage(village);
        user.setCity(city);
        user.setLandmark(landmark);
        user.setPincode(pincode);
        user.setProfileImage(fileName);

        UserDAO dao = new UserDAO();
        try {
            dao.updateProfile(user);
            // Reload user from DB to update session
            User updatedUser = dao.getUserById(user.getId());
            session.setAttribute("user", updatedUser);
            request.setAttribute("success", "Profile updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error. Please try again.");
        }

        request.getRequestDispatcher("account.jsp").forward(request, response);
    }
}