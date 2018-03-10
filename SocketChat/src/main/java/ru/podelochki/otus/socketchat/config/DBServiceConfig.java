package ru.podelochki.otus.socketchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.podelochki.otus.socketchat.handlers.DBMessageHandler;
import ru.podelochki.otus.socketchat.services.ClientMessageService;
import ru.podelochki.otus.socketchat.services.DBService;
import ru.podelochki.otus.socketchat.services.DBServiceHibernateImpl;
import ru.podelochki.otus.socketchat.services.ServiceMessageHandler;
import ru.podelochki.otus.socketchat.services.SocketMessageService;

@Configuration
public class DBServiceConfig {
	
	
	@Bean
	public ServiceMessageHandler serviceMessageHandler(DBService dbService, ClientMessageService clientMessageService) {
		return new DBMessageHandler(dbService, clientMessageService);
	}
	
	@Bean
	public DBService dbService() {
		return new DBServiceHibernateImpl();
	}
	
	@Bean
	public ClientMessageService clientMessageService() {
		return new SocketMessageService("localhost",8181);
	}

}
