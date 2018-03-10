package ru.podelochki.otus.homework16.services;

import ru.podelochki.otus.homework16.messages.RegisterMessage;

public interface ServiceMessageHandler {
	RegisterMessage getRegisterMessage();
}
