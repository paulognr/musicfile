package com.paulognr;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    private RandomUtils(){}

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static Set<Integer> getRandomArray(int min, int max) {
        int size = max + 1 - min;
        Set<Integer> list = new LinkedHashSet<>();
        while (list.size() < size){
            list.add(getRandomInt(min, max));
        }
        return list;
    }
}
