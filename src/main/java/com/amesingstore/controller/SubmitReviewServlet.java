package com.amesingstore.controller;

import com.amesingstore.dao.ReviewDAO;
import com.amesingstore.model.Review;
import com.amesingstore.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/submitReview")
public class SubmitReviewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        int productId = Integer.parseInt(request.getParameter("productId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        ReviewDAO rdao = new ReviewDAO();
        try {
            if (!rdao.hasUserPurchasedProduct(user.getId(), productId)) {
                request.setAttribute("error", "You can only review products you have purchased.");
                request.getRequestDispatcher("product?id=" + productId).forward(request, response);
                return;
            }
            Review rev = new Review();
            rev.setUserId(user.getId());
            rev.setProductId(productId);
            rev.setRating(rating);
            rev.setComment(comment);
            rdao.addReview(rev);
            response.sendRedirect("product?id=" + productId);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}