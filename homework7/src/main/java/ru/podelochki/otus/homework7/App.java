package ru.podelochki.otus.homework7;

import java.util.ArrayList;
import java.util.List;

import ru.podelochki.otus.homework6.currencies.Note;
import ru.podelochki.otus.homework6.currencies.Rouble;
import ru.podelochki.otus.homework6.parts.Atm;
import ru.podelochki.otus.homework6.parts.Cassette;
import ru.podelochki.otus.homework7.parts.Departmant;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Departmant dep = new Departmant(3);
        dep.getAtm(0).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 7));
        dep.getAtm(0).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 7));
        dep.getAtm(1).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 6));
        dep.getAtm(1).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 6));
        dep.getAtm(2).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 1000), 5));
        dep.getAtm(2).loadCassette(new Cassette(new Note<Rouble>(Rouble.getInstance(), 100), 5));
        dep.getAtm(0).saveState();
        dep.getAtm(1).saveState();
        dep.getAtm(2).saveState();
        dep.getAtm(0).withdraw(Rouble.getInstance(), 1100);
        dep.getAtm(1).withdraw(Rouble.getInstance(), 2200);
        dep.getAtm(2).withdraw(Rouble.getInstance(), 3300);
        System.out.println("Amount Left at atm 0 = " + dep.getAtm(0).getAmountLeft().get(Rouble.getInstance()));
        System.out.println("Amount Left at atm 1 = " + dep.getAtm(1).getAmountLeft().get(Rouble.getInstance()));
        System.out.println("Amount Left at atm 2 = " + dep.getAtm(2).getAmountLeft().get(Rouble.getInstance()));
        dep.getAtm(0).restoreInitialState();
        dep.getAtm(1).restoreInitialState();
        dep.getAtm(2).restoreInitialState();
        System.out.println("Amount Left at atm 0 = " + dep.getAtm(0).getAmountLeft().get(Rouble.getInstance()));
        System.out.println("Amount Left at atm 1 = " + dep.getAtm(1).getAmountLeft().get(Rouble.getInstance()));
        System.out.println("Amount Left at atm 2 = " + dep.getAtm(2).getAmountLeft().get(Rouble.getInstance()));
    }
}
