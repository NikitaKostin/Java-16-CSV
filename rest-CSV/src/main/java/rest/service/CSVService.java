package rest.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import rest.model.RecordCSV;

import static rest.model.RecordCSV.getNewRecord;

@Service
public class CSVService {

    private List<RecordCSV> records = new ArrayList<>();

    public void parseCSVFileToRecords() throws IOException {
        var file = new BufferedReader(new FileReader("rest-CSV/status_rcoi.csv"));
        file.readLine(); // Drop 1-st line
        String line;
        while ((line = file.readLine()) != null) {
            records.add(getNewRecord(line));
        }
        file.close();
    }

    public List<RecordCSV> getListRecords() throws IOException {
        if (records == null || records.size() == 0) {
            parseCSVFileToRecords();
        }
        return records;
    }

}
