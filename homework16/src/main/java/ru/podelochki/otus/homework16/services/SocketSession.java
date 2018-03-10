package ru.podelochki.otus.homework16.services;

import java.io.IOException;
import java.net.Socket;

public interface SocketSession {
	void sendMessage(String message) throws IOException;
	Socket getSocket();
}
