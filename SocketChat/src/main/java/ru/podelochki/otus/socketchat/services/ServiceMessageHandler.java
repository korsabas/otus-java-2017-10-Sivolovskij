package ru.podelochki.otus.socketchat.services;

import ru.podelochki.otus.socketchat.messages.RegisterMessage;

public interface ServiceMessageHandler {
	RegisterMessage getRegisterMessage();
	void updateName(String name);
}
