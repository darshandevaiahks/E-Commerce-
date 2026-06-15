package com.amesingstore.dao;

import com.amesingstore.model.Review;
import com.amesingstore.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    public void addReview(Review review) throws SQLException {
        String sql = "INSERT INTO reviews (user_id, product_id, rating, comment) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getProductId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.executeUpdate();
        }
    }

    public List<Review> getReviewsByProduct(int productId) throws SQLException {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.*, u.fullname FROM reviews r JOIN users u ON r.user_id = u.id WHERE r.product_id=? ORDER BY r.review_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review rev = new Review();
                rev.setId(rs.getInt("id"));
                rev.setUserId(rs.getInt("user_id"));
                rev.setProductId(rs.getInt("product_id"));
                rev.setRating(rs.getInt("rating"));
                rev.setComment(rs.getString("comment"));
                rev.setReviewDate(rs.getTimestamp("review_date"));
                rev.setUserName(rs.getString("fullname"));
                list.add(rev);
            }
        }
        return list;
    }

    public boolean hasUserPurchasedProduct(int userId, int productId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM order_items oi JOIN orders o ON oi.order_id = o.id " +
                     "WHERE o.user_id=? AND oi.product_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }
}