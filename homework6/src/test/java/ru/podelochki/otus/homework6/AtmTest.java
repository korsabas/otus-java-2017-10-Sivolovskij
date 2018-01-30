package ru.podelochki.otus.homework6;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import ru.podelochki.otus.homework6.currencies.Note;
import ru.podelochki.otus.homework6.currencies.Rouble;
import ru.podelochki.otus.homework6.exceptions.CouldNotWithdrawException;
import ru.podelochki.otus.homework6.parts.Atm;
import ru.podelochki.otus.homework6.parts.Cassette;
import ru.podelochki.otus.homework6.parts.CassetteSet;

public class AtmTest {
	@Test
    public void shouldCreateCassetteWithRoubles() {
		Cassette cassette = new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 4);
		assertEquals(Rouble.getInstance(), cassette.getNote().getCurrency());
    }
	@Test
    public void shouldCreateCassetteWithFourNotes() {
		Cassette cassette = new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 4);
		assertEquals(4, cassette.getNotesLeft());
    }
	@Test
    public void shouldCreateCassetteSetOrderedDescend() {
		Cassette cassette1000 = new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 4);
		Cassette cassette100 = new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 5);
		CassetteSet cassetteSet = new CassetteSet();
		cassetteSet.loadCassette(cassette1000);
		cassetteSet.loadCassette(cassette100);
		Iterator<Cassette> iterator = cassetteSet.iterator();
		assertTrue(iterator.next().getNote().getNominal() > iterator.next().getNote().getNominal());
    }
	@Test
    public void shouldLeftProperAmountOfNotesInCassettess() throws CouldNotWithdrawException {
		Atm atm = new Atm();
		Cassette cassette1000 = new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 5);
		Cassette cassette100 = new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 4);
        atm.loadMoney(cassette1000);
        atm.loadMoney(cassette100);
        atm.withdraw(Rouble.getInstance(), 2200);
        assertEquals(cassette1000.getNotesLeft(), 3);
        assertEquals(cassette100.getNotesLeft(), 2);
    }
	
	@Test(expected = CouldNotWithdrawException.class)
    public void shouldThrowExceptionOnTooLargeWithdrawRequest() throws CouldNotWithdrawException {
		Atm atm = new Atm();
        atm.loadMoney(new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 1));
        atm.loadMoney(new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 1));
        atm.withdraw(Rouble.getInstance(), 3100);
    }
	@Test
    public void cassettesNotesAmountShouldNoteBeChangedAfterWithdrawException(){
		Atm atm = new Atm();
		Cassette cassette1000 = new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 5);
		Cassette cassette100 = new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 4);
        atm.loadMoney(cassette1000);
        atm.loadMoney(cassette100);
        try {
        	atm.withdraw(Rouble.getInstance(), 6700);
        } catch (CouldNotWithdrawException e) {
        	
        }
        assertEquals(5, cassette1000.getNotesLeft());
        assertEquals(4, cassette100.getNotesLeft());
    }
	@Test
    public void shouldReturnTotalAmountLeft(){
		Atm atm = new Atm();
		Cassette cassette1000 = new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 5);
		Cassette cassette100 = new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 4);
        atm.loadMoney(cassette1000);
        atm.loadMoney(cassette100);
        assertEquals(5400, atm.getAmountLeft().get(Rouble.getInstance()).intValue());

    }

}
