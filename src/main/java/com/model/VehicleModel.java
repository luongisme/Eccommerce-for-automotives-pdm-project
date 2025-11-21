package com.model;

public class VehicleModel {
    private int moID;
    private int maID;
    private int coID;
    private String moName;

    public VehicleModel() {}

    public VehicleModel(int moID, int maID, int coID, String moName) {
        this.moID = moID;
        this.maID = maID;
        this.coID = coID;
        this.moName = moName;
    }

    public int getCoID() {
        return coID;
    }

    public void setCoID(int coID) {
        this.coID = coID;
    }

    public int getMaID() {
        return maID;
    }

    public void setMaID(int maID) {
        this.maID = maID;
    }

    public int getMoID() {
        return moID;
    }

    public void setMoID(int moID) {
        this.moID = moID;
    }

    public String getMoName() {
        return moName;
    }

    public void setMoName(String moName) {
        this.moName = moName;
    }
}
