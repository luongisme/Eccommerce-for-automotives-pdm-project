package com.DAO;

import com.DAO.Interface.cartitemDAO;
import com.model.CartItem;
import com.Util.JdbcConnector;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class cartItemDAOimpl implements cartitemDAO {

    private CartItem mapRow(ResultSet rs) throws SQLException {
        CartItem ci = new CartItem();
        ci.setCiID(rs.getString("CiID"));
        ci.setCartID(rs.getString("CartID"));
        ci.setPid(rs.getString("PID"));
        ci.setQuantity(rs.getInt("Quantity"));
        return ci;
    }

    @Override
    public CartItem findById(String ciID) {
        String sql = "SELECT * FROM cartitem WHERE ciID = ?";
        try (Connection conn = JdbcConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ciID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public List<CartItem> findByCartId(String cartID) {
        String sql = "SELECT * FROM cartitem WHERE cartID = ?";
        ArrayList<CartItem> cartitems = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cartID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cartitems.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return cartitems;
    }

    @Override
    public boolean insert(CartItem item) {
        String sql = "INSERT INTO cartitem (CiID, CartID, Pid, Quantity)"
                + "VALUES (?,?,?,?)";

        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getCiID());
            ps.setString(2, item.getCartID());
            ps.setString(3, item.getPid());
            ps.setInt(4, item.getQuantity());

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
    public boolean update(CartItem item) {
        String sql = "UPDATE CartItem SET CiID = ?, CartID = ?, PID = ?, Quantity = ? WHERE CiID = ?";
        try (Connection conn = JdbcConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getCiID());
            ps.setString(2, item.getCartID());
            ps.setString(3, item.getPid());
            ps.setInt(4, item.getQuantity());
            ps.setString(5, item.getCiID());

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
    public boolean delete(String ciID) {
        String sql = "DELETE FROM cartitem WHERE ciID = ?";
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ciID);

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
    public boolean deleteByCartId(String cartID) {
        String sql = "DELETE FROM cartitem WHERE cartID = ?";
        try(Connection conn = JdbcConnector.connect();
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