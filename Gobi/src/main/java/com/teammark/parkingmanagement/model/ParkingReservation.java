package com.teammark.parkingmanagement.model;

public class ParkingReservation {
    private String reservationID;
    private String slotID;
    private String slotTitle;
    private String vehicleNumber;
    private String customerName;
    private String licenceNumber;
    private String contactNumber;
    private String arrivalDate;
    private String arrivalTime;
    private String evcRequire;

    public ParkingReservation() {
    }

    public ParkingReservation(String reservationID, String slotID, String slotTitle, String vehicleNumber,
                              String customerName, String licenceNumber, String contactNumber,
                              String arrivalDate, String arrivalTime, String evcRequire) {
        this.reservationID = reservationID;
        this.slotID = slotID;
        this.slotTitle = slotTitle;
        this.vehicleNumber = vehicleNumber;
        this.customerName = customerName;
        this.licenceNumber = licenceNumber;
        this.contactNumber = contactNumber;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.evcRequire = evcRequire;
    }

    public ParkingReservation(String slotID, String slotTitle, String vehicleNumber, String customerName,
                              String licenceNumber, String contactNumber, String arrivalDate,
                              String arrivalTime, String evcRequire) {
        this.slotID = slotID;
        this.slotTitle = slotTitle;
        this.vehicleNumber = vehicleNumber;
        this.customerName = customerName;
        this.licenceNumber = licenceNumber;
        this.contactNumber = contactNumber;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.evcRequire = evcRequire;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getSlotID() {
        return slotID;
    }

    public void setSlotID(String slotID) {
        this.slotID = slotID;
    }

    public String getSlotTitle() {
        return slotTitle;
    }

    public void setSlotTitle(String slotTitle) {
        this.slotTitle = slotTitle;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getEvcRequire() {
        return evcRequire;
    }

    public void setEvcRequire(String evcRequire) {
        this.evcRequire = evcRequire;
    }
}
