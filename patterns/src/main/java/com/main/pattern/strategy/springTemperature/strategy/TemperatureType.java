package com.main.pattern.strategy.springTemperature.strategy;

public enum TemperatureType {
    KELVIN(1),
    FAHRENHEIT(2),
    REAUMUR(3);

    private final long value;

    TemperatureType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
