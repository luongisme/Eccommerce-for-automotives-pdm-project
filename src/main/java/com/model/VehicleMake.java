package com.model;

public class VehicleMake {
    private int maID;
    private String maName;

    public VehicleMake() {}

    public VehicleMake(int maID, String maName) {
        this.maID = maID;
        this.maName = maName;
    }

    public int getMaID() {
        return maID;
    }

    public void setMaID(int maID) {
        this.maID = maID;
    }

    public String getMaName() {
        return maName;
    }

    public void setMaName(String maName) {
        this.maName = maName;
    }
}
