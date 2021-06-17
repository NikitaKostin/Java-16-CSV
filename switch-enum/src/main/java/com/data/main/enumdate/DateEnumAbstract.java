package com.data.main.enumdate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public enum DateEnumAbstract {
    XML_GREGORIAN_CALENDAR {
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            var gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            try {
                return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            return null;
        }
    },
    LOCAL_DATE {
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            return LocalDate.parse(dateString, dateTimeFormatter);
        }
    },
    LOCAL_DATE_TIME {
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            return LocalDateTime.parse(dateString, dateTimeFormatter);
        }
    },
    OFFSET_DATE_TIME {
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            return OffsetDateTime.of(LocalDateTime.parse(dateString, dateTimeFormatter), ZoneOffset.UTC);
        }
    },
    ZONED_DATE_TIME {
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            return ZonedDateTime.of(LocalDateTime.parse(dateString, dateTimeFormatter), ZoneOffset.UTC);
        }
    },
    INSTANT {
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            return LocalDateTime.parse(dateString, dateTimeFormatter).toInstant(ZoneOffset.UTC);
        }
    },
    CALENDAR {
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
    },
    GREGORIAN_CALENDAR {
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
    },
    DATE{
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            return date;
        }
    },
    SQL_DATE{
        @Override
        public Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter) {
            return new java.sql.Date(date.getTime());
        }
    };

    public abstract Object getObject(String dateString, Date date, DateTimeFormatter dateTimeFormatter);
}
