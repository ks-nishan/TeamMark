package com.example.myproject;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    private CalculateMoney calculateMoney;

    @Before
    public void setUp(){ calculateMoney= new CalculateMoney(); }

    @Test
    public void TestCharge() throws ParseException {

        String pick = "01/05/2021";
        String retur= "01/10/2021";

        float result = calculateMoney.Calculate(pick,retur);
        assertEquals(25000.0f,result,0);


    }
    @Test
    public void TestTax() {

        float result = calculateMoney.Tax(1000.0f);

        assertEquals(120.0f,result,0);


    }
    @Test
    public void TestCoupon() {

        String coupon = "TM11";
        float result = calculateMoney.Coupon(coupon);

        assertEquals( 200.0f,result,0);

    }


    private CalcDuration trip;


    @Before
    public void setTrip(){ trip= new CalcDuration(); }

    @Test
    public void TestDuration() throws ParseException {

        String pick = "01/05/2021";
        String retur= "01/10/2021";

        int result = trip.durationCalc(pick,retur);
        assertEquals(5,result,0);


    }









}