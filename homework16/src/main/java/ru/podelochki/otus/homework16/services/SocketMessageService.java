package ru.podelochki.otus.homework16.services;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ru.podelochki.otus.homework16.messages.RegisterMessage;
import ru.podelochki.otus.homework16.messages.ServiceMessage;
import ru.podelochki.otus.homework16.messages.SocketMessage;

public class SocketMessageService implements ClientMessageService, SocketMessageHandler {
	private ClientSocketService socketService;
	private final Gson gson = new Gson();
	public SocketMessageService(String address, int port) {
		try {
			socketService = new SimpleClientSocketService(this);
			socketService.connect(address, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void putMessage(ServiceMessage message) {
		SocketMessage socketMessage = new SocketMessage(SocketMessage.SEND_MESSAGE);
		socketMessage.setPayload(gson.toJson(message));
		try {
			socketService.sendAyncMessage(socketMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Queue<ServiceMessage> getMessageQueue(String receiver) {
		Queue<ServiceMessage> queue = null;
		SocketMessage socketMessage = new SocketMessage(SocketMessage.RECEIVE_MESSAGE);
		//socketMessage.setPayload(gson.toJson(rMessage));
		try {
			SocketMessage response = socketService.sendSyncMessage(socketMessage);
			if (response.getAction().equals(SocketMessage.RESPONSE_MESSAGE)) {
				queue = gson.fromJson(response.getPayload(), new TypeToken<ConcurrentLinkedQueue<ServiceMessage>>(){}.getType());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return queue;
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
		RegisterMessage rMessage = receiver.getRegisterMessage();
		SocketMessage socketMessage = new SocketMessage(SocketMessage.REGISTER);
		socketMessage.setPayload(gson.toJson(rMessage));
		try {
			SocketMessage response = socketService.sendSyncMessage(socketMessage);
			if (response.getAction().equals(SocketMessage.REGISTER)) {
				receiver.updateName(gson.fromJson(response.getPayload(), RegisterMessage.class).getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onMessage(SocketSession session, String message) {
		// TODO Auto-generated method stub
		
	}

}
