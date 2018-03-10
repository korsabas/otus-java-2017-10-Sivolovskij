package ru.podelochki.otus.homework16;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import ru.podelochki.otus.homework16.config.DBServiceConfig;
import ru.podelochki.otus.homework16.config.MessageServiceConfig;
import ru.podelochki.otus.homework16.config.WebServiceConfig;
import ru.podelochki.otus.homework16.handlers.DBMessageHandler;
import ru.podelochki.otus.homework16.services.ClientSocketService;
import ru.podelochki.otus.homework16.services.DBService;
import ru.podelochki.otus.homework16.services.DBServiceHibernateImpl;
import ru.podelochki.otus.homework16.services.ServerSocketService;
import ru.podelochki.otus.homework16.services.SimpleClientSocketService;
import ru.podelochki.otus.homework16.services.SocketMessageHandler;
import ru.podelochki.otus.homework16.services.TemplateProcessor;
import ru.podelochki.otus.homework16.servlets.AutorizationFilter;
import ru.podelochki.otus.homework16.servlets.CacheServlet;
import ru.podelochki.otus.homework16.servlets.LoginServlet;
import ru.podelochki.otus.homework16.servlets.ServletContextListener;
import ru.podelochki.otus.homework16.servlets.StaticResourcesServlet;
import ru.podelochki.otus.homework16.servlets.WebSocketServlet;

/**
 * Hello world!
 *
 */
public class WebProcess2 
{
	private static final String MODE = "-m";
    public static void main( String[] args ) throws Exception
    {
    	if (args.length == 0) {
    		initializeWebService();
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
    	ClientSocketService service = ctx.getBean(ClientSocketService.class);
    	try {
			service.connect("127.0.0.1", 8181);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private static void initializeWebService() throws Exception {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext(WebServiceConfig.class);
    	
    	
    	Server server = new Server(8082);
    	ServletContextHandler servletCTX = new ServletContextHandler(ServletContextHandler.SESSIONS);
    	DBService dbServiceHibernate = new DBServiceHibernateImpl();
    	TemplateProcessor tp = new TemplateProcessor();
    	servletCTX.addEventListener(new ServletContextListener());
    	servletCTX.addFilter(AutorizationFilter.class, "/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
    	servletCTX.addServlet(new ServletHolder(CacheServlet.class), "/");
    	servletCTX.addServlet(new ServletHolder(LoginServlet.class), "/login");
    	servletCTX.addServlet(new ServletHolder(StaticResourcesServlet.class), "/resources/*");
    	servletCTX.setResourceBase("./");
    	servletCTX.setInitParameter("contextConfigLocation", "classpath*:**/WEB-INF/applicationContext.xml");
    	
    	
    	server.setHandler(servletCTX);
    	
    	ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(servletCTX);
    	wscontainer.addEndpoint(WebSocketServlet.class);
        server.start();
        server.join();
    }
}
