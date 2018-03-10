package ru.podelochki.otus.homework16.services;

import java.io.IOException;
import java.util.Queue;

import ru.podelochki.otus.homework16.messages.ServiceMessage;

public class SocketMessageService implements ClientMessageService, SocketMessageHandler {
	
	public SocketMessageService(ClientSocketService socketService, String address, int port) {
		try {
			socketService.connect(address, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void putMessage(ServiceMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Queue<ServiceMessage> getMessageQueue(String receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addReceiver(String receiver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeReceiver(String receiver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReceiver(ServiceMessageHandler receiver) {

		
	}

	@Override
	public void onMessage(SocketSession session, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCreateSession(SocketSession session) {
		// TODO Auto-generated method stub
		
	}
	

}
