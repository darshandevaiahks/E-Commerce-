package com.amesingstore.dao;

import com.amesingstore.model.*;
import com.amesingstore.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

	public int placeOrder(int userId, List<CartItem> cartItems, String paymentMethod, String shippingAddress) throws SQLException {
	    Connection con = null;
	    try {
	        con = DBConnection.getConnection();
	        con.setAutoCommit(false);

	        double total = 0;
	        for (CartItem item : cartItems) {
	            total += item.getPrice() * item.getQuantity();
	        }

	        // insert order with shipping address and payment method
	        String orderSql = "INSERT INTO orders (user_id, total, status, payment_method, shipping_address) VALUES (?, ?, 'Pending', ?, ?)";
	        int orderId;
	        try (PreparedStatement ps = con.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
	            ps.setInt(1, userId);
	            ps.setDouble(2, total);
	            ps.setString(3, paymentMethod);
	            ps.setString(4, shippingAddress);
	            ps.executeUpdate();
	            ResultSet rs = ps.getGeneratedKeys();
	            rs.next();
	            orderId = rs.getInt(1);
	        }

	        // insert order items + reduce stock
	        String itemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?,?,?,?)";
	        ProductDAO pdao = new ProductDAO();
	        try (PreparedStatement ps = con.prepareStatement(itemSql)) {
	            for (CartItem item : cartItems) {
	                ps.setInt(1, orderId);
	                ps.setInt(2, item.getProductId());
	                ps.setInt(3, item.getQuantity());
	                ps.setDouble(4, item.getPrice());
	                ps.addBatch();

	                pdao.reduceStock(item.getProductId(), item.getQuantity());
	            }
	            ps.executeBatch();
	        }

	        // clear cart
	        CartDAO cdao = new CartDAO();
	        cdao.clearCart(userId);

	        con.commit();
	        return orderId;
	    } catch (SQLException e) {
	        if (con != null) con.rollback();
	        throw e;
	    } finally {
	        if (con != null) con.setAutoCommit(true);
	    }
	}

    public List<Order> getOrdersByUser(int userId) throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id=? ORDER BY order_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setShippingAddress(rs.getString("shipping_address"));
                list.add(order);
            }
        }
        return list;
    }

    public Order getOrderById(int orderId) throws SQLException {
        Order order = null;
        String sql = "SELECT * FROM orders WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setShippingAddress(rs.getString("shipping_address"));
            }
        }
        if (order != null) {
            List<OrderItem> items = new ArrayList<>();
            String itemSql = "SELECT oi.*, p.name as product_name FROM order_items oi " +
                             "JOIN products p ON oi.product_id = p.id WHERE oi.order_id=?";
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(itemSql)) {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    item.setProductName(rs.getString("product_name"));
                    items.add(item);
                }
            }
            order.setItems(items);
        }
        return order;
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.fullname AS customer_name FROM orders o " +
                     "JOIN users u ON o.user_id = u.id ORDER BY o.order_date DESC";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(rs.getString("status"));
                order.setPaymentMethod(rs.getString("payment_method"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setCustomerName(rs.getString("customer_name"));   // new
                list.add(order);
            }
        }
        return list;
    }
}