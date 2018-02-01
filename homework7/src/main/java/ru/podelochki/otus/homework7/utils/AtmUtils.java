package ru.podelochki.otus.homework7.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ru.podelochki.otus.homework6.currencies.Currency;
import ru.podelochki.otus.homework6.parts.Cassette;
import ru.podelochki.otus.homework6.parts.CassetteSet;

public class AtmUtils {
	public static Map<Currency, CassetteSet> copyCassetteMap(Map<Currency, CassetteSet> cassetteMap) {
		Map<Currency, CassetteSet> newCassetteMap = new HashMap<>();
		for (Entry<Currency, CassetteSet> cEntry: cassetteMap.entrySet()) {
			CassetteSet tmpSet = new CassetteSet();
			for(Cassette cassette: cEntry.getValue()) {
				tmpSet.add(new Cassette(cassette.getNote(), cassette.getNotesLeft()));
			}
			newCassetteMap.put(cEntry.getKey(), tmpSet);
		}
		return newCassetteMap;
	}
}
