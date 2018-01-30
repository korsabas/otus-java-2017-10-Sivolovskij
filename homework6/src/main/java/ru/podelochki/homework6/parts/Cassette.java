package ru.podelochki.homework6.parts;

import ru.podelochki.otus.homework6.currencies.Currency;
import ru.podelochki.otus.homework6.currencies.Note;

public class Cassette implements Comparable<Cassette> {
	private int counter;
	private Note<? extends Currency> note;
	
	public Cassette(Note<? extends Currency> note) {
		this.note = note;
		this.counter = 0;
	}
	public Cassette (Note<? extends Currency> note, int counter) {
		this.note = note;
		this.counter = counter;
	}
	public Note<? extends Currency> getNote() {
		return this.note;
	}
	public int getNotesLeft() {
		return counter;
	}
	public int withdrawNotes(int amount) {
		counter = counter - amount;
		return counter*note.getNominal();
	}
	@Override
	public int compareTo(Cassette cassette) {
		if (this.getNote().getNominal() > cassette.getNote().getNominal()) {
			return 1;
		} else if (this.getNote().getNominal() < cassette.getNote().getNominal()) {
			return -1;
		}
		return 0;
	}
}
