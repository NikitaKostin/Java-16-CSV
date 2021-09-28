package com.main.pattern.strategy.springTemperature.strategy;


import org.springframework.stereotype.Component;

@Component("REAUMUR")
public class ReaumurStrategy implements TemperatureStrategy {
    @Override
    public double convertTemperatureFromCelsius(double value) {
        return Math.round(0.8 * value);
    }

    @Override
    public TemperatureType getTemperatureType() {
        return TemperatureType.REAUMUR;
    }
}
