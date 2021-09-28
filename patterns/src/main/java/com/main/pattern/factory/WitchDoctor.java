package com.main.pattern.factory;

import java.util.List;

public class WitchDoctor implements Healer {
    @Override
    public List<String> medication() {
        return List.of("Красная нитка", "Святая вода", "Заговор");
    }

    @Override
    public String giveCourseOfTreatment() {
        return "Нитку на руку повязать, помощение святой водой окрапить, заговор чиатать, в баню сходить";
    }
}
