package ru.podelochki.otus.homework3;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import ru.podelochki.otus.homework3.collections.MyArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MyArrayListTest {
    
	private MyArrayList<Integer> myArray = new MyArrayList<>();;

    public Object[] testElems() {
        return new Object[] {
                new Object[] {3},
                new Object[] {3, 34},
                new Object[] {1, 3, 2, 4, 4, 1, 4, 12, 0, -10, 1, 3, 2, 4, 4, 1, 4, 12, 0, -10, 1, 3, 2, 4, 4, 1, 4, 12, 0, -10},
        };
    }

    @Test
    @Parameters(method = "testElems")
    public void shouldContainAllAddedElements(Integer[] elems) {
        Collections.addAll(myArray, elems);
        assertThat(myArray, contains(elems));
    }

    @Test
    @Parameters(method = "testElems")
    public void shouldExpand(Integer[] elems) {
        myArray = new MyArrayList<>(1);
        Collections.addAll(myArray, elems);
        assertThat(myArray, contains(elems));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnIncorrectAllocatedSize() {
        myArray = new MyArrayList<>(0);
    }

    @Test
    @Parameters(method = "testElems")
    public void shouldContainAllElemsFromSrcAfterCopy(Integer[] elems) {
        MyArrayList<Integer> src = new MyArrayList<>();
        for (Integer ignored : elems)
            myArray.add(1);
        Collections.addAll(src, elems);
        Collections.copy(myArray, src);
        assertThat(myArray, contains(elems));
    }

    @Test
    public void shouldBeEmptyAfterCopyFromEmptyList() {
        MyArrayList<Integer> src = new MyArrayList<>();
        Collections.copy(myArray, src);
        assertThat(myArray, empty());
    }

    @Test
    @Parameters(method = "testElems")
    public void shouldBeSortedAsArrayList(Integer[] unsortedElems) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, unsortedElems);
        Collections.addAll(myArray, unsortedElems);
        Collections.sort(arrayList, Comparator.naturalOrder());
        Collections.sort(myArray, Comparator.naturalOrder());
        Object[] sortedElems = arrayList.toArray();
        assertThat(myArray, contains(sortedElems));
    }

    @Test
    public void emptyListShouldRemainEmptyAfterSort() {
        Collections.sort(myArray, Comparator.naturalOrder());
        assertThat(myArray, empty());
    }
}
