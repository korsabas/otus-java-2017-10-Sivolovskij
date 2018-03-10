package ru.podelochki.otus.homework16.services;

public interface ClientMessageService extends MessageService {
	void addReceiver(ServiceMessageHandler receiver);

}
