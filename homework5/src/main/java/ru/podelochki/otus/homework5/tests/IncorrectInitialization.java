package ru.podelochki.otus.homework5.tests;

import ru.podelochki.otus.homework5.annotations.After;
import ru.podelochki.otus.homework5.annotations.Before;

public class IncorrectInitialization {
	
	@Before
    public void setup() {
        System.out.println("after");
    }

    @After
    public void finish() {
        System.out.println("after");
    }
    
}
