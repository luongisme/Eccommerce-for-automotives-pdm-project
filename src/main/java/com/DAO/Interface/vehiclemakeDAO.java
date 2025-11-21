package com.DAO.Interface;

import com.model.VehicleMake;

import java.util.List;

public interface vehiclemakeDAO {
    VehicleMake findById(int maID);
    List<VehicleMake> findAll();
    boolean insert(VehicleMake make);
    boolean update(VehicleMake make);
    boolean delete(int maID);
}
