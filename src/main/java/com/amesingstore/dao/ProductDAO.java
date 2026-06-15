package com.amesingstore.dao;

import com.amesingstore.model.Product;
import com.amesingstore.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // -----------------------------------------------------------
    //  GET ALL PRODUCTS
    // -----------------------------------------------------------
    public List<Product> getAllProducts() throws SQLException {
        return getProductsByCategory(0); // 0 = all categories
    }

    // -----------------------------------------------------------
    //  GET PRODUCTS BY CATEGORY (0 = all)
    // -----------------------------------------------------------
    public List<Product> getProductsByCategory(int categoryId) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name as category_name FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.id";
        if (categoryId > 0) {
            sql += " WHERE p.category_id = ?";
        }
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (categoryId > 0) {
                ps.setInt(1, categoryId);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        }
        return list;
    }

    // -----------------------------------------------------------
    //  SEARCH PRODUCTS BY NAME
    // -----------------------------------------------------------
    public List<Product> searchProducts(String keyword) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name as category_name FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.id " +
                     "WHERE p.name LIKE ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        }
        return list;
    }

    // -----------------------------------------------------------
    //  GET PRODUCT BY ID
    // -----------------------------------------------------------
    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT p.*, c.name as category_name FROM products p " +
                     "LEFT JOIN categories c ON p.category_id = c.id WHERE p.id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapProduct(rs);
            }
        }
        return null;
    }

    // -----------------------------------------------------------
    //  ADD NEW PRODUCT (admin)
    // -----------------------------------------------------------
    public void addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products (name, description, price, original_price, image, stock, category_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setDouble(4, p.getOriginalPrice());
            ps.setString(5, p.getImage());
            ps.setInt(6, p.getStock());
            ps.setInt(7, p.getCategoryId());
            ps.executeUpdate();
        }
    }

    // -----------------------------------------------------------
    //  UPDATE PRODUCT (admin)
    // -----------------------------------------------------------
    public void updateProduct(Product p) throws SQLException {
        String sql = "UPDATE products SET name=?, description=?, price=?, original_price=?, " +
                     "image=?, stock=?, category_id=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setDouble(4, p.getOriginalPrice());
            ps.setString(5, p.getImage());
            ps.setInt(6, p.getStock());
            ps.setInt(7, p.getCategoryId());
            ps.setInt(8, p.getId());
            ps.executeUpdate();
        }
    }

    // -----------------------------------------------------------
    //  DELETE PRODUCT
    // -----------------------------------------------------------
    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // -----------------------------------------------------------
    //  UPDATE STOCK (generic)
    // -----------------------------------------------------------
    public boolean updateStock(int productId, int newStock) throws SQLException {
        String sql = "UPDATE products SET stock=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        }
    }

    // -----------------------------------------------------------
    //  REDUCE STOCK (used when placing order)
    // -----------------------------------------------------------
    public void reduceStock(int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET stock = stock - ? WHERE id=? AND stock >= ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Insufficient stock for product ID " + productId);
            }
        }
    }

    // -----------------------------------------------------------
    //  PRIVATE METHOD: map a ResultSet row to a Product object
    // -----------------------------------------------------------
    private Product mapProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setOriginalPrice(rs.getDouble("original_price")); // 0.0 if null
        p.setImage(rs.getString("image"));
        p.setStock(rs.getInt("stock"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setCategoryName(rs.getString("category_name"));
        return p;
    }
}