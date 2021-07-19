package com.generics.main;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericCollections {
    @SafeVarargs
    public static <T> boolean containAllElements(Collection<T> collection, T... elements) {
        return collection.containsAll(Arrays.asList(elements));
    }

    public static <T> Collection<T> unique(Collection<T> a, Collection<T> b) {
        var union = new HashSet<>(a);
        var tempA = new HashSet<>(a);
        union.addAll(b);
        tempA.retainAll(b);
        union.removeAll(tempA);
        return union;

    }

    public static <K, V> Map<K, V> mergeMap(Map<K, V> a, Map<K, V> b) {
        return Stream.of(a, b)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue));
    }
}
