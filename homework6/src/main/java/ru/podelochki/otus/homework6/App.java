package ru.podelochki.otus.homework6;

import java.util.Map.Entry;

import ru.podelochki.otus.homework6.currencies.Note;
import ru.podelochki.otus.homework6.currencies.Rouble;
import ru.podelochki.otus.homework6.parts.Atm;
import ru.podelochki.otus.homework6.parts.Cassette;
import ru.podelochki.otus.homework6.parts.Transaction;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Atm atm = new Atm();
        atm.loadMoney(new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 4));
        atm.loadMoney(new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 1));
        Transaction transaction = atm.withdraw(Rouble.getInstance(), 3100);
        System.out.println("Dispensed:" +  + transaction.getAmount() + " " + transaction.getCurrency().getName());
        System.out.println("Details:");
        for (Entry<Note<?>, Integer> notePack : transaction.getNotesList().entrySet()) {
        	System.out.println("\t" + notePack.getKey().getNominal() + " " + notePack.getKey().getCurrency().getName() + "-" + notePack.getValue());
        }
    }
}
