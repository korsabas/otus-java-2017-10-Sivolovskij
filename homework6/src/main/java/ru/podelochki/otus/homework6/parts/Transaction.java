package ru.podelochki.otus.homework6.parts;

import java.util.Map;

import ru.podelochki.otus.homework6.currencies.Currency;
import ru.podelochki.otus.homework6.currencies.Note;

public class Transaction {
	public enum Type {WITHDRAW, DEPOSIT}
	private Type type;
	private Currency currency;
	private int amount;
	Map<Note<?>, Integer> notesList;
	public Type getType() {
		return type;
	}

	public Currency getCurrency() {
		return currency;
	}

	public int getAmount() {
		return amount;
	}
	public Map<Note<?>, Integer> getNotesList() {
		return notesList;
	}
	public Transaction(Type type, Currency currency, int amount, Map<Note<?>, Integer> notesList) {
		super();
		this.type = type;
		this.currency = currency;
		this.amount = amount;
		this.notesList = notesList;
	}
}
