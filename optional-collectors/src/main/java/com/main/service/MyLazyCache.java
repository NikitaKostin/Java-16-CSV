package com.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyLazyCache <T> {
    public static <T> Supplier<Stream<T>> persist(Stream<T> stream) {
        final List<List<T>> memoryList = new ArrayList<>();
        return () -> {
            if (memoryList.isEmpty()) {
                memoryList.add(stream.collect(Collectors.toList()));
            }
            return memoryList.get(0).stream();
        };
    }

}
