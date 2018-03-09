package ru.podelochki.otus.homework16.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.podelochki.otus.homework16.handlers.DBMessageHandler;
import ru.podelochki.otus.homework16.handlers.ServiceMessageHandler;
import ru.podelochki.otus.homework16.services.ClientSocketService;
import ru.podelochki.otus.homework16.services.DBService;
import ru.podelochki.otus.homework16.services.DBServiceHibernateImpl;
import ru.podelochki.otus.homework16.services.SimpleClientSocketService;
import ru.podelochki.otus.homework16.services.SocketMessageHandler;

@Configuration
public class DBServiceConfig {
	
	
	@Bean
	public SocketMessageHandler socketMessageHandler(DBService dbService) {
		return new DBMessageHandler(dbService);
	}
	
	@Bean
	public DBService dbService() {
		return new DBServiceHibernateImpl();
	}
	
	@Bean
	public ClientSocketService clientSocketService(SocketMessageHandler handler) {
		return new SimpleClientSocketService(handler);
	}

}
