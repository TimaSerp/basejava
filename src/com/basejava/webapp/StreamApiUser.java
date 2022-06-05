package com.basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamApiUser {
    public static void main(String[] args) {
        int[] ints = new int[]{2, 1, 8, 3, 2, 9};
        System.out.println(minValue(ints));
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(4);
        integers.add(7);
        integers.add(3);
        integers.add(8);
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (identity, num) -> identity += num * (int) Math.pow(10, Arrays.stream(values).distinct().count()
                        - Arrays.binarySearch(Arrays.stream(values).distinct().sorted().toArray(), num) - 1));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .filter(num -> integers.stream().reduce(0, Integer::sum) % 2 == 1 ? num % 2 == 0 : num % 2 == 1)
                .collect(Collectors.toList());
    }
}
