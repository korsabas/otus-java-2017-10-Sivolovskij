package ru.podelochki.otus.homework15.services;

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

import ru.podelochki.otus.homework15.messaging.DBMessage;
import ru.podelochki.otus.homework15.messaging.MessageService;
import ru.podelochki.otus.homework15.messaging.ServiceMessage;
import ru.podelochki.otus.homework15.messaging.WSMessage;
import ru.podelochki.otus.homework15.models.AppUser;
import ru.podelochki.otus.homework15.models.ChatMessage;
import ru.podelochki.otus.homework15.models.Message;

@Service
public class WebSocketMessageHandler implements Runnable, ServiceMessageHandler{
	public final String NAME = "WSMessageHandler";
	private final Map<String, Session>  peers;
	private final Queue<WSMessage> wsMessages;
	private final MessageService messageService;
	private final Thread taskThread;
	private final Gson gson = new Gson();
	private final DBService dbService;
	
	@Autowired
	public WebSocketMessageHandler(MessageService messageService, DBService dbService) {
		this.dbService = dbService;
		peers = new ConcurrentHashMap<>();
		this.messageService = messageService;
		wsMessages = new ConcurrentLinkedQueue<>();
		this.messageService.registerMessageHandler(NAME, this);
		taskThread = new Thread(this, NAME);
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
		Queue<ServiceMessage> queue = messageService.getMessageQueue(NAME);
		while(!queue.isEmpty()) {
			ServiceMessage message = queue.poll();
			WSMessage wMessage = gson.fromJson(message.getContent(), WSMessage.class);
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
		try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	public void addWSMessage(WSMessage message) {
		
		ServiceMessage sMessage = new ServiceMessage();
		sMessage.setSender(NAME);
		sMessage.setReceiver("DBMessageHandler");
		ChatMessage cMessage = new ChatMessage();
		
		AppUser mReciever = dbService.getUserByUsername(message.getReceiver());
		AppUser mSender = dbService.getUserByUsername(message.getSender());
		
		
		
		
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
		
		sMessage = new ServiceMessage();
		sMessage.setSender(NAME);
		sMessage.setReceiver("DBMessageHandler");
		cMessage = new ChatMessage();
		cMessage.setSender(mReciever);
		cMessage.setReceiver(mSender);
		sMessage.setContent(gson.toJson(new DBMessage("refresh", cMessage)));
		messageService.putMessage(sMessage);

	}
	
	private void updateSentMessages(WSMessage message) {
		ServiceMessage sMessage = new ServiceMessage();
		sMessage.setSender(NAME);
		sMessage.setReceiver("DBMessageHandler");
		ChatMessage cMessage = new ChatMessage();
		cMessage.setId(message.getId());
		sMessage.setContent(gson.toJson(new DBMessage("sent", cMessage)));
		messageService.putMessage(sMessage);
	}
	private void refreshMessages(WSMessage message) {
		
	}
	
}
