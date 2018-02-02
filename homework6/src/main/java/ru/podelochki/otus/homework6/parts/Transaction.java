package ru.podelochki.otus.homework6.parts;

import ru.podelochki.otus.homework6.currencies.Currency;

public class Transaction {
	public enum Type {WITHDRAW, DEPOSIT}
	private Type type;
	private Currency currency;
	private int amount;
	public Type getType() {
		return type;
	}

	public Currency getCurrency() {
		return currency;
	}

	public int getAmount() {
		return amount;
	}
	public Transaction(Type type, Currency currency, int amount) {
		super();
		this.type = type;
		this.currency = currency;
		this.amount = amount;
	}
	
}
