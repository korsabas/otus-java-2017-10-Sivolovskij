package ru.podelochki.otus.homework16.services;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ru.podelochki.otus.homework16.messages.DBMessage;
import ru.podelochki.otus.homework16.messages.PlainChatMessage;
import ru.podelochki.otus.homework16.messages.RegisterMessage;
import ru.podelochki.otus.homework16.messages.ServiceMessage;
import ru.podelochki.otus.homework16.messages.WSMessage;

@Service
public class WebSocketMessageHandler implements Runnable, ServiceMessageHandler{
	public String serviceName = "WSMessageHandler";
	private final Map<String, Session>  peers;
	private final Queue<WSMessage> wsMessages;
	private final ClientMessageService messageService;
	private final Thread taskThread;
	private final Gson gson = new Gson();
	
	//@Autowired
	public WebSocketMessageHandler() {

		peers = new ConcurrentHashMap<>();
		this.messageService = new SocketMessageService("localhost", 8181);
		messageService.addReceiver(this);
		wsMessages = new ConcurrentLinkedQueue<>();
		taskThread = new Thread(this, serviceName);
		taskThread.start();
	}

	public void addPeer(String name, Session session) {
		peers.put(name, session);
	}
	public void removePeer(String name) {
		peers.remove(name);
	}
	@Override
	public void run() {
		while (true) {
		Queue<ServiceMessage> queue = messageService.getMessageQueue(serviceName);
		if (queue != null) {
		while(!queue.isEmpty()) {
			ServiceMessage message = queue.poll();
			WSMessage wMessage = gson.fromJson(message.getContent(), WSMessage.class);
			if (wMessage.getId() == 0) {
				PlainChatMessage cMessage = new PlainChatMessage();
				ServiceMessage sMessage = new ServiceMessage();
				sMessage.setSender(serviceName);
				sMessage.setReceiver("DBMessageHandler");
				cMessage = new PlainChatMessage();
				cMessage.setSender(wMessage.getReceiver());
				cMessage.setReceiver(wMessage.getSender());
				sMessage.setContent(gson.toJson(new DBMessage("refresh", cMessage)));
				messageService.putMessage(sMessage);
				continue;
			}
			Session session = peers.get(wMessage.getReceiver());
			if (session != null) {
				try {
					//session.getBasicRemote().sendText(tokens[1]);
					//session.getBasicRemote().sendText(wMessage.getText());
					session.getBasicRemote().sendObject(wMessage);
					updateSentMessages(wMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EncodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		}
		try {
			Thread.currentThread().sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	public void addWSMessage(WSMessage message) {
		
		ServiceMessage sMessage = new ServiceMessage();
		sMessage.setSender(serviceName);
		sMessage.setReceiver("DBMessageHandler");
		PlainChatMessage cMessage = new PlainChatMessage();
		
		String mReciever = message.getReceiver();
		String mSender = message.getSender();
		
		
		
		
		if (message.getText() == null || message.getText().length() == 0) {
			cMessage.setSender(mSender);
			cMessage.setReceiver(mReciever);
			sMessage.setContent(gson.toJson(new DBMessage("refresh", cMessage)));
			messageService.putMessage(sMessage);
			return;
		}
		
		cMessage.setSender(mSender);
		cMessage.setReceiver(mReciever);
		cMessage.setMessageText(message.getText());		
		sMessage.setContent(gson.toJson(new DBMessage("save", cMessage)));
		messageService.putMessage(sMessage);
		
		/*
		sMessage = new ServiceMessage();
		sMessage.setSender(serviceName);
		sMessage.setReceiver("DBMessageHandler");
		cMessage = new PlainChatMessage();
		cMessage.setSender(mReciever);
		cMessage.setReceiver(mSender);
		sMessage.setContent(gson.toJson(new DBMessage("refresh", cMessage)));
		messageService.putMessage(sMessage);
		*/
		

	}
	
	private void updateSentMessages(WSMessage message) {
		ServiceMessage sMessage = new ServiceMessage();
		sMessage.setSender(serviceName);
		sMessage.setReceiver("DBMessageHandler");
		PlainChatMessage cMessage = new PlainChatMessage();
		cMessage.setId(message.getId());
		sMessage.setContent(gson.toJson(new DBMessage("sent", cMessage)));
		messageService.putMessage(sMessage);
	}
	private void refreshMessages(WSMessage message) {
		
	}

	@Override
	public RegisterMessage getRegisterMessage() {
		return new RegisterMessage(serviceName, null);
	}

	@Override
	public void updateName(String name) {
		this.serviceName = name;
	}
	
}
