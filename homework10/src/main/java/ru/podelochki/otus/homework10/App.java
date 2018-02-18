package ru.podelochki.otus.homework10;

import java.util.ArrayList;
import java.util.Arrays;

import ru.podelochki.otus.homework10.executors.ExecutorFactory;
import ru.podelochki.otus.homework10.models.AddressDataSet;
import ru.podelochki.otus.homework10.models.PhoneDataSet;
import ru.podelochki.otus.homework10.models.UsersDataSet;
import ru.podelochki.otus.homework10.services.DBService;
import ru.podelochki.otus.homework10.services.DBServiceHibernateImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String connectionUrl = "jdbc:mysql://localhost:3306/orm?user=root&password=password&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    	UsersDataSet userDataSet = new UsersDataSet();
        userDataSet.setAge(20);
        userDataSet.setName("Jhon");
        AddressDataSet address = new AddressDataSet();
        address.setAddress("address");
        PhoneDataSet phone = new PhoneDataSet();
        PhoneDataSet phone2 = new PhoneDataSet();
        phone.setPhone("12345678");
        phone2.setPhone("123456789");
        phone.setUser(userDataSet);
        phone2.setUser(userDataSet);
        userDataSet.setAddress(address);
        userDataSet.setPhones(new ArrayList<>(Arrays.asList(phone,phone2)));

        DBService dbService = ExecutorFactory.getExecutor(connectionUrl);

        dbService.save(userDataSet);

        UsersDataSet userDataSetLoaded = dbService.load(1, UsersDataSet.class);
        System.out.println(userDataSetLoaded);
        dbService.close();

        DBService dbServiceHibernate = new DBServiceHibernateImpl();
        dbServiceHibernate.save(userDataSet);

         userDataSetLoaded = dbServiceHibernate.load(userDataSet.getId(), UsersDataSet.class);
        System.out.println(userDataSetLoaded);
        dbServiceHibernate.close();
    }
}
