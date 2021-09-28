package com.main.pattern.factory;

public class WitchDoctorDialog extends DialogFactory {
    @Override
    public Healer createHealer() {
        return new WitchDoctor();
    }
}
