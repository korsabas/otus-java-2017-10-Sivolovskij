package ru.podelochki.otus.homework15.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.podelochki.otus.homework15.models.AddressDataSet;
import ru.podelochki.otus.homework15.models.PhoneDataSet;
import ru.podelochki.otus.homework15.models.UsersDataSet;

//@Component
public class DataHandler 
{
	private UsersDataSet user;
    private AddressDataSet address;
    private PhoneDataSet phone1;
    private PhoneDataSet phone2;
    
    
    private DBService dbServiceHibernate;
    
    @Autowired
    public DataHandler(DBService dbServiceHibernate) {
    	this.dbServiceHibernate = dbServiceHibernate;
    	prepareData();
        try {
			createTimer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
	private void prepareData() {
        user = new UsersDataSet();
        user.setAge(1);
        user.setName("ANY_NAME");
        address = new AddressDataSet();
        address.setAddress("ANY_ADDRESS");
        phone1 = new PhoneDataSet();
        phone1.setPhone("12345");
        phone1.setUser(user);
        phone2 = new PhoneDataSet();
        phone2.setPhone("54321");
        phone2.setUser(user);
        user.setAddress(address);
        user.setPhones(new ArrayList<>(Arrays.asList(phone1, phone2)));
    }

    private void createTimer() throws InterruptedException {

        Timer timer = new Timer();
        timer.schedule(createDataUsageTask(), 0, 1000);
    }

    private TimerTask createDataUsageTask() {
        return new TimerTask() {
        	private int actionCounter;
            @Override
            public void run() {
                try {
                	performDataUsage();
                	if (actionCounter>10) {
                		this.cancel();
                	}
                	actionCounter++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void performDataUsage() throws InterruptedException {
        dbServiceHibernate.save(user);

        UsersDataSet userLoaded = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        //System.out.println(userLoaded);
        userLoaded = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        //System.out.println(userLoaded);
        System.gc();
        Thread.sleep(1000);
        userLoaded = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
       // System.out.println(userLoaded);
    }
}
