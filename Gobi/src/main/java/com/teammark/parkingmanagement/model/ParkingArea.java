package com.teammark.parkingmanagement.model;

public class ParkingArea {
    private String parkingareaID;
    private String parkingAreaImg;
    private String parkingareaTitle;
    private String parkingareaAddress;

    private int typeBike;
    private int typeCar;

    private int countBikeSlot;
    private int countCarSlot;

    private double feeBike;
    private double feeCar;

    private int evFacility;

    private int wattMin;
    private int wattMed;
    private int wattMax;

    private int conTwo;
    private int conCombo;
    private int conCha;

    public ParkingArea() {
    }


    public ParkingArea(String parkingareaID, String parkingAreaImg, String parkingareaTitle, String parkingareaAddress,
                       int typeBike, int typeCar, int countBikeSlot, int countCarSlot, double feeBike, double feeCar,
                       int evFacility, int wattMin, int wattMed, int wattMax, int conTwo, int conCombo, int conCha) {
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
                       int typeBike, int typeCar, int countBikeSlot, int countCarSlot, double feeBike, double feeCar,
                       int evFacility, int wattMin, int wattMed, int wattMax, int conTwo, int conCombo, int conCha) {
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

    public int getTypeBike() {
        return typeBike;
    }

    public void setTypeBike(int typeBike) {
        this.typeBike = typeBike;
    }

    public int getTypeCar() {
        return typeCar;
    }

    public void setTypeCar(int typeCar) {
        this.typeCar = typeCar;
    }

    public int getCountBikeSlot() {
        return countBikeSlot;
    }

    public void setCountBikeSlot(int countBikeSlot) {
        this.countBikeSlot = countBikeSlot;
    }

    public int getCountCarSlot() {
        return countCarSlot;
    }

    public void setCountCarSlot(int countCarSlot) {
        this.countCarSlot = countCarSlot;
    }

    public double getFeeBike() {
        return feeBike;
    }

    public void setFeeBike(double feeBike) {
        this.feeBike = feeBike;
    }

    public double getFeeCar() {
        return feeCar;
    }

    public void setFeeCar(double feeCar) {
        this.feeCar = feeCar;
    }

    public int getEvFacility() {
        return evFacility;
    }

    public void setEvFacility(int evFacility) {
        this.evFacility = evFacility;
    }

    public int getWattMin() {
        return wattMin;
    }

    public void setWattMin(int wattMin) {
        this.wattMin = wattMin;
    }

    public int getWattMed() {
        return wattMed;
    }

    public void setWattMed(int wattMed) {
        this.wattMed = wattMed;
    }

    public int getWattMax() {
        return wattMax;
    }

    public void setWattMax(int wattMax) {
        this.wattMax = wattMax;
    }

    public int getConTwo() {
        return conTwo;
    }

    public void setConTwo(int conTwo) {
        this.conTwo = conTwo;
    }

    public int getConCombo() {
        return conCombo;
    }

    public void setConCombo(int conCombo) {
        this.conCombo = conCombo;
    }

    public int getConCha() {
        return conCha;
    }

    public void setConCha(int conCha) {
        this.conCha = conCha;
    }
}
