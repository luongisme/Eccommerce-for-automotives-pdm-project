package com.DAO;

import com.DAO.Interface.shoppingcartDAO;
import com.model.ShoppingCart;
import com.Util.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class shoppingcartDAOimpl implements shoppingcartDAO {

    private ShoppingCart mapRow(ResultSet rs) throws SQLException {
        ShoppingCart cart = new ShoppingCart();
        cart.setCartID(rs.getString("cartID"));
        cart.setUserID(rs.getString("userID"));
        cart.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        return cart;
    }

    @Override
    public ShoppingCart findById(String cartID) {
        String sql = "SELECT * FROM shoppingcart WHERE CartID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cartID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public List<ShoppingCart> findByUserId(String userID) {
        String sql = "SELECT * FROM shoppingcart WHERE UserID = ?";
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    shoppingCarts.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return shoppingCarts;
    }

    @Override
    public boolean insert(ShoppingCart cart) {
        String sql = "INSERT INTO shoppingcart (CartID, UserID, CreatedAt) VALUES (?, ?, ?)";
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cart.getCartID());
            ps.setString(2, cart.getUserID());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(cart.getCreatedAt()));

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
    public boolean update(ShoppingCart cart) {
        String sql = "UPDATE shoppingcart SET CartID = ?, UserID = ?, CreatedAt = ? WHERE CartID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cart.getCartID());
            ps.setString(2, cart.getUserID());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(cart.getCreatedAt()));
            ps.setString(4, cart.getCartID());

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
    public boolean delete(String cartID) {
        String sql = "DELETE FROM shoppingcart WHERE CartID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cartID);

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