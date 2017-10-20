package ru.podelochki.otus.homework1;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	List<String> names = Lists.newArrayList("John", "Doe", "Foo");
    	System.out.print("Original names order=");
    	printNames(names);
        List<String> reversed = Lists.reverse(names);
        System.out.print("Reversed names order=");
        printNames(reversed);
    }
    private static void printNames(List<String> names) {
    	for (int i = 0; i< names.size() - 1; i++) {
    		System.out.print(names.get(i) + ", ");
    	}
    	System.out.println(names.get(names.size() - 1));
    }
}
