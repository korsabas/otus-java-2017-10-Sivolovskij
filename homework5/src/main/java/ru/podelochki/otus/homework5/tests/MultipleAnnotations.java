package ru.podelochki.otus.homework5.tests;

import ru.podelochki.otus.homework5.annotations.After;
import ru.podelochki.otus.homework5.annotations.Before;
import ru.podelochki.otus.homework5.annotations.Test;

public class MultipleAnnotations {

    @Before
    public void setup() {
        System.out.println("before");
    }
    @After
    public void finish() {
        System.out.println("after");
    }
    @Test
    @After
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void test3() {
        System.out.println("test3");
    }

    
}
