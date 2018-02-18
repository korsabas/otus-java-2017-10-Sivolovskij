package ru.podelochki.otus.homework10.executors;


import java.sql.Connection;
import java.sql.DriverManager;
import ru.podelochki.otus.homework10.services.DBService;
import ru.podelochki.otus.homework10.services.DBServiceMyImpl;


public class ExecutorFactory {
	
	private ExecutorFactory() {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
	}
	public static DBService getExecutor(String url) {
		//String dtoPath = null;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url);
		} catch (Exception e) {
            e.printStackTrace();
        }		
		return new DBServiceMyImpl(connection);
	}
	

}
