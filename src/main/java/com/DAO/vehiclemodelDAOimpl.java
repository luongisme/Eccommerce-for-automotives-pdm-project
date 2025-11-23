package com.DAO;

import com.DAO.Interface.vehiclemodelDAO;
import com.Util.JdbcConnector;
import com.model.VehicleModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class vehiclemodelDAOimpl implements vehiclemodelDAO {

    private VehicleModel mapRow(ResultSet rs) throws SQLException {
        VehicleModel veMo = new VehicleModel();
        veMo.setMoID(rs.getString("MO_ID"));
        veMo.setMaID(rs.getString("MA_ID"));
        veMo.setCoID(rs.getString("CO_ID"));
        veMo.setMoName(rs.getString("MO_NAME"));
        return veMo;
    }

    @Override
    public VehicleModel findById(String moID) {
        String sql = "SELECT * FROM address WHERE MO_ID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, moID);
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
    public List<VehicleModel> findByMakeId(String maID) {
        String sql = "SELECT * FROM address WHERE MA_ID = ?";
        List<VehicleModel> vehicleModels = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, maID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicleModels.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return vehicleModels;
    }

    @Override
    public List<VehicleModel> findAll() {
        String sql = "SELECT * FROM VehicleModel";
        List<VehicleModel> vehicleModels = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vehicleModels.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return vehicleModels;
    }

    @Override
    public boolean insert(VehicleModel model) {
        String sql = "INSERT INTO VehicleModel (MO_ID, MA_ID, CO_ID, MO_NAME) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, model.getMoID());
            ps.setString(2, model.getMaID());
            ps.setString(3, model.getCoID());
            ps.setString(4, model.getMoName());

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
    public boolean update(VehicleModel model) {
        String sql = "UPDATE VehicleModel SET MO_ID = ?, MA_ID = ?, CO_ID = ?, MO_NAME = ? " +
                "WHERE MO_ID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, model.getMoID());
            ps.setString(2, model.getMaID());
            ps.setString(3, model.getCoID());
            ps.setString(4, model.getMoName());
            ps.setString(5, model.getMoID());

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
    public boolean delete(String moID) {
        String sql = "DELETE FROM Address WHERE MO_ID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, moID);

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
