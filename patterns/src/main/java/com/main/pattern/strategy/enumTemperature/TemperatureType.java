package com.main.pattern.strategy.enumTemperature;

public enum TemperatureType {
    Kelvin(1){
        @Override
        public double convertTemperatureFromCelsius(double value) {
            return Math.round(value + 273.15);
        }
    },
    Fahrenheit(2){
        @Override
        public double convertTemperatureFromCelsius(double value) {
            return Math.round(9.0/5 * value + 32);
        }
    },
    Reaumur(3){
        @Override
        public double convertTemperatureFromCelsius(double value) {
            return Math.round(0.8 * value);
        }
    };

    private final long value;

    TemperatureType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
    public double convertTemperatureFromCelsius(double value){
        return 0.0;
    }
}
