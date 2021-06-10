package com.data.main.beauty;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static com.data.main.beauty.DataParser.beautyDate;

public class DataParserTest {
    private String format = "yyyy.MM.dd";
    private String expected;
    @Before
    public void setUp() throws Exception {
        var date = Calendar.getInstance().getTime();
        var dateFormat = new SimpleDateFormat(format);
        expected = dateFormat.format(date);
    }

    @Test
    public void beautyDateTestXMLGregorianCalendar() throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        var result = beautyDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c), format);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beautyDateTestLocalDate()  {
        var result = beautyDate(LocalDate.now(), format);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beautyDateTestLocalDateTime()  {
        var result = beautyDate(LocalDateTime.now(), format);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beautyDateTestOffsetDateTime()  {
        var result = beautyDate(OffsetDateTime.now(), format);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beautyDateTestZonedDateTime()  {
        var result = beautyDate(ZonedDateTime.now(), format);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beautyDateTestInstant()  {
        var result = beautyDate(Instant.now(), format);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beautyDateTestCalendar()  {
        var result = beautyDate(Calendar.getInstance(), format);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beautyDateTestDate()  {
        var result = beautyDate(new Date(), format);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beautyDateTestGregorianCalendar()  {
        var result = beautyDate(new GregorianCalendar(), format);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void beautyDateTestSqlDate()  {
        var result = beautyDate(new java.util.Date(), format);
        Assert.assertEquals(expected, result);
    }
}