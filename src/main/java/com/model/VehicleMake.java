package com.model;

public class VehicleMake {
    private String maID;
    private String maName;

    public VehicleMake() {}

    public VehicleMake(String maID, String maName) {
        this.maID = maID;
        this.maName = maName;
    }

    public String getMaID() {
        return maID;
    }

    public void setMaID(String maID) {
        this.maID = maID;
    }

    public String getMaName() {
        return maName;
    }

    public void setMaName(String maName) {
        this.maName = maName;
    }
}
