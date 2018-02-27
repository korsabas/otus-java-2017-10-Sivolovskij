package ru.podelochki.otus.homework14;

import java.util.Arrays;

public class IntegerArraySorter {
	private int numberOfThreads = 4;
	private int[] result;
	public IntegerArraySorter() {
	}
	
	public IntegerArraySorter(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}
	
	public void sort(int[] array) {
		if (array == null) {
			return;
		}
		result = null;
		
		int range = array.length / numberOfThreads;
		
		if (array.length <= numberOfThreads) {
			new Sorter(Arrays.copyOfRange(array, 0, array.length)).run();
			System.arraycopy(result, 0, array, 0, result.length);
			return;
		}
		
		if ((array.length % numberOfThreads) > 0) {
			range++;
		}
		
		int startIndex = 0;
		int endIndex = 0;
		int createdThreads = numberOfThreads;
		if (array.length < numberOfThreads) {
			createdThreads = 1;
		}
		
		Thread[] sorterThreads = new Thread[createdThreads];
		for (int i = 0; i < sorterThreads.length; i++) {
			startIndex = endIndex;
			endIndex = startIndex + range + 1;
			if (endIndex > array.length) {
				endIndex = array.length;
			}
			Sorter sorter = new Sorter(Arrays.copyOfRange(array, startIndex, endIndex));
			sorterThreads[i] = new Thread(sorter, "Sorter" + i);
			sorterThreads[i].start();
		}
		
		
		for (int i = 0; i < sorterThreads.length; i++) {
			try {
				sorterThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.arraycopy(result, 0, array, 0, result.length);
	}
	
	private synchronized void merge(int[] chunk) {
		if (result == null) {
			result = chunk;
		} else {
			int[] mergeArray = new int[result.length + chunk.length];
			System.arraycopy(result, 0, mergeArray, 0, result.length);
			System.arraycopy(chunk, 0, mergeArray, result.length, chunk.length);
			Mergesort mSorter = new Mergesort();
			mSorter.sort(mergeArray);
			result = mergeArray;
		}
	}

	
	private class Sorter implements Runnable {
		private int[] chunk;
		public Sorter(int[] chunk) {
			this.chunk = chunk;
		}
		
		public void run() {
			Arrays.sort(chunk);
			IntegerArraySorter.this.merge(chunk);
		}
	}
}
