package com.main.pattern.factory;

public class DoctorDialog extends DialogFactory {

    @Override
    public Healer createHealer() {
        return new Doctor();
    }
}
