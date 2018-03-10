package ru.podelochki.otus.socketchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.podelochki.otus.socketchat.services.MessageService;
import ru.podelochki.otus.socketchat.services.ServerSocketService;
import ru.podelochki.otus.socketchat.services.SimpleMessageService;
import ru.podelochki.otus.socketchat.services.SimpleMessageServiceHandler;
import ru.podelochki.otus.socketchat.services.SimpleServerSocketService;
import ru.podelochki.otus.socketchat.services.SocketMessageHandler;

@Configuration
public class MessageServiceConfig {
	
	@Bean
	public MessageService messageService() {
		return new SimpleMessageService();
	}
	@Bean
	public SocketMessageHandler socketMessageHandler(MessageService mService) {
		return new SimpleMessageServiceHandler(mService);
	}
	
	@Bean
	public ServerSocketService socketService(SocketMessageHandler socketMessageHandler) {
		return new SimpleServerSocketService(socketMessageHandler);
	}

}
