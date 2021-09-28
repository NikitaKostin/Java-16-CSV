package com.main.pattern.strategy.springTemperature.strategy;


import org.springframework.stereotype.Component;

@Component("KELVIN")
public class KelvinStrategy implements TemperatureStrategy {
    @Override
    public double convertTemperatureFromCelsius(double value) {
        return value + 273.15;
    }

    @Override
    public TemperatureType getTemperatureType() {
        return TemperatureType.KELVIN;
    }
}
