package com.amesingstore.dao;


import com.amesingstore.model.Category;
import com.amesingstore.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
	public List<Category> getAllCategories() throws SQLException {
	    List<Category> list = new ArrayList<>();
	    String sql = "SELECT id, name, image FROM categories";
	    try (Connection con = DBConnection.getConnection();
	         Statement st = con.createStatement();
	         ResultSet rs = st.executeQuery(sql)) {
	        while (rs.next()) {
	            Category c = new Category();
	            c.setId(rs.getInt("id"));
	            c.setName(rs.getString("name"));
	            c.setImage(rs.getString("image"));
	            list.add(c);
	        }
	    }
	    return list;
	}

    public void addCategory(String name, String image) throws SQLException {
        String sql = "INSERT INTO categories (name, image) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, image);
            ps.executeUpdate();
        }
    }

    public void deleteCategory(int id) throws SQLException {
        String sql = "DELETE FROM categories WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}