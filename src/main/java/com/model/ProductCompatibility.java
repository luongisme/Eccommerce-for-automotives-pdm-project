package com.model;

public class ProductCompatibility {
    private String PCID;
    private String PID;
    private String MoID;
    private String CDID;
    private int yearStart;
    private int yearEnd;
    private String specifications;

    public ProductCompatibility() {}

    public ProductCompatibility(String PCID, String PID, String MoID, String CDID, int yearStart, int yearEnd, String specifications) {
        this.PCID = PCID;
        this.PID = PID;
        this.MoID = MoID;
        this.CDID = CDID;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        this.specifications = specifications;
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

    public String getPCID() {
        return PCID;
    }

    public void setPCID(String PCID) {
        this.PCID = PCID;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getMoID() {
        return MoID;
    }

    public void setMoID(String MoID) {
        this.MoID = MoID;
    }

    public String getCDID() {
        return CDID;
    }

    public void setCDID(String CDID) {
        this.CDID = CDID;
    }
}
