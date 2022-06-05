package com.basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreamApiUser {
    public static void main(String[] args) {
        int[] ints = new int[]{2, 1, 3, 3, 2, 3};
        System.out.println(minValue(ints));
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(5);
        integers.add(7);
        integers.add(3);
        integers.add(8);
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        int[] ints = Arrays.stream(values)
                .distinct()
                .sorted()
                .toArray();
        return Arrays.stream(ints)
                .reduce(0, (identity, num) -> identity += num * (int) Math.pow(10, ints.length - Arrays.binarySearch(ints, num) - 1));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = (int) integers.stream()
                .reduce(0, Integer::sum);
        List<Integer> integersCopy = new ArrayList<>(integers);
        int checkOddOrEven = sum % 2 == 0 ? 0 : 1;
        integersCopy.stream()
                .forEach(num -> {
                    if (num % 2 == checkOddOrEven) integers.remove(num);
                });
        return integers;
    }
}
