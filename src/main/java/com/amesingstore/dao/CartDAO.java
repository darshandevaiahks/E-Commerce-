package com.amesingstore.dao;

import com.amesingstore.model.CartItem;
import com.amesingstore.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public void addToCart(int userId, int productId, int qty) throws SQLException {
        String check = "SELECT id, quantity FROM cart WHERE user_id=? AND product_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(check)) {
            ps.setInt(1, userId); ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int newQty = rs.getInt("quantity") + qty;
                String upd = "UPDATE cart SET quantity=? WHERE id=?";
                try (PreparedStatement p = con.prepareStatement(upd)) {
                    p.setInt(1, newQty); p.setInt(2, rs.getInt("id"));
                    p.executeUpdate();
                }
            } else {
                String ins = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
                try (PreparedStatement p = con.prepareStatement(ins)) {
                    p.setInt(1, userId); p.setInt(2, productId); p.setInt(3, qty);
                    p.executeUpdate();
                }
            }
        }
    }

    public List<CartItem> getCartItems(int userId) throws SQLException {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT c.id, c.product_id, c.quantity, p.name, p.price " +
                     "FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("id"));
                item.setUserId(userId);
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setProductName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                list.add(item);
            }
        }
        return list;
    }

    public void updateQuantity(int cartId, int qty) throws SQLException {
        String sql = "UPDATE cart SET quantity=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, qty); ps.setInt(2, cartId);
            ps.executeUpdate();
        }
    }

    public void removeItem(int cartId) throws SQLException {
        String sql = "DELETE FROM cart WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
        }
    }

    public void clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}