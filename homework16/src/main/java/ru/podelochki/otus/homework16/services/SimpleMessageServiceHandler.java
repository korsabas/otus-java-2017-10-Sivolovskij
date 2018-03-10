package ru.podelochki.otus.homework16.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

import ru.podelochki.otus.homework16.messages.RegisterMessage;
import ru.podelochki.otus.homework16.messages.ServiceMessage;
import ru.podelochki.otus.homework16.messages.SocketMessage;

public class SimpleMessageServiceHandler implements SocketMessageHandler{
	private final Gson gson = new Gson();
	private final MessageService mService;
	private final Map<RegisterMessage, SocketSession>  peerMap;
	private final Map<SocketSession, RegisterMessage>  sessionMap;
	private final Map<String, Queue<SocketSession>>  groups;
	private AtomicInteger counter = new AtomicInteger(0);
	
	public SimpleMessageServiceHandler(MessageService mService) {
		this.mService = mService;
		peerMap = new ConcurrentHashMap<>();
		groups = new ConcurrentHashMap<>();
		sessionMap = new ConcurrentHashMap<>();
	}
	
	@Override
	public void onMessage(SocketSession session, String message) {
		SocketMessage socketMessage = gson.fromJson(message, SocketMessage.class);
		if (socketMessage.getAction().equals(SocketMessage.REGISTER)) {
			register(session, socketMessage);
		} else if (socketMessage.getAction().equals(SocketMessage.SEND_MESSAGE)) {
			putMessage(session, socketMessage);
		} else if (socketMessage.getAction().equals(SocketMessage.RECEIVE_MESSAGE)) {
			getMessageQueue(session, socketMessage);
		}
	}
	
	private void register(SocketSession session, SocketMessage socketMessage) {
		int suffix = counter.incrementAndGet();
		RegisterMessage rMessage = gson.fromJson(socketMessage.getPayload(), RegisterMessage.class);
		
		SocketMessage responseSocketMessage = new SocketMessage(SocketMessage.REGISTER);
		RegisterMessage rMessageResponse = new RegisterMessage(rMessage.getName() + suffix, rMessage.getGroup());
		responseSocketMessage.setPayload(gson.toJson(rMessageResponse));
		try {
			session.sendMessage(gson.toJson(responseSocketMessage));
			if (rMessage.getGroup()!=null && !groups.containsKey(rMessage.getGroup())) {
				Queue<SocketSession> q = new ConcurrentLinkedQueue<>();
				q.offer(session);
				groups.put(rMessageResponse.getGroup(), q);
				mService.addReceiver(rMessageResponse.getGroup());
			} else if (rMessageResponse.getGroup()!=null) {
				groups.get(rMessageResponse.getGroup()).offer(session);
			}
			peerMap.put(rMessageResponse, session);
			sessionMap.put(session, rMessageResponse);
			mService.addReceiver(rMessageResponse.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void putMessage(SocketSession session, SocketMessage socketMessage) {
		ServiceMessage sMessage = gson.fromJson(socketMessage.getPayload(), ServiceMessage.class);
		mService.putMessage(sMessage);
	}
	
	private void getMessageQueue(SocketSession session, SocketMessage socketMessage) {
		System.out.println("Getting MEssage Queue");
		RegisterMessage registration = sessionMap.get(session);
		if (registration.getGroup() != null) {
			ServiceMessage sMessage = mService.getMessageQueue(registration.getGroup()).poll();
			if (sMessage != null) {
				
				SocketMessage responseSocketMessage = new SocketMessage(SocketMessage.RESPONSE_MESSAGE);
				responseSocketMessage.setPayload(gson.toJson(sMessage));
				try {
					session.sendMessage(gson.toJson(responseSocketMessage));
				} catch (IOException e) {
					unregister(session);
				}
				
			} else {
				System.out.println("Queue empty");
			}
		} else {
			Queue<ServiceMessage> tmpQueue = mService.getMessageQueue(registration.getName());
			List<ServiceMessage> tmpList = new ArrayList<>();
			while (!tmpQueue.isEmpty()) {
				tmpList.add(tmpQueue.poll());
			}
			if (tmpList.size() > 0) {
				SocketMessage responseSocketMessage = new SocketMessage(SocketMessage.RESPONSE_MESSAGE);
				responseSocketMessage.setPayload(gson.toJson(tmpList));
			}
		}
	}
	
	public void unregister(SocketSession session) {
		RegisterMessage registration = sessionMap.remove(session);
		peerMap.remove(registration);
		Queue<SocketSession> queue = groups.get(registration.getGroup());
		queue.remove(session);
		mService.removeReceiver(registration.getName());
	}

	@Override
	public void onCreateSession(SocketSession session) {
		// TODO Auto-generated method stub
		
	}
	
}
