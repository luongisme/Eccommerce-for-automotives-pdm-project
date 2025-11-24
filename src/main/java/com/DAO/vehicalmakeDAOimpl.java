package com.DAO;

import com.DAO.Interface.vehiclemakeDAO;
import com.Util.JdbcConnector;
import com.model.Address;
import com.model.VehicleMake;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class vehicalmakeDAOimpl implements vehiclemakeDAO {

    private VehicleMake mapRow(ResultSet rs) throws SQLException {
        VehicleMake vm = new VehicleMake();
        vm.setMaID(rs.getString("MaID"));
        vm.setMaName(rs.getString("MaName"));
        return vm;
    }

    @Override
    public VehicleMake findById(String maID) {
        String sql = "SELECT * FROM vehiclemake WHERE MaID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maID);
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
    public List<VehicleMake> findAll() {
        String sql = "SELECT * FROM VehicleMake";
        List<VehicleMake> vehicleMakes = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vehicleMakes.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return vehicleMakes;
    }

    @Override
    public boolean insert(VehicleMake make) {
        String sql = "INSERT INTO vehicleMake (MaId, MaName) VALUES (?,?)";
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, make.getMaID());
            ps.setString(2, make.getMaName());

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
    public boolean update(VehicleMake make) {
        String sql = "UPDATE vehicleMake SET MaID = ?, MaName= ? WHERE MaID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, make.getMaID());
            ps.setString(2, make.getMaName());
            ps.setString(3, make.getMaID());

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
    public boolean delete(String maID) {
        String sql = "DELETE FROM vehiclemake WHERE MaID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maID);

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
