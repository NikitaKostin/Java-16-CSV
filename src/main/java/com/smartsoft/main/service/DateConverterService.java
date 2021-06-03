package com.smartsoft.main.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverterService {

    private final static String DATE_FORMAT = "yyyy-MM-dd-hh";

    public static Date convertFromCSVFormat(String date) {
        try {
            var format = new SimpleDateFormat(DATE_FORMAT);
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

