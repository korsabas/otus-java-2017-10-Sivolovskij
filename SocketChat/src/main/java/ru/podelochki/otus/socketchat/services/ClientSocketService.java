package ru.podelochki.otus.socketchat.services;

import java.io.IOException;

import ru.podelochki.otus.socketchat.messages.SocketMessage;

public interface ClientSocketService {
	void connect(String address, int port) throws IOException;
	SocketMessage sendSyncMessage(SocketMessage message) throws IOException;
	SocketMessage sendAyncMessage(SocketMessage message) throws IOException;
}
