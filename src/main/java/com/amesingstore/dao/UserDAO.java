package com.amesingstore.dao;

import com.amesingstore.model.User;
import com.amesingstore.util.DBConnection;
import java.sql.*;

public class UserDAO {

    // -----------------------------------------------------------
    //  VALIDATE USER (Login)
    // -----------------------------------------------------------
    public User validate(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                // Address fields
                user.setPhone(rs.getString("phone"));
                user.setStreet(rs.getString("street"));
                user.setVillage(rs.getString("village"));
                user.setCity(rs.getString("city"));
                user.setLandmark(rs.getString("landmark"));
                user.setPincode(rs.getString("pincode"));
                user.setProfileImage(rs.getString("profile_image"));
                return user;
            }
        }
        return null;
    }
    
    
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                // map all fields (same as validate)
                user.setId(rs.getInt("id"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getString("phone"));
                user.setStreet(rs.getString("street"));
                user.setVillage(rs.getString("village"));
                user.setCity(rs.getString("city"));
                user.setLandmark(rs.getString("landmark"));
                user.setPincode(rs.getString("pincode"));
                user.setProfileImage(rs.getString("profile_image"));
                return user;
            }
        }
        return null;
    }
    
    public void updateProfile(User user) throws SQLException {
        String sql = "UPDATE users SET fullname=?, phone=?, street=?, village=?, city=?, landmark=?, pincode=?, profile_image=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getStreet());
            ps.setString(4, user.getVillage());
            ps.setString(5, user.getCity());
            ps.setString(6, user.getLandmark());
            ps.setString(7, user.getPincode());
            ps.setString(8, user.getProfileImage());
            ps.setInt(9, user.getId());
            ps.executeUpdate();
        }
    }

    // -----------------------------------------------------------
    //  REGISTER NEW USER
    // -----------------------------------------------------------
    public boolean register(User user) throws SQLException {
        String sql = "INSERT INTO users (fullname, email, password, phone, street, village, city, landmark, pincode, profile_image) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getStreet());
            ps.setString(6, user.getVillage());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getLandmark());
            ps.setString(9, user.getPincode());
            ps.setString(10, user.getProfileImage());
            return ps.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        }
    }

    // -----------------------------------------------------------
    //  UPDATE USER ADDRESS (used in checkout)
    // -----------------------------------------------------------
    public void updateAddress(User user) throws SQLException {
        String sql = "UPDATE users SET phone=?, street=?, village=?, city=?, landmark=?, pincode=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getPhone());
            ps.setString(2, user.getStreet());
            ps.setString(3, user.getVillage());
            ps.setString(4, user.getCity());
            ps.setString(5, user.getLandmark());
            ps.setString(6, user.getPincode());
            ps.setInt(7, user.getId());
            ps.executeUpdate();
        }
    }
}