package com.smartsoft.main.service;

import com.smartsoft.main.model.RecordCSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.smartsoft.main.model.RecordCSV.getNewRecord;

public class CSVService {

    public static List<RecordCSV> parseCSVFileToRecords() throws IOException {
        var file = new BufferedReader(new FileReader("./test_case.csv"));
        ArrayList<RecordCSV> records = new ArrayList<>();
        file.readLine(); // Drop 1-st line
        String line;
        while ((line = file.readLine()) != null) {
            records.add(getNewRecord(line));
        }
        file.close();
        return records;
    }
}
