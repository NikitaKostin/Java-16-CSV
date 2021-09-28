package com.main.pattern.strategy.springTemperature.strategy;

import org.springframework.stereotype.Component;

@Component("FAHRENHEIT")
public class FahrenheitStrategy implements TemperatureStrategy {
    @Override
    public double convertTemperatureFromCelsius(double value) {
        return Math.round(9.0/5 * value + 32);
    }

    @Override
    public TemperatureType getTemperatureType() {
        return TemperatureType.FAHRENHEIT;
    }
}
