package com.DAO;

import com.DAO.Interface.orderitemDAO;
import com.model.OrderItem;
import com.Util.JdbcConnector;
import com.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class orderitemDAOimpl implements orderitemDAO {

    private OrderItem mapRow(ResultSet rs) throws SQLException {
        OrderItem oi = new OrderItem();
        oi.setOiID(rs.getString("oiID"));
        oi.setOrderID(rs.getString("orderID"));
        oi.setPid(rs.getString("pid"));
        oi.setQuantity(rs.getInt("quantity"));
        oi.setPriceAtPurchase(rs.getBigDecimal("priceAtPurchase")
        );
        return oi;
    }

    @Override
    public OrderItem findById(String oiID) {
        String sql = "SELECT * FROM User WHERE oiID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, oiID);
            try (ResultSet rs = ps.executeQuery()) {
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
    public List<OrderItem> findByOrderId(String orderID) {
        String sql = "SELECT * FROM OrderItem WHERE OrderID = ?";
        List<OrderItem> orderItem = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                orderItem.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return orderItem;
    }

    @Override
    public boolean insert(OrderItem item) {
        String sql = "INSERT INTO OrderItem (oiID, OrderID, Pid, Quantity, PriceAtPurchase) " +
                "VALUES(?,?,?,?,?)";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getOiID());
            ps.setString(2, item.getOrderID());
            ps.setString(3, item.getPid());
            ps.setInt(4, item.getQuantity());
            ps.setBigDecimal(5, item.getPriceAtPurchase());

            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return false;
    }

    @Override
    public boolean update(OrderItem item) {
        String sql = "UPDATE OrderItem SET OiID = ?, OrderID = ?, Pid = ?, Quantity = ?, PriceAtPurchase = ? " +
                "WHERE OrderID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getOiID());
            ps.setString(2, item.getOrderID());
            ps.setString(3, item.getPid());
            ps.setInt(4, item.getQuantity());
            ps.setBigDecimal(5, item.getPriceAtPurchase());
            ps.setString(6, item.getOrderID());

            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return false;
    }

    @Override
    public boolean delete(String oiID) {
        String sql = "DELETE FROM Address WHERE OiID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, oiID);

            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return false;
    }

    @Override
    public boolean deleteByOrderId(String orderID) {
        String sql = "DELETE FROM Address WHERE OrderID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, orderID);

            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return false;
    }
}
