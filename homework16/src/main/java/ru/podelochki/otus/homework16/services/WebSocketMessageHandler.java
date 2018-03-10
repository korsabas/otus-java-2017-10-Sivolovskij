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

import ru.podelochki.otus.homework16.messages.PlainChatMessage;
import ru.podelochki.otus.homework16.messages.RegisterMessage;
import ru.podelochki.otus.homework16.messages.ServiceMessage;
import ru.podelochki.otus.homework16.messages.SocketMessage;
import ru.podelochki.otus.homework16.messages.WSMessage;



@Service
public class WebSocketMessageHandler implements SocketMessageHandler{
	private String serviceName = "WSMessageHandler";
	private final Map<String, Session>  peers;
	private final Queue<WSMessage> wsMessages;
	//private final Thread taskThread;
	private final Gson gson = new Gson();
	private boolean registered;
	
	public WebSocketMessageHandler() {
		ClientSocketService clientSocketService = new SimpleClientSocketService(this);
		try {
			clientSocketService.connect("localhost", 8181);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		peers = new ConcurrentHashMap<>();
		wsMessages = new ConcurrentLinkedQueue<>();

	}

	public void addPeer(String name, Session session) {
		peers.put(name, session);
	}
	public void removePeer(String name) {
		peers.remove(name);
	}

	@Override
	public void onMessage(SocketSession session, String message) {
		SocketMessage socketMessage = gson.fromJson(message, SocketMessage.class);
		if (socketMessage.getAction().equals(SocketMessage.REGISTER)) {
			RegisterMessage rMessage = gson.fromJson(socketMessage.getPayload(), RegisterMessage.class);
			serviceName = rMessage.getName();
			registered = true;
			System.out.println("REgistered:" + serviceName);
		}
		
	}

	@Override
	public void onCreateSession(SocketSession session) {
		new MessageReader(session);
		RegisterMessage rMessage = new RegisterMessage(serviceName, null);
		SocketMessage socketMessage = new SocketMessage(SocketMessage.REGISTER);
		socketMessage.setPayload(gson.toJson(rMessage));
		try {
			session.sendMessage(gson.toJson(socketMessage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private class MessageReader implements Runnable {
		private SocketSession readerSession;
		private MessageReader(SocketSession session) {
			this.readerSession = session;
			Thread thread = new Thread(this, "MessageReader");
			thread.start();
		}
		@Override
		public void run() {
			while (readerSession.getSocket().isConnected()) {
				if (registered) {
					SocketMessage socketMessage = new SocketMessage(SocketMessage.RECEIVE_MESSAGE);
					try {
						readerSession.sendMessage(gson.toJson(socketMessage));
					} catch (Exception e) {
						registered = false;
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
		
		sMessage = new ServiceMessage();
		sMessage.setSender(NAME);
		sMessage.setReceiver("DBMessageHandler");
		cMessage = new PlainChatMessage();
		cMessage.setSender(mReciever);
		cMessage.setReceiver(mSender);
		sMessage.setContent(gson.toJson(new DBMessage("refresh", cMessage)));
		messageService.putMessage(sMessage);

	}

	
}
