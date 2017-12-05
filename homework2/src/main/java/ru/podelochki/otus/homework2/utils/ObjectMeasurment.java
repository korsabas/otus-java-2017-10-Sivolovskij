package ru.podelochki.otus.homework2.utils;

public class ObjectMeasurment {
	
	private static final int SIZE = 1000;
	public static <T> long measure(Class<T> testClass) {
		
		final Object[] array = new Object[SIZE];
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
		
		try {
			for (int i = 0; i < array.length; i++) {
				array[i] = testClass.newInstance();
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		runtime.gc();
		long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
		return Math.round((double)(memoryAfter - memoryBefore) / array.length);
		
	}
}
