package ru.podelochki.otus.socketchat.services;

import java.io.IOException;
import java.net.Socket;

public interface SocketSession {
	void sendMessage(String message) throws IOException;
	String readMessage() throws IOException;
}
