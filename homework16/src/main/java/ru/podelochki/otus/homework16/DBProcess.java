package ru.podelochki.otus.homework16;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.podelochki.otus.homework16.config.DBServiceConfig;
import ru.podelochki.otus.homework16.config.MessageServiceConfig;
import ru.podelochki.otus.homework16.config.WebServiceConfig;
import ru.podelochki.otus.homework16.services.ServerSocketService;
import ru.podelochki.otus.homework16.services.ServiceMessageHandler;

/**
 * Hello world!
 *
 */
public class DBProcess 
{
	private static final String MODE = "-m";
    public static void main( String[] args ) throws IOException
    {
    	if (args.length == 0) {
    		initializeDatabaseService();
        } else
    	
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
    	ServiceMessageHandler service = ctx.getBean(ServiceMessageHandler.class);
    }
    private static void initializeWebService() {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext(WebServiceConfig.class);
    }
}
