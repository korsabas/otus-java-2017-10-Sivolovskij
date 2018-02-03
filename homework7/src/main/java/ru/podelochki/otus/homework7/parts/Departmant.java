package ru.podelochki.otus.homework7.parts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.podelochki.otus.homework6.currencies.Currency;
import ru.podelochki.otus.homework7.exceptions.AtmNotEmptyException;

public class Departmant {
	private List<Atm> atms;
	Map<Currency, Integer> amountLeft;
	
	public Departmant() {
		atms = new ArrayList<>();
	}
	public Departmant(int initialNumber) {
		atms = new ArrayList<>(initialNumber);
		for (int i =0; i < initialNumber; i++) {
			atms.add(new Atm());
		}
	}
	
	public void addAtm() {
		atms.add(new Atm());
	}
	public void removeAtm(int index) {
		Atm atm = atms.get(index);
		for(Integer amount: atm.getAmountLeft().values()) {
			if (amount > 0) {
				throw new AtmNotEmptyException("Atm with index " + index + "is not empty");
			}
		}
		atms.remove(index);
	}
	public Atm getAtm(int index) {
		return atms.get(index);
	}
	public Map<Currency, Integer> getTotalAmountLeft() {
		amountLeft = new HashMap<>();
		for (Atm atm : atms) {
			Map<Currency, Integer> tmpMap = atm.getAmountLeft();
			for (Currency c: tmpMap.keySet()) {
				if(amountLeft.containsKey(c)) {
					int total = amountLeft.get(c) + tmpMap.get(c);
					amountLeft.put(c, total);
				} else {
					amountLeft.put(c, tmpMap.get(c));
				}
			}
		}
		return amountLeft;
	}
	public void restoreInitialState() {
		for (Atm atm: atms) {
			atm.restoreInitialState();
		}
	}
}
