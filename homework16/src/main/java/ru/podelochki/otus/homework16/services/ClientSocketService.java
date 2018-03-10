package ru.podelochki.otus.homework16.services;

import java.io.IOException;

public interface ClientSocketService {
	void connect(String address, int port) throws IOException;
}
