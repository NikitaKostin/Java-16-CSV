package com.main.pattern.strategy.enumTemperature;

import java.util.stream.Stream;

public class EnumTemperatureConverter {
    public static void convertTemperatureEnum(double cesium){
       Stream.of(TemperatureType.values()).forEach(temperatureType ->
               System.out.println(temperatureType.convertTemperatureFromCelsius(cesium)));
    }
}
