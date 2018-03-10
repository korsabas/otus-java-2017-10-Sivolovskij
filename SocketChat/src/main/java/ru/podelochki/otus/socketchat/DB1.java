package ru.podelochki.otus.socketchat;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.podelochki.otus.socketchat.config.DBServiceConfig;
import ru.podelochki.otus.socketchat.config.MessageServiceConfig;
import ru.podelochki.otus.socketchat.config.WebServiceConfig;
import ru.podelochki.otus.socketchat.services.DBService;
import ru.podelochki.otus.socketchat.services.DBServiceHibernateImpl;
import ru.podelochki.otus.socketchat.services.ServerSocketService;
import ru.podelochki.otus.socketchat.services.TemplateProcessor;
import ru.podelochki.otus.socketchat.servlets.AutorizationFilter;
import ru.podelochki.otus.socketchat.servlets.CacheServlet;
import ru.podelochki.otus.socketchat.servlets.LoginServlet;
import ru.podelochki.otus.socketchat.servlets.ServletContextListener;
import ru.podelochki.otus.socketchat.servlets.StaticResourcesServlet;
import ru.podelochki.otus.socketchat.servlets.WebSocketServlet;

/**
 * Hello world!
 *
 */
public class DB1 
{
	private static final String MODE = "-m";
    public static void main( String[] args ) throws Exception
    {
    	initializeDatabaseService();
    }
    private static void initializeMessageService() throws IOException {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext(MessageServiceConfig.class);
    	ServerSocketService service = ctx.getBean(ServerSocketService.class);
    	service.start(8181);
    }
    
    private static void initializeDatabaseService() {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext(DBServiceConfig.class);
    }
    private static void initializeWebService(int serverPort) throws Exception {

    	Server server = new Server(serverPort);
    	ServletContextHandler servletCTX = new ServletContextHandler(ServletContextHandler.SESSIONS);
    	//DBService dbServiceHibernate = new DBServiceHibernateImpl();
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
