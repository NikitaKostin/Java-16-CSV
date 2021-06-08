package rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rest.model.RecordCSV;
import rest.model.Result;
import rest.service.CSVService;

import java.io.IOException;
import java.util.stream.Collectors;

import static rest.service.DelayService.getTimeToMs;

@Slf4j
@RestController
public class MainRestController {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private CSVService csvService;

    @GetMapping(value = "/stat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getStatusByRunId(@RequestParam String delay) {
        String result = null;
        var inputDelay = getTimeToMs(delay);

        try {
            var delayStatusList = csvService.getListRecords()
                    .stream()
                    .filter(recordCSV -> (recordCSV.put().getTime() - recordCSV.created().getTime()) > inputDelay)
                    .map(RecordCSV::statuscode)
                    .collect(Collectors.toList());

            result = mapper.writeValueAsString(new Result(delayStatusList.size(), delayStatusList));
        } catch (IOException e) {
            log.error("Ошибка");
            e.printStackTrace();
        }

        return result;
    }

}
