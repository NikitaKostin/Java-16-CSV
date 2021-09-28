package com.main.pattern.strategy.springTemperature.strategy;

public interface TemperatureStrategy {
    double convertTemperatureFromCelsius(double value);
    TemperatureType getTemperatureType();

}
