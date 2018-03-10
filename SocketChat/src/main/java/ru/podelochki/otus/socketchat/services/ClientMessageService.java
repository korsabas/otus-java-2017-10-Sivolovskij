package ru.podelochki.otus.socketchat.services;

public interface ClientMessageService extends MessageService {
	void addReceiver(ServiceMessageHandler receiver);

}
