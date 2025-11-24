package com.DAO;

import com.DAO.Interface.productcompatibilityDAO;
import com.Util.JdbcConnector;
import com.model.Address;
import com.model.ProductCompatibility;
import com.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class productcompatibilityDAOimpl implements productcompatibilityDAO {

    private ProductCompatibility mapRow(ResultSet rs) throws SQLException {
        ProductCompatibility pc = new ProductCompatibility();
        pc.setCoID(rs.getString("CoID"));
        pc.setPID(rs.getString("PID"));
        pc.setMoID(rs.getString("MOID"));
        pc.setCDID(rs.getString("CDID"));
        pc.setYearStart(rs.getInt("yearStart"));
        pc.setSpecifications(rs.getString("specifications"));
        pc.setYearEnd(rs.getInt("yearEnd"));
        return pc;
    }

    @Override
    public ProductCompatibility findById(String coID) {
        String sql = "SELECT * FROM address WHERE CoID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, coID);
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
    public List<ProductCompatibility> findAll() {
        String sql = "SELECT * FROM ProductCompatibility";
        List<ProductCompatibility> productCompatibilities = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productCompatibilities.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return productCompatibilities;
    }

    @Override
    public boolean insert(ProductCompatibility pc) {
        String sql = "INSERT INTO ProductCompatibility (CoID, PID, MoID, CDID, yearStart, specifications, yearEnd) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pc.getCoID());
            ps.setString(2, pc.getPID());
            ps.setString(3, pc.getMoID());
            ps.setString(4, pc.getCDID());
            ps.setInt(5, pc.getYearStart());
            ps.setString(6, pc.getSpecifications());
            ps.setInt(7, pc.getYearEnd());

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
    public boolean update(ProductCompatibility pc) {
        String sql = "UPDATE ProductCompatibility SET CoID = ?, PID = ?, MoID = ?, " +
                "CDID = ?, yearStart = ?, specifications = ?, yearEnd = ? " +
                "WHERE PCID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pc.getCoID());
            ps.setString(2, pc.getPID());
            ps.setString(3, pc.getMoID());
            ps.setString(4, pc.getCDID());
            ps.setInt(5, pc.getYearStart());
            ps.setString(6, pc.getSpecifications());
            ps.setInt(7, pc.getYearEnd());
            ps.setString(8, pc.getCoID());

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
    public boolean delete(String coID) {
        String sql = "DELETE FROM Address WHERE PCID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, coID);

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