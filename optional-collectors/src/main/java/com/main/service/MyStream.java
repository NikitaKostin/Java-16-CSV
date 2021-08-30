package com.main.service;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class MyStream {
    public static <T, R> Stream<R> mapWithIndex(Stream<T> stream, BiFunction<T, Integer, R> mapper){
        return stream
                .collect(HashMap<Integer, T>::new,
                        (map, streamValue) -> map.put(map.size(), streamValue),
                        (map, map2) -> {
                        })
                .entrySet()
                .stream()
                .map(value -> mapper.apply(value.getValue(), value.getKey()));
    }

}
