package ru.podelochki.otus.homework16.handlers;

import java.io.IOException;

import ru.podelochki.otus.homework16.messages.ServiceMessage;

public interface ServiceMessageHandler {
	void getMessage() throws IOException;
	void sendMessage(ServiceMessage message) throws IOException;

}
