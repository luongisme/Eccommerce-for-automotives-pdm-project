package com.DAO;

import com.DAO.Interface.orderDAO;
import com.model.Order;
import com.Util.JdbcConnector;


import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class orderDAOimpl implements orderDAO {

    private Order mapRow(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderID(rs.getString("OrderID"));
        order.setOrderDate(rs.getTimestamp("OrderDate").toLocalDateTime());
        order.setTotalAmount(rs.getBigDecimal("TotalAmount"));
        order.setOrderStatus(rs.getString("OrderStatus"));
        order.setUserID(rs.getString("UserID"));
        order.setPaymentID(rs.getString("PaymentID"));
        return order;
    }

    @Override
    public Order findById(String orderID) {
        String sql = "SELECT * FROM orders WHERE OrderID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, orderID);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Order> findByUserId(String userID) {
        String sql = "SELECT * FROM orders WHERE UserID = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                orders.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public boolean insert(Order order) {
        String sql = "INSERT INTO orders (OrderID, OrderDate, TotalAmount, OrderStatus, UserID, PaymentID)" +
                " VALUES(?,?,?,?,?,?)";
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, order.getOrderID());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(order.getOrderDate()));
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.setString(4, order.getOrderStatus());
            ps.setString(5, order.getUserID());
            ps.setString(6, order.getPaymentID());

            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean update(Order order) {
        String sql = "UPDATE orders SET OrderDate = ?, TotalAmount = ?, OrderStatus = ?, " +
                "UserID = ?, PaymentID = ? WHERE OrderID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, java.sql.Timestamp.valueOf(order.getOrderDate()));
            ps.setBigDecimal(2, order.getTotalAmount());
            ps.setString(3, order.getOrderStatus());
            ps.setString(4, order.getUserID());
            ps.setString(5, order.getPaymentID());
            ps.setString(6, order.getOrderID());

            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    @Override
    public boolean delete(String orderID) {
        String sql = "DELETE FROM orders WHERE OrderID = ?";
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, orderID);

            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
