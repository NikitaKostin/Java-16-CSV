package com.smartsoft.main.model;

import com.smartsoft.main.service.DateConverterService;

import javax.persistence.*;
import java.util.Date;

public record RecordCSV(String ssoid,
                        String ts,
                        String grp,
                        String type,
                        String subtype,
                        String url,
                        String orgid,
                        String formid,
                        String code,
                        String ltpa,
                        String sudirresponse,
                        @Temporal(TemporalType.TIMESTAMP)
                        Date ymdh) {

    public static RecordCSV getNewRecord(String recordData) {
        String[] splittedData = recordData.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        return new RecordCSV(
                splittedData[0],
                splittedData[1],
                splittedData[2],
                splittedData[3],
                splittedData[4],
                splittedData[5],
                splittedData[6],
                splittedData[7],
                splittedData[8],
                splittedData[9],
                splittedData[10],
                DateConverterService.convertFromCSVFormat(splittedData[11]));
    }


}


