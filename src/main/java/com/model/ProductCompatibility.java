package com.model;

public class ProductCompatibility {
    private int coID;
    private int yearStart;
    private String specifications;
    private int yearEnd;

    public ProductCompatibility() {}

    public ProductCompatibility(int coID, int yearStart, String specifications, int yearEnd) {
        this.coID = coID;
        this.yearStart = yearStart;
        this.specifications = specifications;
        this.yearEnd = yearEnd;
    }

    public int getYearStart() {
        return yearStart;
    }

    public void setYearStart(int yearStart) {
        this.yearStart = yearStart;
    }

    public int getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(int yearEnd) {
        this.yearEnd = yearEnd;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public int getCoID() {
        return coID;
    }

    public void setCoID(int coID) {
        this.coID = coID;
    }
}
