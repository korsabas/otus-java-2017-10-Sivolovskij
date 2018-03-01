package ru.podelochki.otus.homework15.messaging;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.Session;

import org.springframework.stereotype.Service;

import ru.podelochki.otus.homework15.services.ServiceMessageHandler;

@Service
public class MessageService {
	private final Map<String, ServiceMessageHandler> messageHandlers;
	private final Map<String, Queue<ServiceMessage>> messageQueues;
	private final Queue<ServiceMessage> requestMessageQueue;
	private final Queue<ServiceMessage> responseMessageQueue;
	
	public MessageService() {
		messageHandlers = new ConcurrentHashMap<>();
		messageQueues = new ConcurrentHashMap<>();
		requestMessageQueue = new ConcurrentLinkedQueue<>();
		responseMessageQueue = new ConcurrentLinkedQueue<>();
	}
	
	public void registerMessageHandler(String name, ServiceMessageHandler handler) {
		messageHandlers.put(name, handler);
		messageQueues.put(name, new ConcurrentLinkedQueue<>());
	}
	public void removeMessageHandler(String name) {
		messageHandlers.remove(name);
		messageQueues.remove(name);
	}
	
	
	public Queue<ServiceMessage> getMessageQueue(String name) {
		return messageQueues.get(name);
	}

	public void putMessage(ServiceMessage message) {
		System.out.println("message in " + message.getReceiver() + " queue");
		messageQueues.get(message.getReceiver()).offer(message);
	}

}
