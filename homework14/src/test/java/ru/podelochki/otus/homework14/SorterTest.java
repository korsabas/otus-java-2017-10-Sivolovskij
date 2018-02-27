package ru.podelochki.otus.homework14;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class SorterTest {

    public Object[] params() {
        return $(
                $(new int[]{63, 92, 8, 65, 14, 78, 45, 88, 29, 13, 19, 47, 95,
                		38, 53, 4, 8, 53, 57, 62, 18, 69, 12, 76, 72, 89, 22, 72,
                		46, 13, 16, 23, 10, 4, 19, 72, 75, 25, 86, 64, 76, 22, 66}),
                $(new int[] {92, 63}),
                $(new int[] {63, 92, 8, 65, 14, 78, 45})
        );
    }

    @Test
    @Parameters(method = "params")
    public void shouldSort(int[] array) {
        int[] expected = Arrays.copyOf(array, array.length);
        Arrays.sort(expected);

        new IntegerArraySorter().sort(array);

        assertArrayEquals(expected, array);
    }
}
