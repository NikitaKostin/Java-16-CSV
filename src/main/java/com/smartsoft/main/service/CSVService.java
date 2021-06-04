package com.smartsoft.main.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.smartsoft.main.model.RecordCSV.getNewRecord;

import com.smartsoft.main.model.Record;

public class CSVService {

    public static List<Record> parseCSVFileToRecords() throws IOException {
        var file = new BufferedReader(new FileReader("./test_case.csv"));
        var records = new ArrayList<Record>();
        file.readLine(); // Drop 1-st line
        String line;
        while ((line = file.readLine()) != null) {
            var recordCSV = getNewRecord(line);
            records.add(new Record(
                    recordCSV.ssoid(),
                    recordCSV.ts(),
                    recordCSV.grp(),
                    recordCSV.type(),
                    recordCSV.subtype(),
                    recordCSV.url(),
                    recordCSV.orgid(),
                    recordCSV.formid(),
                    recordCSV.code(),
                    recordCSV.ltpa(),
                    recordCSV.sudirresponse(),
                    recordCSV.ymdh()));
        }
        file.close();
        return records;
    }
}
