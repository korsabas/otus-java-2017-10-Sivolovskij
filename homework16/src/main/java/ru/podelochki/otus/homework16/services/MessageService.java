package ru.podelochki.otus.homework16.services;

import java.util.Queue;

import ru.podelochki.otus.homework16.messages.ServiceMessage;

public interface MessageService {

	void putMessage(ServiceMessage message);
	Queue<ServiceMessage> getMessageQueue(String receiver);
	void addReceiver(String receiver);
	void removeReceiver(String receiver);
}
