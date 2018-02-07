package ru.podelochki.otus.homework9;

import ru.podelochki.otus.homework9.executors.DBExecutor;
import ru.podelochki.otus.homework9.executors.ExecutorFactory;
import ru.podelochki.otus.homework9.models.UserDataSet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String connectionUrl = "jdbc:mysql://localhost:3306/orm?user=root&password=password&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    	UserDataSet user = null;
        DBExecutor executor = ExecutorFactory.getExecutor(connectionUrl);
        UserDataSet ds = new UserDataSet("mike", 50);
        try {
        	executor.save(ds);
			user = executor.load(1, UserDataSet.class);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        executor.close();
        System.out.println("id=" + user.getId() + ", name=" + user.getName() + ", age=" + user.getAge());
    }
}
