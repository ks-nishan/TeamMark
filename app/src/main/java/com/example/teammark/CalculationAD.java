package com.example.teammark;

public class CalculationAD {
    protected int GoldSubTotal(int days){
        int ans = 1000 * days;
        return ans;
    }

    protected int SilverSubTotal(int days){
        int ans = 750 * days;
        return ans;
    }

    protected  int BrownSubTotal(int days){
        int ans = 500 * days;
        return ans;
    }
}
