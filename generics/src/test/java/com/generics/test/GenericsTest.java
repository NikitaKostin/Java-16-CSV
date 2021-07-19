package com.generics.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.generics.main.GenericCollections.*;


public class GenericsTest {
    @Test
    public void containAllElementsArrayStringTest()  {
        var arrayList = new ArrayList<String>();
        arrayList.add("a");
        arrayList.add("b");
        Assert.assertTrue(containAllElements(arrayList, "a"));
    }

    @Test
    public void containAllElementsSetDoubleTest()  {
        var doubleTreeSet = new TreeSet<Double>();
        doubleTreeSet.add(1.0);
        doubleTreeSet.add(36.6);
        Assert.assertTrue(containAllElements(doubleTreeSet, 36.0));
    }

    @Test
    public void uniqueArrayListDoubleTest() {
        var a = new ArrayList<Double>();
        a.add(1.0);
        a.add(36.6);

        var b = new ArrayList<>(a);
        b.add(3.30);
        a.add(2.23);

        var resultUnique = unique(a, b);
        var resultTest  = Stream.concat(a.stream(),b.stream()).collect(Collectors.toSet()); ;
        var intersect = a.stream().filter(b::contains).collect(Collectors.toSet());
        resultTest.removeAll(intersect);
        var result = resultUnique.containsAll(resultTest) && resultUnique.size() == resultTest.size();

        Assert.assertTrue(result);
    }

    @Test
    public void mergeMapTest() {
        var a = new HashMap<Long, String>();
        var b = new HashMap<Long, String>();
        a.put(1L, "Tom");
        a.put(2L, "Jerry");
        b.put(3L, "Ann");
        b.put(2L, "Bob");

        var result = Stream.concat(a.entrySet().stream(), b.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue));
        var resultTest = mergeMap(a, b);
        Assert.assertEquals(result, resultTest);

    }
}
