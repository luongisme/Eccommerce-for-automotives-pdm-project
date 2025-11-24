package com.model;

public class VehicleModel {
    private String moID;
    private String maID;

    private String moName;

    public VehicleModel() {}

    public VehicleModel(String moID, String maID, String coID, String moName) {
        this.moID = moID;
        this.maID = maID;

        this.moName = moName;
    }



    public String getMaID() {
        return maID;
    }

    public void setMaID(String maID) {
        this.maID = maID;
    }

    public String getMoID() {
        return moID;
    }

    public void setMoID(String moID) {
        this.moID = moID;
    }

    public String getMoName() {
        return moName;
    }

    public void setMoName(String moName) {
        this.moName = moName;
    }
}
