package com.teammark.parkingmanagement.model;

public class ParkingArea {
    private String parkingareaID;
    private String parkingAreaImg;
    private String parkingareaTitle;
    private String parkingareaAddress;

    private String typeBike;
    private String typeCar;

    private String countBikeSlot;
    private String countCarSlot;

    private String feeBike;
    private String feeCar;

    private String evFacility;

    private String wattMin;
    private String wattMed;
    private String wattMax;

    private String conTwo;
    private String conCombo;
    private String conCha;

    public ParkingArea() {
    }


    public ParkingArea(String parkingareaID, String parkingAreaImg, String parkingareaTitle, String parkingareaAddress,
                       String typeBike, String typeCar, String countBikeSlot, String countCarSlot, String feeBike, String feeCar,
                       String evFacility, String wattMin, String wattMed, String wattMax, String conTwo, String conCombo, String conCha) {
        this.parkingareaID = parkingareaID;
        this.parkingAreaImg = parkingAreaImg;
        this.parkingareaTitle = parkingareaTitle;
        this.parkingareaAddress = parkingareaAddress;
        this.typeBike = typeBike;
        this.typeCar = typeCar;
        this.countBikeSlot = countBikeSlot;
        this.countCarSlot = countCarSlot;
        this.feeBike = feeBike;
        this.feeCar = feeCar;
        this.evFacility = evFacility;
        this.wattMin = wattMin;
        this.wattMed = wattMed;
        this.wattMax = wattMax;
        this.conTwo = conTwo;
        this.conCombo = conCombo;
        this.conCha = conCha;
    }



    public ParkingArea(String parkingareaID, String parkingareaTitle, String parkingareaAddress,
                       String typeBike, String typeCar, String countBikeSlot, String countCarSlot, String feeBike, String feeCar,
                       String evFacility, String wattMin, String wattMed, String wattMax, String conTwo, String conCombo, String conCha) {
        this.parkingareaID = parkingareaID;
        this.parkingareaTitle = parkingareaTitle;
        this.parkingareaAddress = parkingareaAddress;
        this.typeBike = typeBike;
        this.typeCar = typeCar;
        this.countBikeSlot = countBikeSlot;
        this.countCarSlot = countCarSlot;
        this.feeBike = feeBike;
        this.feeCar = feeCar;
        this.evFacility = evFacility;
        this.wattMin = wattMin;
        this.wattMed = wattMed;
        this.wattMax = wattMax;
        this.conTwo = conTwo;
        this.conCombo = conCombo;
        this.conCha = conCha;
    }

    public String getParkingareaID() {
        return parkingareaID;
    }

    public void setParkingareaID(String parkingareaID) {
        this.parkingareaID = parkingareaID;
    }

    public String getParkingAreaImg() {
        return parkingAreaImg;
    }

    public void setParkingAreaImg(String parkingAreaImg) {
        this.parkingAreaImg = parkingAreaImg;
    }

    public String getParkingareaTitle() {
        return parkingareaTitle;
    }

    public void setParkingareaTitle(String parkingareaTitle) {
        this.parkingareaTitle = parkingareaTitle;
    }

    public String getParkingareaAddress() {
        return parkingareaAddress;
    }

    public void setParkingareaAddress(String parkingareaAddress) {
        this.parkingareaAddress = parkingareaAddress;
    }

    public String getTypeBike() {
        return typeBike;
    }

    public void setTypeBike(String typeBike) {
        this.typeBike = typeBike;
    }

    public String getTypeCar() {
        return typeCar;
    }

    public void setTypeCar(String typeCar) {
        this.typeCar = typeCar;
    }

    public String getCountBikeSlot() {
        return countBikeSlot;
    }

    public void setCountBikeSlot(String countBikeSlot) {
        this.countBikeSlot = countBikeSlot;
    }

    public String getCountCarSlot() {
        return countCarSlot;
    }

    public void setCountCarSlot(String countCarSlot) {
        this.countCarSlot = countCarSlot;
    }

    public String getFeeBike() {
        return feeBike;
    }

    public void setFeeBike(String feeBike) {
        this.feeBike = feeBike;
    }

    public String getFeeCar() {
        return feeCar;
    }

    public void setFeeCar(String feeCar) {
        this.feeCar = feeCar;
    }

    public String getEvFacility() {
        return evFacility;
    }

    public void setEvFacility(String evFacility) {
        this.evFacility = evFacility;
    }

    public String getWattMin() {
        return wattMin;
    }

    public void setWattMin(String wattMin) {
        this.wattMin = wattMin;
    }

    public String getWattMed() {
        return wattMed;
    }

    public void setWattMed(String wattMed) {
        this.wattMed = wattMed;
    }

    public String getWattMax() {
        return wattMax;
    }

    public void setWattMax(String wattMax) {
        this.wattMax = wattMax;
    }

    public String getConTwo() {
        return conTwo;
    }

    public void setConTwo(String conTwo) {
        this.conTwo = conTwo;
    }

    public String getConCombo() {
        return conCombo;
    }

    public void setConCombo(String conCombo) {
        this.conCombo = conCombo;
    }

    public String getConCha() {
        return conCha;
    }

    public void setConCha(String conCha) {
        this.conCha = conCha;
    }
}
