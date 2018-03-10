package ru.podelochki.otus.homework16.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ContextLoaderListener;

@WebListener
public class ServletContextListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        super.contextInitialized(arg0);
        System.out.println("---- initialize servlet context -----");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        super.contextDestroyed(arg0);
    }
} 
