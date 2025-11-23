package com.DAO.Interface;

import com.model.VehicleModel;

import java.util.List;

public interface vehiclemodelDAO {
    VehicleModel findById(String moID);
    List<VehicleModel> findByMakeId(String maID);
    List<VehicleModel> findAll();
    boolean insert(VehicleModel model);
    boolean update(VehicleModel model);
    boolean delete(String moID);
}
