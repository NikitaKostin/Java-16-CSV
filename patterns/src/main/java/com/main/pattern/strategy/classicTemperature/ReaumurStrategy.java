package com.main.pattern.strategy.classicTemperature;

public class ReaumurStrategy implements TemperatureStrategy {
    @Override
    public double convertTemperatureFromCelsius(double value) {
        return Math.round(0.8 * value);
    }

}
