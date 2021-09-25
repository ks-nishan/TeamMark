package com.example.myproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalculateMoney {


    public float Calculate(String pick, String retrn) throws ParseException {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date d1 = sdf.parse(pick);
        Date d2 = sdf.parse(retrn);

        long Time
                = d2.getTime() - d1.getTime();
        long Days
                = (Time
                / (1000 * 60 * 60 * 24))
                % 365;
        float Charge = 5000 * Days;
        return Charge;

    }

    public float Tax(float charge) {

        return (charge / 100) * 12;

    }
    public Float Coupon(String coupon) {

        if (coupon.equalsIgnoreCase("TM11") || coupon.equalsIgnoreCase("TM25")) {

            return 200.0f;
        } else
            return 0f;


    }


}


