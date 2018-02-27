package ru.podelochki.otus.homework14;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int[] array = {63, 92, 8, 65, 14, 78, 45, 88, 29, 13, 19, 47, 95,
        		38, 53, 4, 8, 53, 57, 62, 18, 69, 12, 76, 72, 89, 22, 72,
        		46, 13, 16, 23, 10, 4, 19, 72, 75, 25, 86, 64, 76, 22, 66};
        IntegerArraySorter sorter = new IntegerArraySorter();
        sorter.sort(array);
        for (int item: array) {
			System.out.print(item + ",");
		}
		System.out.println();
		
		int[] array2 = {92, 63};
		sorter.sort(array2);
        for (int item: array2) {
			System.out.print(item + ",");
		}
		System.out.println();
		int[] array3 = {63, 92, 8, 65, 14, 78, 45};
        sorter.sort(array3);
        for (int item: array3) {
			System.out.print(item + ",");
		}
		System.out.println();
    }
}
