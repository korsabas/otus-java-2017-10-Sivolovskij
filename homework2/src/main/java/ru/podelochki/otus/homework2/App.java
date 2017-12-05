package ru.podelochki.otus.homework2;

import ru.podelochki.otus.homework2.utils.ObjectMeasurment;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	long result;
    	result = ObjectMeasurment.measure((new String()).getClass());
    	System.out.printf("String size = %d\n", result);
    	result = ObjectMeasurment.measure((new Object()).getClass());
    	System.out.printf("Object size = %d\n", result);
    }
}
