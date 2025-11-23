package com.DAO.Interface;

import com.model.VehicleMake;

import java.util.List;

public interface vehiclemakeDAO {
    VehicleMake findById(String maID);
    List<VehicleMake> findAll();
    boolean insert(VehicleMake make);
    boolean update(VehicleMake make);
    boolean delete(String maID);
}
