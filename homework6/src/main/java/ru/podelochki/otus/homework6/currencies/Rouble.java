package ru.podelochki.otus.homework6.currencies;

public class Rouble implements Currency{
	
	private static final Rouble rouble = new Rouble();
	private Rouble() {
	}
	@Override
	public String getName() {
		return "rouble";
	}

	public static Rouble getInstance() {
		return rouble;
	}

}
