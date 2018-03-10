package ru.podelochki.otus.socketchat.services;

import java.io.IOException;

public interface ServerSocketService {
	void start(int port) throws IOException;
	//void sendMessage(String Message);
}
