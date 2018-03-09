package ru.podelochki.otus.homework15.messaging;

import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ru.podelochki.otus.homework15.models.ChatMessage;
import ru.podelochki.otus.homework15.models.PlainChatMessage;
import ru.podelochki.otus.homework15.services.DBService;
import ru.podelochki.otus.homework15.services.ServiceMessageHandler;

@Service
public class DBMessageHandler implements Runnable, ServiceMessageHandler{
	private final String NAME = "DBMessageHandler";
	private final DBService dbService;
	private final MessageService messageService;
	private final Thread taskThread;
	private final Gson gson = new Gson();
	
	@Autowired
	public DBMessageHandler(DBService dbService, MessageService messageService) {
		this.dbService = dbService;
		this.messageService = messageService;
		this.messageService.registerMessageHandler(NAME, this);
		taskThread = new Thread(this, NAME);
		taskThread.start();
	}


	@Override
	public void run() {
		while (true) {
		Queue<ServiceMessage> queue = messageService.getMessageQueue(NAME);
		while(!queue.isEmpty()) {
			ServiceMessage message = queue.poll();
			ServiceMessage response = executeMessage(message);
			if (response != null) {
				messageService.putMessage(response);
			}
		}
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	private ServiceMessage executeMessage(ServiceMessage message) {
		DBMessage dMessage = gson.fromJson(message.getContent(), DBMessage.class);
		
		if (dMessage.getType().equals("save")) {
			PlainChatMessage pMessage = dMessage.getMessage();
			ChatMessage cMessage = pMessage.getChatMessage();
			cMessage.setSender(dbService.getUserByUsername(pMessage.getSender()));
			cMessage.setReceiver(dbService.getUserByUsername(pMessage.getReceiver()));
			dbService.saveMessage(cMessage);
			return null;
		}
		if (dMessage.getType().equals("refresh")) {
			PlainChatMessage pMessage = dMessage.getMessage();
			ChatMessage cMessage = pMessage.getChatMessage();
			cMessage.setSender(dbService.getUserByUsername(pMessage.getSender()));
			cMessage.setReceiver(dbService.getUserByUsername(pMessage.getReceiver()));
			List<ChatMessage> chatMessagesdbService = dbService.getUnreadedMessagesForUser(cMessage.getReceiver(), cMessage.getSender());
			for (ChatMessage chatMessage: chatMessagesdbService) {
				ServiceMessage tmpMessage = new ServiceMessage();
				tmpMessage.setSender(NAME);
				tmpMessage.setReceiver("WSMessageHandler");
				WSMessage textMessage = new WSMessage(chatMessage.getSender().getUsername(), chatMessage.getReceiver().getUsername(), chatMessage.getMessageText());
				textMessage.setId(chatMessage.getId());
				tmpMessage.setContent(gson.toJson(textMessage));
				messageService.putMessage(tmpMessage);
			}
			return null;
		}
		if (dMessage.getType().equals("sent")) {
			PlainChatMessage pMessage = dMessage.getMessage();
			ChatMessage cMessage = pMessage.getChatMessage();
			cMessage.setSender(dbService.getUserByUsername(pMessage.getSender()));
			cMessage.setReceiver(dbService.getUserByUsername(pMessage.getReceiver()));
			dbService.updateMessage(cMessage);
			return null;
		}
		
		return null;
	}
}