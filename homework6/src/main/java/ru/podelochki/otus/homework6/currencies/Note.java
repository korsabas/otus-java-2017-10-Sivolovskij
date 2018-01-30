package ru.podelochki.otus.homework6.currencies;

public class Note<T extends Currency> implements Comparable<Note<T>>{
	private int nominal;
	private T currency;
	public Note(T currency, int nominal){
		this.nominal = nominal;
		this.currency = currency;
	}
	public int getNominal() {
		return nominal;
	}
	@Override
	public int compareTo(Note<T> note) {
		if (this.nominal > note.getNominal()) {
			return 1;
		} else if (this.nominal < note.getNominal()) {
			return -1;
		}
		return 0;
	}

	public Currency getCurrency() {
		return this.currency;
	}
}
