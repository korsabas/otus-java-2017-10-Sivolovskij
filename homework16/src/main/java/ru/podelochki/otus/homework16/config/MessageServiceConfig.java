package ru.podelochki.otus.homework16.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.podelochki.otus.homework16.services.MessageService;
import ru.podelochki.otus.homework16.services.SimpleMessageService;
import ru.podelochki.otus.homework16.services.SimpleMessageServiceHandler;
import ru.podelochki.otus.homework16.services.SimpleServerSocketService;
import ru.podelochki.otus.homework16.services.SocketMessageHandler;
import ru.podelochki.otus.homework16.services.ServerSocketService;

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
