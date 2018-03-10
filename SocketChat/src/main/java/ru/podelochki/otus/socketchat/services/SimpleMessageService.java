package ru.podelochki.otus.socketchat.services;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.Session;

import org.springframework.stereotype.Service;

import ru.podelochki.otus.socketchat.messages.ServiceMessage;

//@Service
public class SimpleMessageService implements MessageService {

	private final Map<String, Queue<ServiceMessage>> messageQueues;
	public SimpleMessageService() {

		messageQueues = new ConcurrentHashMap<>();

	}
	
	@Override
	public Queue<ServiceMessage> getMessageQueue(String receiver) {
		return messageQueues.get(receiver);
	}
	
	public void addReceiver(String receiver) {
		messageQueues.put(receiver, new ConcurrentLinkedQueue<>());
	}
	
	public void removeReceiver(String receiver) {
		messageQueues.remove(receiver);
	}
	
	@Override
	public void putMessage(ServiceMessage message) {
		System.out.println("message in " + message.getReceiver() + " queue");
		messageQueues.get(message.getReceiver()).offer(message);
	}
}
