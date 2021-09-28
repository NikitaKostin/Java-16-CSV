package com.main.pattern.factory;

public class ShamanDialog extends DialogFactory {
    @Override
    public Healer createHealer() {
        return new Shaman();
    }
}
