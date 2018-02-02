package ru.podelochki.otus.homework6.parts;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ru.podelochki.otus.homework6.currencies.Currency;
import ru.podelochki.otus.homework6.currencies.Note;

public class CassetteSet extends TreeSet<Cassette>{
	
	private static final long serialVersionUID = 1L;
	private Map<Cassette, Integer> dispenseList = new HashMap<>();
	
	public CassetteSet() {
		super(Collections.reverseOrder());		
	}
	public void loadCassette(Cassette cassette) {
		this.add(cassette);
	}
	public boolean prepareToDispense(Cassette cassette, Integer quantity) {
		if (cassette.getNotesLeft() >= quantity) {
			dispenseList.put(cassette, quantity);
			return true;
		}
		return false;
	}
	public Map<Note<?>, Integer> dispense() {
		Set<Cassette> cassettes = dispenseList.keySet();
		Map<Note<?>, Integer> notesList = new HashMap<>();
		for (Cassette cassette: cassettes) {
			int quantity = dispenseList.get(cassette);
			notesList.put(cassette.getNote(), quantity);
			cassette.withdrawNotes(dispenseList.get(cassette));
			
		}
		restore();
		return notesList;
	}
	public void restore() {
		dispenseList = new HashMap<>();
	}
	public int getTotalAmount() {
		int total = 0;
		for(Cassette cassette: this) {
			total += cassette.getNotesLeft() * cassette.getNote().getNominal();
		}
		return total;
	}
}
