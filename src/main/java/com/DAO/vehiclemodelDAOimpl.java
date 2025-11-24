package com.DAO;

import com.DAO.Interface.vehiclemodelDAO;
import com.model.VehicleModel;

import java.util.List;

public class vehiclemodelDAOimpl implements vehiclemodelDAO {
    @Override
    public VehicleModel findById(String moID) {
        return null;
    }

    @Override
    public List<VehicleModel> findByMakeId(String maID) {
        return List.of();
    }

    @Override
    public List<VehicleModel> findAll() {
        return List.of();
    }

    @Override
    public boolean insert(VehicleModel model) {
        return false;
    }

    @Override
    public boolean update(VehicleModel model) {
        return false;
    }

    @Override
    public boolean delete(String moID) {
        return false;
    }
}
