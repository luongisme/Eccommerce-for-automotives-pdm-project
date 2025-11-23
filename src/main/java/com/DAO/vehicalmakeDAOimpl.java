package com.DAO;

import com.DAO.Interface.vehiclemakeDAO;
import com.model.VehicleMake;

import java.util.List;

public class vehicalmakeDAOimpl implements vehiclemakeDAO {
    @Override
    public VehicleMake findById(int maID) {
        return null;
    }

    @Override
    public List<VehicleMake> findAll() {
        return List.of();
    }

    @Override
    public boolean insert(VehicleMake make) {
        return false;
    }

    @Override
    public boolean update(VehicleMake make) {
        return false;
    }

    @Override
    public boolean delete(int maID) {
        return false;
    }
}
