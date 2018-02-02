package ru.podelochki.otus.homework6.parts;

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
	public Transaction withdraw(Currency currency, int amount) throws CouldNotWithdrawException {
		dispenser.dispense(currency, amount);
		return new Transaction(Transaction.Type.WITHDRAW, currency, amount);
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
	public Map<Currency,Integer> getAmountLeft() {
		Map<Currency,Integer> amountMap = new HashMap<>();
		for (Currency currency: cassetteMap.keySet()) {
			amountMap.put(currency, cassetteMap.get(currency).getTotalAmount());
		}
		return amountMap;
	}
}
