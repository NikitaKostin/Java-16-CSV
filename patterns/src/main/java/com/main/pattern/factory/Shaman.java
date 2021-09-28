package com.main.pattern.factory;

import java.util.List;

public class Shaman implements Healer {
    @Override
    public List<String> medication() {
        return List.of("Кора дуба, сушеный можжевельник, настойка полыни");
    }

    @Override
    public String giveCourseOfTreatment() {
        return "Кору дуба к больному месту приложить. Ингаляцию можжевельником. Полынь перед сном 40 мл. выпить";
    }
}
