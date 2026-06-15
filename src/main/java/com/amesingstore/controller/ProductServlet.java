package com.amesingstore.controller;

import com.amesingstore.dao.ProductDAO;
import com.amesingstore.dao.ReviewDAO;
import com.amesingstore.dao.WishlistDAO;
import com.amesingstore.model.Product;
import com.amesingstore.model.Review;
import com.amesingstore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("home");
            return;
        }
        int id = Integer.parseInt(idParam);

        ProductDAO pdao = new ProductDAO();
        ReviewDAO rdao = new ReviewDAO();
        WishlistDAO wdao = new WishlistDAO();
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        try {
            Product product = pdao.getProductById(id);
            if (product == null) {
                response.sendRedirect("home");
                return;
            }

            // Reviews
            List<Review> reviews = rdao.getReviewsByProduct(id);

            // Wishlist status
            boolean inWishlist = false;
            if (user != null) {
                inWishlist = wdao.isInWishlist(user.getId(), id);
            }

            // Review permission (purchased?)
            boolean canReview = false;
            if (user != null) {
                canReview = rdao.hasUserPurchasedProduct(user.getId(), id);
            }

            // --- Suggested products ---
            // Fetch all products in the same category, then exclude the current one
            List<Product> suggested = new ArrayList<>();
            if (product.getCategoryId() > 0) {
                List<Product> sameCategory = pdao.getProductsByCategory(product.getCategoryId());
                for (Product p : sameCategory) {
                    if (p.getId() != product.getId()) {
                        suggested.add(p);
                    }
                }
            }
            // If not enough suggestions, add some from any category (optional)
            if (suggested.size() < 4) {
                List<Product> all = pdao.getAllProducts();
                for (Product p : all) {
                    if (p.getId() != product.getId() && !suggested.contains(p)) {
                        suggested.add(p);
                        if (suggested.size() >= 4) break;
                    }
                }
            }
            // Limit to 4 suggestions
            if (suggested.size() > 4) {
                suggested = suggested.subList(0, 4);
            }

            request.setAttribute("product", product);
            request.setAttribute("reviews", reviews);
            request.setAttribute("inWishlist", inWishlist);
            request.setAttribute("canReview", canReview);
            request.setAttribute("suggestedProducts", suggested);

            request.getRequestDispatcher("product.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}