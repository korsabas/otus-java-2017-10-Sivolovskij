package ru.podelochki.otus.homework8;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

/**
 * Unit test for simple App.
 */
public class AppTest {
	ComplexClass testComplex;
	SimpleClass testSimple;
	List<SimpleClass> list;
	Gson gson;
	@Before
	public void setUp() {
		testComplex = new ComplexClass();
    	list = Arrays.asList(new SimpleClass(4, "John", 'r'), new SimpleClass(5, "Mary", 't'), new SimpleClass(4, "Dow", 'y'));
    	testComplex.setList(list);
    	testComplex.setItems(new int[]{1,2,3});
    	testSimple = new SimpleClass(83, "Peter", 'q');
    	testComplex.setsClass(testSimple);
    	gson = new Gson();
	}
	@Test
    public void shouldSerializeSimpleObject() {
        String json = Serializer.getJson(testSimple);

        SimpleClass gsonTestSimple = gson.fromJson(json, SimpleClass.class);
        assertEquals(testSimple, gsonTestSimple);
    }
	@Test
    public void shouldSerializeComplexObject() {
		String json = Serializer.getJson(testComplex);
		ComplexClass gsonTestComplex = gson.fromJson(json, ComplexClass.class);
        assertEquals(testComplex, gsonTestComplex);
    }
	@Test
    public void shouldSerializeArrayOfPrimitives() {
        int[] array = {1, 2, 3, 4};
        String json = Serializer.getJson(array);
        int[] gsonArray = gson.fromJson(json, int[].class);
        assertArrayEquals(array, gsonArray);
    }
	@Test
    public void shouldSerializeArrayOfStrings() {
        String[] array = {"1", "2", "3", "4"};
        String json = Serializer.getJson(array);
        String[] gsonArray = gson.fromJson(json, String[].class);
        assertArrayEquals(gsonArray, array);
    }

    @Test
    public void shouldSerializeArrayOfIntegers() {
        Integer[] array = {1, 2, 3, 4};
        String json = Serializer.getJson(array);
        Integer[] gsonArray = gson.fromJson(json, Integer[].class);
        assertArrayEquals(gsonArray, array);
    }

    @Test
    public void shouldSerializeInteger() {
        Integer integer = new Integer(1);
        String json = Serializer.getJson(integer);
        Integer gsonInteger = gson.fromJson(json, Integer.class);
        assertEquals(gsonInteger, integer);
    }

}
