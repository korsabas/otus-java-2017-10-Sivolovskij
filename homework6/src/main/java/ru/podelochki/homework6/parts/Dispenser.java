package ru.podelochki.homework6.parts;

import java.util.Map;

import ru.podelochki.otus.homework6.currencies.Currency;
import ru.podelochki.otus.homework6.exceptions.CouldNotWithdrawException;

public class Dispenser {
	private Map<Currency, CassetteSet> cassetteMap;
	public Dispenser(Map<Currency, CassetteSet> cassetteMap) {
		this.cassetteMap = cassetteMap;
	}
	public void dispense(Currency currency, int amount) throws CouldNotWithdrawException {
		CassetteSet cSet = cassetteMap.get(currency);
		cSet.restore();
		for (Cassette c : cSet) {
			 int count = amount / c.getNote().getNominal();
			 if (!cSet.prepareToDispense(c, count)) {
				 cSet.restore();
				 throw new CouldNotWithdrawException("Requested amount could not be withdrawn");
			 }
			 amount -= count * c.getNote().getNominal();
		}
		cSet.dispense();
	}
}
