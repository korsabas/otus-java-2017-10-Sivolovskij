package ru.podelochki.otus.homework16.services;

import java.io.IOException;

import ru.podelochki.otus.homework16.messages.SocketMessage;

public interface ClientSocketService {
	void connect(String address, int port) throws IOException;
	SocketMessage sendSyncMessage(SocketMessage message) throws IOException;
	SocketMessage sendAyncMessage(SocketMessage message) throws IOException;
}
