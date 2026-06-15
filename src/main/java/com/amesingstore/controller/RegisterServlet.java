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

@WebServlet("/register")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1 MB
    maxFileSize = 1024 * 1024 * 5,     // 5 MB
    maxRequestSize = 1024 * 1024 * 10  // 10 MB
)
public class RegisterServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "images";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String street = request.getParameter("street");
        String village = request.getParameter("village");
        String city = request.getParameter("city");
        String landmark = request.getParameter("landmark");
        String pincode = request.getParameter("pincode");

        // --- Handle profile image upload ---
        Part filePart = request.getPart("profileImage");
        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + File.separator + UPLOAD_DIR;
            File uploadDir = new File(savePath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            filePart.write(savePath + File.separator + fileName);
        }

        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setStreet(street);
        user.setVillage(village);
        user.setCity(city);
        user.setLandmark(landmark);
        user.setPincode(pincode);
        user.setProfileImage(fileName);   // can be null => DAO handles it

        UserDAO dao = new UserDAO();
        try {
            boolean success = dao.register(user);
            if (success) {
                response.sendRedirect("login.jsp?registered=true");
            } else {
                request.setAttribute("error", "Registration failed. Email may already exist.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}