package com.example.controller;

import com.example.model.Result;
import com.example.repository.TestDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainRestController {
    private final ObjectMapper mapper = new ObjectMapper();
    @NonNull
    private TestDataRepository testDataRepository;

    @GetMapping(value = "/all", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getAll() {
        String result = null;

        try {
            var delayStatusList = testDataRepository.findAll();

            result = mapper.writeValueAsString(new Result(delayStatusList));
        } catch (IOException e) {
            log.error("Ошибка");
            e.printStackTrace();
        }

        return result;
    }

}
