package com.DAO;

import com.DAO.Interface.addressDAO;
import com.model.Address;
import com.Util.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class addressDAOimpl implements addressDAO {

    private Address mapRow(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setAid(rs.getString("AID"));
        address.setStreet(rs.getString("Street"));
        address.setCity(rs.getString("City"));
        address.setPostalCode(rs.getString("Postal_Code"));
        address.setCountry(rs.getString("Country"));
        address.setDefaultShipping(rs.getBoolean("isDefaultShipping"));
        address.setUserID(rs.getString("UserID"));
        return address;
    }

    @Override
    public Address findById(String aid) {
        String sql = "SELECT * FROM address WHERE AID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, aid);
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
    public List<Address> findByUserId(String userID) {
        String sql = "SELECT * FROM address WHERE UserID = ?";
        List<Address> addressList = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ) {

            ps.setString(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    addressList.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return addressList;
    }

    @Override
    public List<Address> findAll() {
        String sql = "SELECT * FROM address";
        List<Address> addressList = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                addressList.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return addressList;
    }

    @Override
    public boolean insert(Address address) {
        String sql = "INSERT INTO Address (AID, Street, City, Postal_Code, Country, isDefaultShipping, UserID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JdbcConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, address.getAid());
            ps.setString(2, address.getStreet());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getPostalCode());
            ps.setString(5, address.getCountry());
            ps.setBoolean(6, address.isDefaultShipping());
            ps.setString(7, address.getUserID());

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
    public boolean update(Address address) {
        String sql = "UPDATE Address SET Street = ?, City = ?,  " +
                     "Postal_Code = ?, Country = ?, isDefaultShipping = ?, UserID = ? " +
                     "WHERE AID = ?";
        try (Connection conn = JdbcConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, address.getStreet());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getPostalCode());
            ps.setString(4, address.getCountry());
            ps.setBoolean(5, address.isDefaultShipping());
            ps.setString(6, address.getUserID());
            ps.setString(7, address.getAid());

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
    public boolean delete(String aid) {
        String sql = "DELETE FROM Address WHERE AID = ?";
        try (Connection conn = JdbcConnector.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, aid);

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