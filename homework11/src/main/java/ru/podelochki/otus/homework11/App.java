package ru.podelochki.otus.homework11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private static DBService dbServiceHibernate;
    public static void main( String[] args ) throws Exception
    {
    	prepareData();
    	dbServiceHibernate = new DBServiceHibernateImpl();

        createTimer();

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
    private static void createTimer() throws InterruptedException {

        Timer timer = new Timer();
        timer.schedule(createDataUsageTask(), 100, 100);

        Thread.sleep(5000);
        timer.cancel();

        dbServiceHibernate.close();
        dbServiceHibernate.printCacheInfo();
    }

    private static TimerTask createDataUsageTask() {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                	performDataUsage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private static void performDataUsage() throws InterruptedException {
    	user.setAge(user.getAge() + 1);
    	user.setName(user.getName() + 1);
        dbServiceHibernate.save(user);

        UsersDataSet userLoaded = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        System.out.println(userLoaded);
        userLoaded = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        System.out.println(userLoaded);
        System.gc();
        Thread.sleep(500);
    }
}
