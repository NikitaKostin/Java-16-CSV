package com.data.main;

import com.data.main.enumdate.DateEnum;
import com.data.main.enumdate.DateEnumAbstract;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataReParser {
    private static final String dateFormat = "yyyy.MM.dd";
    private static final String dateTimeFormat = "yyyy.MM.dd HH:mm:ss";

    public static Object switchDate(String dateString, DateEnum dateEnum) throws ParseException, DatatypeConfigurationException {
        var simpleDateFormat = new SimpleDateFormat(dateFormat);
        var date = simpleDateFormat.parse(dateString);
        var dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);

        return switch (dateEnum) {
            case XML_GREGORIAN_CALENDAR -> {
                var gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(date);
                yield DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            }
            case LOCAL_DATE -> LocalDate.parse(dateString, DateTimeFormatter.ofPattern(dateFormat));
            case LOCAL_DATE_TIME -> LocalDateTime.parse(dateString + " 00:00:00", dateTimeFormatter);
            case OFFSET_DATE_TIME -> OffsetDateTime.of(LocalDateTime.parse(dateString + " 00:00:00", dateTimeFormatter), ZoneOffset.UTC);
            case ZONED_DATE_TIME -> ZonedDateTime.of(LocalDateTime.parse(dateString + " 00:00:00", dateTimeFormatter), ZoneOffset.UTC);
            case INSTANT -> LocalDateTime.parse(dateString + " 00:00:00", dateTimeFormatter).toInstant(ZoneOffset.UTC);
            case CALENDAR, GREGORIAN_CALENDAR -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                yield cal;
            }
            case DATE -> date;
            case SQL_DATE -> new java.sql.Date(date.getTime());
        };

    }

    public static Object enumDate(String dateString, DateEnumAbstract dateEnum) throws ParseException, DatatypeConfigurationException {
        var simpleDateFormat = new SimpleDateFormat(dateFormat);
        var date = simpleDateFormat.parse(dateString);
        var dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);

        return switch (dateEnum) {
            case XML_GREGORIAN_CALENDAR, CALENDAR, GREGORIAN_CALENDAR, DATE, SQL_DATE -> dateEnum.getObject(null, date, null);
            case LOCAL_DATE -> dateEnum.getObject(dateString, null, DateTimeFormatter.ofPattern(dateFormat));
            case LOCAL_DATE_TIME, OFFSET_DATE_TIME, ZONED_DATE_TIME, INSTANT -> dateEnum.getObject(dateString + " 00:00:00", null, dateTimeFormatter);
        };

    }
}
