package ru.podelochki.otus.homework12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;

import ru.podelochki.otus.homework12.jetty.Autorization;
import ru.podelochki.otus.homework12.jetty.CacheServlet;
import ru.podelochki.otus.homework12.models.AddressDataSet;
import ru.podelochki.otus.homework12.models.PhoneDataSet;
import ru.podelochki.otus.homework12.models.UsersDataSet;
import ru.podelochki.otus.homework12.services.DBService;
import ru.podelochki.otus.homework12.services.DBServiceHibernateImpl;

public class App 
{
	private static final int JETTY_PORT = 8080;
	
	private static UsersDataSet user;
    private static AddressDataSet address;
    private static PhoneDataSet phone1;
    private static PhoneDataSet phone2;
    private static DBService dbServiceHibernate = new DBServiceHibernateImpl();
    
    public static void main( String[] args ) throws Exception
    {
    	Server server = new Server(JETTY_PORT);
    	
    	LoginService loginService = new HashLoginService("MyRealm", "src/main/resources/security.properties");
        server.addBean(loginService);
        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        

        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[] { "user", "admin" });
        ConstraintMapping mapping = new ConstraintMapping();
        
        mapping.setPathSpec("/*");
        mapping.setConstraint(constraint);
        security.setConstraintMappings(Collections.singletonList(mapping));
        FormAuthenticator authenticator = new FormAuthenticator("/login", "", true);

        security.setAuthenticator(authenticator);
        security.setLoginService(loginService);
    	ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);
    	ctx.setContextPath("/");
    	ctx.setSecurityHandler(security);

    	
        ctx.addServlet(Autorization.class, "/login");
        ctx.addServlet(new ServletHolder(new CacheServlet(dbServiceHibernate)), "/");


        
        server.setHandler(ctx);
        server.start();
        //server.dumpStdErr();
        prepareData();
        createTimer();
        server.join();
    }
    
    private static void prepareData() {
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

    private static void createTimer() throws InterruptedException {

        Timer timer = new Timer();
        timer.schedule(createDataUsageTask(), 0, 1000);

        Thread.sleep(20000);
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
        dbServiceHibernate.save(user);

        UsersDataSet userLoaded = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        System.out.println(userLoaded);
        userLoaded = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        System.out.println(userLoaded);
        System.gc();
        Thread.sleep(1000);
        userLoaded = dbServiceHibernate.load(user.getId(), UsersDataSet.class);
        System.out.println(userLoaded);
    }
}
