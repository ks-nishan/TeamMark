package com.example.myproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalcDuration {




    public int durationCalc(String pick, String aReturn) throws ParseException {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date d1 = sdf.parse(pick);
        Date d2 = sdf.parse(aReturn);

        long Time = d2.getTime() - d1.getTime();
        long Days = (Time
                / (1000 * 60 * 60 * 24))
                % 365;
        int Day = (int)Days;
        return  Day;
    }
}



