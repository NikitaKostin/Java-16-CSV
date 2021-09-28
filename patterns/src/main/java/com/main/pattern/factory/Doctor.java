package com.main.pattern.factory;

import java.util.List;

public class Doctor implements Healer {
    @Override
    public List<String> medication() {
        return List.of("Парацетамол", "Нафтизин", "Арбидол");
    }

    @Override
    public String giveCourseOfTreatment() {
        return "Постельный режим. Пить больше воды. Оставаться дома";
    }

}
