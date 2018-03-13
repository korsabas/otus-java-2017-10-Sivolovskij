package ru.podelochki.otus.homework16;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;
import java.util.Map.Entry;

import javax.jws.WebService;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import ru.podelochki.otus.homework16.config.DBServiceConfig;
import ru.podelochki.otus.homework16.config.MessageServiceConfig;
import ru.podelochki.otus.homework16.config.WebServiceConfig;
import ru.podelochki.otus.homework16.services.DBService;
import ru.podelochki.otus.homework16.services.DBServiceHibernateImpl;
import ru.podelochki.otus.homework16.services.ServerSocketService;
import ru.podelochki.otus.homework16.services.SocketConnectionProperties;
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
public class MessageProcess 
{
	private static final String MODE = "-m";
    public static void main( String[] args ) throws Exception
    {
    	
    	String mode = null;
    	int mSPort = -1;
    	String mSHost = null;
    	int webPort = -1;
    	if (args.length == 0) {
    		ProcessBuilder pb = new ProcessBuilder("java", "-jar", "homework16.jar", "-m", "messageService", "-h", "localhost", "-p", "8181");
    		Process process = pb.start();
    		System.out.println("Starting message");
    		pb = new ProcessBuilder("java", "-jar", "homework16.jar", "-m", "dbservice", "-h", "localhost", "-p", "8181");
    		process = pb.start();
    		System.out.println("Starting dbservice");
    		pb = new ProcessBuilder("java", "-jar", "homework16.jar", "-m", "dbservice", "-h", "localhost", "-p", "8181");
    		process = pb.start();
    		System.out.println("Starting dbservice");
    		pb = new ProcessBuilder("java", "-jar", "homework16.jar", "-m", "webfront", "-h", "localhost", "-p", "8181", "-wp", "8081");
    		process = pb.start();
    		System.out.println("Starting webfront");
    		pb = new ProcessBuilder("java", "-jar", "homework16.jar", "-m", "webfront", "-h", "localhost", "-p", "8181", "-wp", "8082");
    		process = pb.start();
    		System.out.println("Starting webfront");


    		
    		
        } else{
    	
        for (int i = 0; i < args.length; i++) {
        	switch (args[i]){
        	case "-m":  mode = args[i + 1]; break;
        	case "-h": mSHost = args[i + 1]; break;
        	case "-p": mSPort = Integer.valueOf(args[i + 1]); break;
        	case "-wp": webPort = Integer.valueOf(args[i + 1]); break;
        	}
        }
        if (mode.equals("messageService")) {
        	initializeMessageService();
        } else
        if (mode.equals("dbservice")) {
        	initializeDatabaseService();
        } else if (mode.equals("webfront")) {
        	initializeWebService(mSHost, mSPort, webPort);
        }    
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
    private static void initializeWebService(String msHost, int msport, int serverPort) throws Exception {

    	XmlWebApplicationContext ctx = new XmlWebApplicationContext();
    	ctx.setConfigLocation("classpath*:**/WEB-INF/applicationContext.xml");
    	ctx.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor(){
    		
    		@Override
    		public void postProcessBeanFactory(ConfigurableListableBeanFactory bf)
    		        throws BeansException {

    		    SocketConnectionProperties scp = bf.getBean(SocketConnectionProperties.class);
    		    
    		    scp.setHost(msHost);
    		    scp.setPort(msport);
    		    
    		}

    	});
    	Server server = new Server(serverPort);
    	ServletContextHandler servletCTX = new ServletContextHandler(ServletContextHandler.SESSIONS);
    	TemplateProcessor tp = new TemplateProcessor();
    	
    	servletCTX.addEventListener(new ContextLoaderListener(ctx));
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
