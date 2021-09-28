package com.main.pattern;

import com.main.pattern.factory.DialogFactory;
import com.main.pattern.factory.DoctorDialog;
import com.main.pattern.factory.ShamanDialog;
import com.main.pattern.factory.WitchDoctorDialog;
import com.main.pattern.singleton.MySynchronizedSingleton;
import com.main.pattern.strategy.classicTemperature.FahrenheitStrategy;
import com.main.pattern.strategy.classicTemperature.KelvinStrategy;
import com.main.pattern.strategy.classicTemperature.ReaumurStrategy;
import com.main.pattern.strategy.classicTemperature.TemperatureStrategy;
import com.main.pattern.strategy.springTemperature.service.TemperatureSpringConverterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.stream.Stream;

import static com.main.pattern.strategy.enumTemperature.EnumTemperatureConverter.convertTemperatureEnum;
import static com.main.pattern.util.Util.getRandomNumber;
import static org.junit.Assert.assertSame;

@SpringBootTest
@RunWith( SpringJUnit4ClassRunner.class )
public class PatternsTest {
    @Autowired
    TemperatureSpringConverterService temperatureClassicConverterService;

    @Test
    public void singletonTest() {
        var singletonList = new ArrayList<MySynchronizedSingleton>();

        Stream.<Runnable>of(
                () -> {
                    try {
                        Thread.sleep(500);
                        var singleton = MySynchronizedSingleton.getInstance();
                        Thread.sleep(500);
                        singletonList.add(singleton);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                () -> {
                    try {
                        var singleton = MySynchronizedSingleton.getInstance();
                        Thread.sleep(500);
                        singletonList.add(singleton);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .parallel()
                .forEach(Runnable::run);
        assertSame(singletonList.get(0), singletonList.get(1));
    }

    @Test
    public void factoryTest() {
        var randomNumber = getRandomNumber(1, 3);
        DialogFactory dialogFactory;
        if (randomNumber == 1) {
            dialogFactory = new DoctorDialog();
        } else if (randomNumber == 2) {
            dialogFactory = new ShamanDialog();
        } else {
            dialogFactory = new WitchDoctorDialog();
        }
        dialogFactory.healMe();
    }

    @Test
    public void enumStrategyTest() {
        convertTemperatureEnum(11.4);
    }

    @Test
    public void classicStrategyTest() {
        TemperatureStrategy temperatureStrategy;
        var randomNumber = getRandomNumber(1, 3);
        if (randomNumber == 1) {
            temperatureStrategy = new FahrenheitStrategy();
        } else if (randomNumber == 2) {
            temperatureStrategy = new KelvinStrategy();
        } else {
            temperatureStrategy = new ReaumurStrategy();
        }

        System.out.println(temperatureStrategy.convertTemperatureFromCelsius(11.4));
    }

    @Test
    public void springStrategyTest() {
        temperatureClassicConverterService.convertTemperatureClassicStrategy(11.4);
    }

}
