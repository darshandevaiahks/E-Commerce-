package com.amesingstore.dao;

import com.amesingstore.model.WishlistItem;
import com.amesingstore.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {
    public void addToWishlist(int userId, int productId) throws SQLException {
        String sql = "INSERT IGNORE INTO wishlist (user_id, product_id) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }

    public void removeFromWishlist(int userId, int productId) throws SQLException {
        String sql = "DELETE FROM wishlist WHERE user_id=? AND product_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }

    public List<WishlistItem> getWishlistByUser(int userId) throws SQLException {
        List<WishlistItem> list = new ArrayList<>();
        String sql = "SELECT w.id, w.product_id, p.name, p.price, p.image FROM wishlist w " +
                     "JOIN products p ON w.product_id = p.id WHERE w.user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WishlistItem item = new WishlistItem();
                item.setId(rs.getInt("id"));
                item.setUserId(userId);
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setImage(rs.getString("image"));
                list.add(item);
            }
        }
        return list;
    }

    public boolean isInWishlist(int userId, int productId) throws SQLException {
        String sql = "SELECT 1 FROM wishlist WHERE user_id=? AND product_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}