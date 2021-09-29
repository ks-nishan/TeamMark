package com.teammark.parkingmanagement.util;

import com.teammark.parkingmanagement.CalculateParkingPaymentActivity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingPaymentCalculatorTest {

    private ParkingPaymentCalculator calcPark;

    @Before
    public void setUp() {
        calcPark = new ParkingPaymentCalculator(1, 30, 50, 25);
    }

    @Test
    public void calcHour() {
        double result = calcPark.calcHour();
        assertEquals(1.5, result, 0);
    }

    @Test
    public void calcParkFee() {
        double result = calcPark.calcParkFee();
        assertEquals(75.0, result, 0);
    }

    @Test
    public void calcTotalPayment() {
        double result = calcPark.calcTotalPayment();
        assertEquals(100.0, result, 0);
    }
}