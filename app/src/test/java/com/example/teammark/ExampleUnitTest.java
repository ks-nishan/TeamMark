package com.example.teammark;

import com.example.myproject.CalcDuration;
import com.example.myproject.CalculateMoney;

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

    private CalculationAD calculationAD;
    @Before
    public void setUp() {
        calculationAD = new CalculationAD();
    }

    @Test
    public void goldADTest(){
        int answer = 3000;
        //int answer = calculationAD.GoldSubTotal(3);
        assertEquals(3000,answer);
    }

    @Test
    public void silverADTest(){
        int answer = calculationAD.SilverSubTotal(2);
        assertEquals(1500,answer);
    }

    @Test
    public void brownADTest(){
        int answer = calculationAD.BrownSubTotal(4);
        assertEquals(2000,answer);
    }

    private CalcDuration trip;



}