package ru.podelochki.otus.homework2;

import java.util.*;
import java.util.function.Supplier;

public class App {
    private static  final  int SIZE = 1000;

    public static void main(String[] args) {

        Integer[] array = new Integer[SIZE];
        Arrays.setAll(array, i -> i);

        printMemory(Object::new, "Empty Object");
        printMemory(String::new, "Empty String");
        printMemory(() -> new int[]{1,2}, "Int 2 objects");
        printMemory(() -> new int[]{1,2,3}, "Int 3 objects");
        printMemory(() -> new int[]{1,2,3,4,5}, "Int 5 objects");
        printMemory(() -> new int[]{1,2,3,4,5,6,7}, "Int 7 objects");
        printMemory(() -> new Integer[]{1,2}, "Integer 2 objects");
        printMemory(() -> new Integer[]{1,2,3}, "Integer 3 objects");
        printMemory(() -> new Integer[]{1,2,3,4,5}, "Integer 5 objects");
        printMemory(() -> new Integer[]{1,2,3,4,5,6,7}, "Integer 7 objects");
        printMemory(() -> {
            ArrayList<Integer> result = new ArrayList<>();
            Collections.addAll(result, array);
            return result;
        }, "ArrayList");
        printMemory(() -> {
            LinkedList<Integer> result = new LinkedList<>();
            Collections.addAll(result, array);
            return result;
        }, "LinkedList");
        printMemory(() -> {
            HashSet<Integer> result = new HashSet<>();
            Collections.addAll(result, array);
            return result;
        }, "HashSet");
        printMemory(() -> {
            TreeSet<Integer> result = new TreeSet<>();
            Collections.addAll(result, array);
            return result;
        }, "TreeSet");

    }

    private static <T> Object printMemory(Supplier<T> supplier, String comment){
        final Object[] objects = new Object[SIZE];
        Runtime runtime = Runtime.getRuntime();

        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        for (int i = 0; i < SIZE; i++){
            objects[i] = supplier.get();
        }

        runtime.gc();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println(String.format("\n%30s\tSizeof(): %d bytes\n", comment, Math.round((double) (memoryAfter-memoryBefore)/SIZE)));

        return objects;
    }

}
