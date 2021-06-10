package com.data.main.beauty;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DataParser {
    public static String beautyDate(Object date, String format) {
        var dateFormat = new SimpleDateFormat(format);
        var formatter = DateTimeFormatter.ofPattern(format);
        if (date instanceof XMLGregorianCalendar xmlGregorianCalendar) {
            return dateFormat.format(xmlGregorianCalendar.toGregorianCalendar().getTime());
        } else if (date instanceof LocalDate localDate) {
            return localDate.format(formatter);
        } else if (date instanceof LocalDateTime localDateTime) {
            return localDateTime.format(formatter);
        } else if (date instanceof OffsetDateTime offsetDateTime) {
            return offsetDateTime.format(formatter);
        } else if (date instanceof ZonedDateTime zonedDateTime) {
            return zonedDateTime.format(formatter);
        } else if (date instanceof Instant instant) {
            return formatter.withZone(ZoneId.systemDefault()).format(instant);
        } else if (date instanceof Calendar calendar) {
            return  dateFormat.format(calendar.getTime());
        } else if (date instanceof Date inputDate) {
            return  dateFormat.format(inputDate.getTime());
        }
        return null;
    }
}
