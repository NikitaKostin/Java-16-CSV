package com.main.pattern.strategy.classicTemperature;


public class FahrenheitStrategy implements TemperatureStrategy {
    @Override
    public double convertTemperatureFromCelsius(double value) {
        return Math.round(9.0/5 * value + 32);
    }
}
