package com.teammark.parkingmanagement.util;

public class ParkingPaymentCalculator {
    private double hours;
    private double minutes;
    private double vehicleFee;
    private double wattFee;
    private double totalPayment;

    public ParkingPaymentCalculator() {
        this.totalPayment = 0.0;
    }

    public ParkingPaymentCalculator(double hours, double minutes, double vehicleFee, double wattFee) {
        this.hours = hours;
        this.minutes = minutes;
        this.vehicleFee = vehicleFee;
        this.wattFee = wattFee;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }

    public double getVehicleFee() {
        return vehicleFee;
    }

    public void setVehicleFee(double vehicleFee) {
        this.vehicleFee = vehicleFee;
    }

    public double getWattFee() {
        return wattFee;
    }

    public void setWattFee(double wattFee) {
        this.wattFee = wattFee;
    }

    public double getTotalPayment() {
        return totalPayment ;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }
    public double calcHour(){
        return (hours + (minutes/60));
    }

    public double calcParkFee(){
        return(vehicleFee * calcHour());
    }

    public double calcTotalPayment(){
        return (calcParkFee() + wattFee);
    }
}
