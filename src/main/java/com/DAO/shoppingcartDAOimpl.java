package com.DAO;

import com.DAO.Interface.shoppingcartDAO;
import com.model.Address;
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
        return cart;
    }

    @Override
    public ShoppingCart findById(String cartID) {
        String sql = "SELECT * FROM address WHERE CartID = ?";
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
        String sql = "SELECT * FROM address WHERE UserID = ?";
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
        String sql = "INSERT INTO address (CartID, UserID) VALUES (?, ?)";
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cart.getCartID());
            ps.setString(2, cart.getUserID());

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
        String sql = "UPDATE address SET CartID = ?, UserID = ? WHERE CartID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cart.getCartID());
            ps.setString(2, cart.getUserID());
            ps.setString(3, cart.getCartID());

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
        String sql = "DELETE FROM Address WHERE CartID = ?";
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
