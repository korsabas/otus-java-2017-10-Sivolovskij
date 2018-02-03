package ru.podelochki.otus.homework7;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import ru.podelochki.otus.homework6.currencies.Note;
import ru.podelochki.otus.homework6.currencies.Rouble;
import ru.podelochki.otus.homework6.parts.Cassette;
import ru.podelochki.otus.homework7.parts.Departmant;

/**
 * Unit test for simple App.
 */
public class AppTest {
	Departmant dep;
	
	@Before
	public void setUp() {
		dep = new Departmant(3);
		dep.getAtm(0).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 7));
		dep.getAtm(0).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 7));
		dep.getAtm(1).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 6));
		dep.getAtm(1).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 6));
		dep.getAtm(2).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 5));
		dep.getAtm(2).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 5));
		dep.getAtm(0).saveState();
		dep.getAtm(1).saveState();
		dep.getAtm(2).saveState();
	}
	@Test
	public void shouldRestoreInitialStates() {
        dep.getAtm(0).withdraw(Rouble.getInstance(), 1100);
        dep.getAtm(1).withdraw(Rouble.getInstance(), 2200);
        dep.getAtm(2).withdraw(Rouble.getInstance(), 3300);
        assertEquals(6600, dep.getAtm(0).getAmountLeft().get(Rouble.getInstance()).intValue());
        assertEquals(4400, dep.getAtm(1).getAmountLeft().get(Rouble.getInstance()).intValue());
        assertEquals(2200, dep.getAtm(2).getAmountLeft().get(Rouble.getInstance()).intValue());
        dep.restoreInitialState();
        assertEquals(7700, dep.getAtm(0).getAmountLeft().get(Rouble.getInstance()).intValue());
        assertEquals(6600, dep.getAtm(1).getAmountLeft().get(Rouble.getInstance()).intValue());
        assertEquals(5500, dep.getAtm(2).getAmountLeft().get(Rouble.getInstance()).intValue());
	}
	@Test
	public void shouldGetTotalAmount() {
		assertEquals(19800, dep.getTotalAmountLeft().get(Rouble.getInstance()).intValue());
	}
	@Test
	public void incorrectStateNumber() {
		dep.getAtm(0).withdraw(Rouble.getInstance(), 1100);
		dep.getAtm(0).restoreState(-1);
		assertEquals(6600, dep.getAtm(0).getAmountLeft().get(Rouble.getInstance()).intValue());
		dep.getAtm(0).restoreState(7);
		assertEquals(6600, dep.getAtm(0).getAmountLeft().get(Rouble.getInstance()).intValue());
		dep.getAtm(0).restoreState(0);
		assertEquals(7700, dep.getAtm(0).getAmountLeft().get(Rouble.getInstance()).intValue());
	}
}
