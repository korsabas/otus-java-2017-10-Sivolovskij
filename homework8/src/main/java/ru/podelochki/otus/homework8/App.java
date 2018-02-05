package ru.podelochki.otus.homework8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ComplexClass test = new ComplexClass();
    	List<SimpleClass> list = Arrays.asList(new SimpleClass(4, "John", 'r'), new SimpleClass(5, "Mary", 't'), new SimpleClass(4, "Dow", 'y'));
    	test.setList(list);
    	test.setItems(new int[]{1,2,3});
    	test.setsClass(new SimpleClass(83, "Peter", 'q'));
        String jsonS = Serializer.getJson(test);
        Gson gson = new Gson();
        ComplexClass resultOrig = gson.fromJson(jsonS, ComplexClass.class);
        System.out.println(test.equals(resultOrig));

    }
}
