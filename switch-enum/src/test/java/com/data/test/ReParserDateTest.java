package com.data.test;

import com.data.main.enumdate.DateEnum;
import com.data.main.enumdate.DateEnumAbstract;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.data.main.DataReParser.enumDate;
import static com.data.main.DataReParser.switchDate;

public class ReParserDateTest {
    private final String dateFormat = "2021.06.15";
    //    SWITCH
    @Test
    public void reParserDateTestXMLGregorianCalendar() throws DatatypeConfigurationException, ParseException {
        var result = switchDate(dateFormat, DateEnum.XML_GREGORIAN_CALENDAR);
        Assert.assertTrue(result instanceof XMLGregorianCalendar);
    }

    @Test
    public void reParserDateTestLocalDate() throws ParseException, DatatypeConfigurationException {
        var result = switchDate(dateFormat, DateEnum.LOCAL_DATE);
        Assert.assertTrue(result instanceof LocalDate);
    }

    @Test
    public void reParserDateTestLocalDateTime() throws DatatypeConfigurationException, ParseException {
        var result = switchDate(dateFormat, DateEnum.LOCAL_DATE_TIME);
        Assert.assertTrue(result instanceof LocalDateTime);
    }

    @Test
    public void reParserDateTestOffsetDateTime() throws DatatypeConfigurationException, ParseException {
        var result = switchDate(dateFormat, DateEnum.OFFSET_DATE_TIME);
        Assert.assertTrue(result instanceof OffsetDateTime);
    }

    @Test
    public void reParserDateTestZonedDateTime() throws DatatypeConfigurationException, ParseException {
        var result = switchDate(dateFormat, DateEnum.ZONED_DATE_TIME);
        Assert.assertTrue(result instanceof ZonedDateTime);
    }

    @Test
    public void reParserDateTestInstant() throws DatatypeConfigurationException, ParseException {
        var result = switchDate(dateFormat, DateEnum.INSTANT);
        Assert.assertTrue(result instanceof Instant);
    }

    @Test
    public void reParserDateTestCalendar() throws DatatypeConfigurationException, ParseException {
        var result = switchDate(dateFormat, DateEnum.CALENDAR);
        Assert.assertTrue(result instanceof Calendar);
    }

    @Test
    public void reParserDateTestDate() throws DatatypeConfigurationException, ParseException {
        var result = switchDate(dateFormat, DateEnum.DATE);
        Assert.assertTrue(result instanceof Date);
    }

    @Test
    public void reParserDateTestGregorian() throws DatatypeConfigurationException, ParseException {
        var result = switchDate(dateFormat, DateEnum.GREGORIAN_CALENDAR);
        Assert.assertTrue(result instanceof GregorianCalendar);
    }

    @Test
    public void reParserDateTestSqlDate() throws DatatypeConfigurationException, ParseException {
        var result = switchDate(dateFormat, DateEnum.SQL_DATE);
        System.out.println(result);
        Assert.assertTrue(result instanceof java.sql.Date);
    }
//    ENUM ABSTRACT

    @Test
    public void reParserDateTestXMLGregorianCalendarAbstract() throws DatatypeConfigurationException, ParseException {
        var result = enumDate(dateFormat, DateEnumAbstract.XML_GREGORIAN_CALENDAR);
        Assert.assertTrue(result instanceof XMLGregorianCalendar);
    }

    @Test
    public void reParserDateTestLocalDateAbstract() throws ParseException, DatatypeConfigurationException {
        var result = enumDate(dateFormat, DateEnumAbstract.LOCAL_DATE);
        Assert.assertTrue(result instanceof LocalDate);
    }

    @Test
    public void reParserDateTestLocalDateTimeAbstract() throws ParseException, DatatypeConfigurationException {
        var result = enumDate(dateFormat, DateEnumAbstract.LOCAL_DATE_TIME);
        Assert.assertTrue(result instanceof LocalDateTime);
    }

    @Test
    public void reParserDateTestZonedDateTimeAbstract() throws DatatypeConfigurationException, ParseException {
        var result = enumDate(dateFormat, DateEnumAbstract.ZONED_DATE_TIME);
        Assert.assertTrue(result instanceof ZonedDateTime);
    }

    @Test
    public void reParserDateTestInstantAbstract() throws DatatypeConfigurationException, ParseException {
        var result = enumDate(dateFormat, DateEnumAbstract.INSTANT);
        Assert.assertTrue(result instanceof Instant);
    }

    @Test
    public void reParserDateTestCalendarAbstract() throws DatatypeConfigurationException, ParseException {
        var result = enumDate(dateFormat, DateEnumAbstract.CALENDAR);
        Assert.assertTrue(result instanceof Calendar);
    }

    @Test
    public void reParserDateTestDateAbstract() throws DatatypeConfigurationException, ParseException {
        var result = enumDate(dateFormat, DateEnumAbstract.DATE);
        Assert.assertTrue(result instanceof Date);
    }

    @Test
    public void reParserDateTestGregorianAbstract() throws DatatypeConfigurationException, ParseException {
        var result = enumDate(dateFormat, DateEnumAbstract.GREGORIAN_CALENDAR);
        Assert.assertTrue(result instanceof GregorianCalendar);
    }

    @Test
    public void reParserDateTestSqlDateAbstract() throws DatatypeConfigurationException, ParseException {
        var result = enumDate(dateFormat, DateEnumAbstract.SQL_DATE);
        System.out.println(result);
        Assert.assertTrue(result instanceof java.sql.Date);
    }
}
