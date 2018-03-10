package ru.podelochki.otus.socketchat.services;

public interface SocketMessageHandler {
	void onMessage(SocketSession session, String message);
	//void onCreateSession(SocketSession session);
}
