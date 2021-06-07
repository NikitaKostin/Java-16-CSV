package rest.model;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public record RecordCSV(
        String id,
        String servicenumber,
        String statuscode,
        @Temporal(TemporalType.TIMESTAMP)
        Date created,
        String msgid,
        @Temporal(TemporalType.TIMESTAMP)
        Date put,
        String reasoncode,
        String route,
        String direction
) {

    public static RecordCSV getNewRecord(String recordData) {
        String[] splittedData = recordData.split(";");
        return new RecordCSV(
                splittedData[0],
                splittedData[1],
                splittedData[2],
                convertFromCSVFormat(splittedData[3]),
                splittedData[4],
                convertFromCSVFormat(splittedData[5]),
                splittedData[6],
                splittedData[7],
                splittedData[8]);
    }

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

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
