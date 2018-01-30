package ru.podelochki.otus.homework6;

import ru.podelochki.otus.homework6.currencies.Note;
import ru.podelochki.otus.homework6.currencies.Rouble;
import ru.podelochki.otus.homework6.parts.Atm;
import ru.podelochki.otus.homework6.parts.Cassette;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Atm atm = new Atm();
        atm.loadMoney(new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 4));
        atm.loadMoney(new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 1));
        atm.withdraw(Rouble.getInstance(), 3100);
    }
}
