package com.main.pattern.strategy.springTemperature.service;

import com.main.pattern.strategy.springTemperature.strategy.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Component
public class TemperatureSpringConverterService {

    private final Map<String, TemperatureStrategy> strategyMap;

    public void convertTemperatureClassicStrategy(double cesium){
        Stream.of(TemperatureType.values()).forEach(temperatureType -> {
            var factory = strategyMap.get(temperatureType.name());
            System.out.println(factory.convertTemperatureFromCelsius(cesium));
        });
    }
}
