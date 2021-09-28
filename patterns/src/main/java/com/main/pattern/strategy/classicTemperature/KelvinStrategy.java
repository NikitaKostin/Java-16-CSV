package com.main.pattern.strategy.classicTemperature;


public class KelvinStrategy implements TemperatureStrategy {
    @Override
    public double convertTemperatureFromCelsius(double value) {
        return Math.round(value + 273.15);
    }
}
