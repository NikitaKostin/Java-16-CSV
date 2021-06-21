package com.example.model;

import com.example.entity.TestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Result {
    private List<TestData> data;
}
