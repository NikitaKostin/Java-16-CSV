package com.main;


import com.main.service.MyCollectors;
import com.main.service.MyLazyCache;
import com.main.service.MyStream;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    public static void main(String[] args) {
        // Задача 1
        MyStream.mapWithIndex(
                Stream.of("a1", "a2", "b1", "c2", "c1"), (a, b) -> b + "-" + a)
                .forEach(System.out::println
                );

        // Задача 2
        var result =
                Stream.of("Devoxx", "Voxxed Days", "Code One", "Basel One",
                        "Angular Connect")
                        .collect(MyCollectors.tripling(
                                Collectors.filtering((String n) -> n.contains("xx"), Collectors.toList()),
                                Collectors.filtering((String n) -> n.endsWith("One"), Collectors.toList()),
                                Collectors.filtering((String n) -> n.endsWith("Connect"), Collectors.toList()),
                                (List<String> list1, List<String> list2, List<String> list3) -> List.of(list1, list2, list3)
                        ));
        System.out.println(result);

        var result2 =
                Stream.of(5, 12, 19, 21)
                        .collect(MyCollectors.tripling(
                                Collectors.counting(),
                                Collectors.summingInt(n -> Integer.parseInt(n.toString())),
                                Collectors.maxBy(Integer::compareTo),
                                (count, sum, max) -> List.of(count, sum, max.orElse(-1))
                        ));
        System.out.println(result2);

        // Задача 3
        val list = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        val stream = list.stream()
                .filter(it -> {
                    System.out.println("Filter"); // Должно появиться ровно 10 раз за время выполнения
                    return it % 2 == 0;
                });

        // Данная операция НЕ должна вызвать фильтр
        val cachedStreamSupplier = MyLazyCache.persist(stream);
        // Вызов "Filter"
        System.out.println(cachedStreamSupplier.get().filter(it -> Math.random() < 0.5).count());
        // "Filter" НЕ должен вызваться повторно
        System.out.println(cachedStreamSupplier.get().count());
    }
}