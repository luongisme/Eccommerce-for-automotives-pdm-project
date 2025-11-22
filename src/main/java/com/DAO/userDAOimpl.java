package com.DAO;

import com.DAO.Interface.userDAO;
import com.model.User;
import com.Util.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class userDAOimpl implements userDAO {

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getString("UserID"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setFirstName(rs.getString("FirstName"));
        user.setMidName(rs.getString("MidName"));
        user.setLastName(rs.getString("LastName"));
        // Giả sử cột trong DB là DateCreated kiểu TIMESTAMP
        user.setDateCreated(rs.getTimestamp("DateCreated").toLocalDateTime());
        user.setRole(User.UserRole.valueOf(rs.getString("Role")));
        return user;
    }

    @Override
    public User findById(String userID) {
        String sql = "SELECT * FROM User WHERE UserID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
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
    public User findByEmail(String email) {
        String sql = "SELECT * FROM User WHERE Email = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
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
    public List<User> findAll() {
        String sql = "SELECT * FROM User";
        List<User> users = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public boolean insert(User user) {
        String sql = "INSERT INTO User (UserID, Email, LastName, MidName, FirstName, DateCreated, Password, Role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserID());                              // UserID
            ps.setString(2, user.getEmail());                               // Email
            ps.setString(3, user.getLastName());                            // LastName
            ps.setString(4, user.getMidName());                             // MidName
            ps.setString(5, user.getFirstName());                           // FirstName
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(user.getDateCreated())); // DateCreated
            ps.setString(7, user.getPassword());                            // Password
            ps.setString(8, user.getRole().name());                         // Role (ADMIN/CUSTOMER)

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
    public boolean update(User user) {
        String sql = "UPDATE User SET Email = ?, LastName = ?, MidName = ?, FirstName = ?, " +
                "DateCreated = ?, Password = ?, Role = ? WHERE UserID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getMidName());
            ps.setString(4, user.getFirstName());
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(user.getDateCreated()));
            ps.setString(6, user.getPassword());
            ps.setString(7, String.valueOf(user.getRole()));
            ps.setString(8, user.getUserID());

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
    public boolean delete(String userID) {
        String sql = "DELETE FROM User WHERE UserID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
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
