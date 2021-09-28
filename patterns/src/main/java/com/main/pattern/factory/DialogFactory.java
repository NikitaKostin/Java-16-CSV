package com.main.pattern.factory;

public abstract class DialogFactory {
    public void healMe() {

        Healer healer = createHealer();
        System.out.println("Держи лекарства");
        System.out.println(healer.medication());
        System.out.println("Сдедуй этим указаниям");
        System.out.println(healer.giveCourseOfTreatment());
    }

    public abstract Healer createHealer();
}
