package ru.podelochki.otus.homework16.services;

public interface SocketMessageHandler {
	void onMessage(SocketSession session, String message);
	void onCreateSession(SocketSession session);
}
