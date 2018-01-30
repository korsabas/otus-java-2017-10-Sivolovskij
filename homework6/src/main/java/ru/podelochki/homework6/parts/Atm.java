package ru.podelochki.homework6.parts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ru.podelochki.otus.homework6.currencies.Currency;
import ru.podelochki.otus.homework6.exceptions.CouldNotWithdrawException;

public class Atm {
	private Dispenser dispenser;
	private Map<Currency, CassetteSet> cassetteMap;
	public Atm() {
		cassetteMap = new HashMap<>();
		dispenser = new Dispenser(cassetteMap);
		
	}
	public void withdraw(Currency currency, int amount) throws CouldNotWithdrawException {
		dispenser.dispense(currency, amount);
	}
	public void loadMoney(Collection<Cassette> cassettes) {
		for(Cassette cassette: cassettes) {
			loadMoney(cassette);
		}
	}
	public void loadMoney(Cassette cassette) {
		if (cassetteMap.containsKey(cassette.getNote().getCurrency())) {
			cassetteMap.get(cassette.getNote().getCurrency()).add(cassette);
		} else {
			CassetteSet cassetteSet = new CassetteSet();
			cassetteSet.add(cassette);
			cassetteMap.put(cassette.getNote().getCurrency(), cassetteSet);
		}
		
	}
}
