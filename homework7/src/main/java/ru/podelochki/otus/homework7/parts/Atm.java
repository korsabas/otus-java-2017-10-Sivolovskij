package ru.podelochki.otus.homework7.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.podelochki.otus.homework6.currencies.Currency;
import ru.podelochki.otus.homework6.exceptions.CouldNotWithdrawException;
import ru.podelochki.otus.homework6.parts.Cassette;
import ru.podelochki.otus.homework6.parts.CassetteSet;
import ru.podelochki.otus.homework6.parts.Dispenser;
import ru.podelochki.otus.homework6.parts.Transaction;
import ru.podelochki.otus.homework7.utils.AtmUtils;


public class Atm {
	private Dispenser dispenser;
	private Map<Currency, CassetteSet> cassetteMap;
	private List<Map<Currency, CassetteSet>> states = new ArrayList<>();
	public Atm() {
		cassetteMap = new HashMap<>();
		dispenser = new Dispenser(cassetteMap);
		
	}
	public Transaction withdraw(Currency currency, int amount) throws CouldNotWithdrawException {
		return new Transaction(Transaction.Type.WITHDRAW, currency, amount, dispenser.dispense(currency, amount));
	}
	public void loadCassettes(Collection<Cassette> cassettes) {
		for(Cassette cassette: cassettes) {
			loadCassette(cassette);
		}
	}
	public void loadCassette(Cassette cassette) {
		if (cassetteMap.containsKey(cassette.getNote().getCurrency())) {
			cassetteMap.get(cassette.getNote().getCurrency()).add(cassette);
		} else {
			CassetteSet cassetteSet = new CassetteSet();
			cassetteSet.add(cassette);
			cassetteMap.put(cassette.getNote().getCurrency(), cassetteSet);
		}
		
	}
	public boolean unloadCassette(Cassette cassette) {
		if (cassetteMap.containsKey(cassette.getNote().getCurrency())) {
			return cassetteMap.get(cassette.getNote().getCurrency()).remove(cassette);
		} else {
			return false;
		}
	}
	public Map<Currency,Integer> getAmountLeft() {
		Map<Currency,Integer> amountMap = new HashMap<>();
		for (Currency currency: cassetteMap.keySet()) {
			amountMap.put(currency, cassetteMap.get(currency).getTotalAmount());
		}
		return amountMap;
	}
	
	public void saveState() {
		states.add(AtmUtils.copyCassetteMap(this.cassetteMap));
	}
	public void restoreState(int stateNumber) {
		this.cassetteMap = (stateNumber > -1) && (stateNumber < states.size()) ? states.get(stateNumber): this.cassetteMap;
	}
	public void restoreInitialState() {
		restoreState(0);
	}
}
