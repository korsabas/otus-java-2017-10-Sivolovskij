package ru.podelochki.otus.socketchat.handlers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ru.podelochki.otus.socketchat.messages.DBMessage;
import ru.podelochki.otus.socketchat.messages.PlainChatMessage;
import ru.podelochki.otus.socketchat.messages.RegisterMessage;
import ru.podelochki.otus.socketchat.messages.ServiceMessage;
import ru.podelochki.otus.socketchat.messages.SocketMessage;
import ru.podelochki.otus.socketchat.messages.WSMessage;
import ru.podelochki.otus.socketchat.models.AppUser;
import ru.podelochki.otus.socketchat.models.ChatMessage;
import ru.podelochki.otus.socketchat.services.ClientMessageService;
import ru.podelochki.otus.socketchat.services.DBService;
import ru.podelochki.otus.socketchat.services.ServiceMessageHandler;
import ru.podelochki.otus.socketchat.services.SocketMessageHandler;
import ru.podelochki.otus.socketchat.services.SocketSession;



public class DBMessageHandler implements ServiceMessageHandler{
	private String serviceName = "DBMessageHandler";
	private final String GROUP_NAME = "DBMessageHandler";
	private final DBService dbService;
	private final Gson gson = new Gson();
	private ClientMessageService messageService;

	private boolean registered;

	private char[] sizeMarker = new char[4];
	InputStreamReader in;
	OutputStreamWriter out;

	public DBMessageHandler(DBService dbService, ClientMessageService messageService) {
		this.dbService = dbService;
		this.messageService = messageService;
		this.messageService.addReceiver(this);
		new MessageReader();
	}

	private class MessageReader implements Runnable {
		
		private MessageReader() {
			Thread thread = new Thread(this, "MessageReader");
			thread.start();
		}
		
		@Override
		public void run() {
			while (true) {
			Queue<ServiceMessage> queue = messageService.getMessageQueue(serviceName);
			if (queue != null) {
			System.out.println("Received non empty queue");	
			while(!queue.isEmpty()) {
				ServiceMessage message = queue.poll();
				ServiceMessage response = executeMessage(message);
				if (response != null) {
					messageService.putMessage(response);
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
		
	}
	
	private ServiceMessage executeMessage(ServiceMessage message) {
		DBMessage dMessage = gson.fromJson(message.getContent(), DBMessage.class);
		
		if (dMessage.getType().equals("save")) {
			PlainChatMessage pMessage = dMessage.getMessage();
			ChatMessage cMessage = pMessage.getChatMessage();
			AppUser tmpUser = dbService.getUserByUsername(pMessage.getSender()); 
			cMessage.setSender(tmpUser);
			cMessage.setReceiver(dbService.getUserByUsername(pMessage.getReceiver()));
			dbService.saveMessage(cMessage);
			ServiceMessage tmpMessage = new ServiceMessage();
			tmpMessage.setSender(message.getReceiver());
			tmpMessage.setReceiver(tmpUser.getActiveNode());
			WSMessage textMessage = new WSMessage(cMessage.getSender().getUsername(), cMessage.getReceiver().getUsername(), null);
			//textMessage.setId(cMessage.getId());
			tmpMessage.setContent(gson.toJson(textMessage));
			messageService.putMessage(tmpMessage);
			return null;
		}
		if (dMessage.getType().equals("refresh")) {
			PlainChatMessage pMessage = dMessage.getMessage();
			ChatMessage cMessage = pMessage.getChatMessage();
			AppUser tmpUser = dbService.getUserByUsername(pMessage.getSender()); 
			cMessage.setSender(tmpUser);
			cMessage.setReceiver(dbService.getUserByUsername(pMessage.getReceiver()));
			List<ChatMessage> chatMessagesdbService = dbService.getUnreadedMessagesForUser(cMessage.getReceiver(), cMessage.getSender());
			for (ChatMessage chatMessage: chatMessagesdbService) {
				ServiceMessage tmpMessage = new ServiceMessage();
				tmpMessage.setSender(message.getReceiver());
				tmpMessage.setReceiver(tmpUser.getActiveNode());
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
			//cMessage.setSender(dbService.getUserByUsername(pMessage.getSender()));
			//cMessage.setReceiver(dbService.getUserByUsername(pMessage.getReceiver()));
			dbService.updateMessage(cMessage);
			return null;
		}
		
		return null;
	}

	@Override
	public RegisterMessage getRegisterMessage() {
		// TODO Auto-generated method stub
		return new RegisterMessage(serviceName, GROUP_NAME);
	}


	@Override
	public void updateName(String name) {
		this.serviceName = name;
		
	}
	
}