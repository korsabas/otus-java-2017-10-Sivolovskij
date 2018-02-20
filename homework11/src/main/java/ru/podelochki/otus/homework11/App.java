package ru.podelochki.otus.homework11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.podelochki.otus.homework11.models.AddressDataSet;
import ru.podelochki.otus.homework11.models.PhoneDataSet;
import ru.podelochki.otus.homework11.models.UsersDataSet;
import ru.podelochki.otus.homework11.services.DBService;
import ru.podelochki.otus.homework11.services.DBServiceHibernateImpl;

/**
 * Hello world!
 *
 */
public class App 
{
	private static UsersDataSet user;
    private static AddressDataSet address;
    private static PhoneDataSet phone1;
    private static PhoneDataSet phone2;
    public static void main( String[] args ) throws Exception
    {
    	prepareData();
    	DBService dbServiceHibernate = new DBServiceHibernateImpl();
        dbServiceHibernate.save(user);

        UsersDataSet userFromDb = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        System.out.println(userFromDb);
        userFromDb = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        System.out.println(userFromDb);
        System.gc();
        Thread.sleep(1000);
        userFromDb = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        System.out.println(userFromDb);
        dbServiceHibernate.close();
        dbServiceHibernate.printCacheInfo();
    }
    private static void prepareData() {
        user = new UsersDataSet();
        user.setAge(20);
        user.setName("Jhon");
        address = new AddressDataSet();
        address.setAddress("ul. Pushkina, dom Kolotushkina");
        phone1 = new PhoneDataSet();
        phone1.setPhone("12345");
        phone1.setUser(user);
        phone2 = new PhoneDataSet();
        phone2.setPhone("54321");
        phone2.setUser(user);
        user.setAddress(address);
        user.setPhones(new ArrayList<>(Arrays.asList(phone1, phone2)));
    }
}
