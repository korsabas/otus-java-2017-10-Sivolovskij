package ru.podelochki.otus.homework16.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ru.podelochki.otus.homework16.services.WebSocketMessageHandler;

@WebListener
public class ServletContextListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        super.contextInitialized(arg0);
        //SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(new WebSocketMessageHandler(host, port));
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        super.contextDestroyed(arg0);
    }
} 
