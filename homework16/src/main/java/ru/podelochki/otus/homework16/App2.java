package ru.podelochki.otus.homework16;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.podelochki.otus.homework16.config.DBServiceConfig;
import ru.podelochki.otus.homework16.config.MessageServiceConfig;
import ru.podelochki.otus.homework16.config.WebServiceConfig;
import ru.podelochki.otus.homework16.services.ServerSocketService;

/**
 * Hello world!
 *
 */
public class App2 
{
	private static final String MODE = "-m";
    public static void main( String[] args ) throws IOException
    {
    	if (args.length == 0) {
    		initializeMessageService();
        }
    	
        if (args.length != 2 && !args[0].equals(MODE)) {
        	System.exit(1);
        }
        
        
        
    }
    private static void initializeMessageService() throws IOException {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext(MessageServiceConfig.class);
    	ServerSocketService service = ctx.getBean(ServerSocketService.class);
    	service.start(8181);
    }
    
    private static void initializeDatabaseService() {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext(DBServiceConfig.class);
    }
    private static void initializeWebService() {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext(WebServiceConfig.class);
    }
}
