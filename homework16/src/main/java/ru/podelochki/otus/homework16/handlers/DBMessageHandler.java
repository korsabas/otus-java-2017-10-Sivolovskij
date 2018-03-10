package ru.podelochki.otus.homework16.handlers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ru.podelochki.otus.homework16.messages.DBMessage;
import ru.podelochki.otus.homework16.messages.RegisterMessage;
import ru.podelochki.otus.homework16.messages.ServiceMessage;
import ru.podelochki.otus.homework16.messages.SocketMessage;
import ru.podelochki.otus.homework16.services.DBService;
import ru.podelochki.otus.homework16.services.SocketMessageHandler;
import ru.podelochki.otus.homework16.services.SocketSession;



public class DBMessageHandler implements SocketMessageHandler{
	private String serviceName = "DBMessageHandler";
	private final String GROUP_NAME = "DBMessageHandler";
	private final DBService dbService;
	private final Gson gson = new Gson();

	private boolean registered;

	private char[] sizeMarker = new char[4];
	InputStreamReader in;
	OutputStreamWriter out;

	public DBMessageHandler(DBService dbService) {
		this.dbService = dbService;
	}


	

	
	public void getMessage() throws IOException {
		
		ServiceMessage message = new ServiceMessage();
		message.setSender(serviceName);
		message.setContent("hello");
		String sMessage = gson.toJson(message);
		String outString = String.format("%04d", sMessage.length());
		outString += sMessage;
		System.out.println(outString);
		out.write(outString);
		out.flush();
		//out.close();
		//ServiceMessage serviceMessage = readMessage(in);

		
		
	}


	private ServiceMessage readMessage(InputStreamReader in) throws IOException {
		in.read(sizeMarker);
		int bytesToRead = Integer.parseInt(new String(sizeMarker));
		char[] charMessage = new char[bytesToRead];
		in.read(charMessage);
		String stringMessage = new String(charMessage);
		return gson.fromJson(stringMessage, ServiceMessage.class);
	}


	@Override
	public void onMessage(SocketSession session, String message) {
		SocketMessage socketMessage = gson.fromJson(message, SocketMessage.class);
		if (socketMessage.getAction().equals(SocketMessage.REGISTER)) {
			RegisterMessage rMessage = gson.fromJson(socketMessage.getPayload(), RegisterMessage.class);
			serviceName = rMessage.getName();
			registered = true;
			System.out.println("REgistered:" + serviceName);
		} else if (socketMessage.getAction().equals(SocketMessage.RECEIVE_MESSAGE)){
			if (socketMessage.getPayload() != null) {
				System.out.println("Processing queue");
			} else {
				System.out.println("Emty queue");
			}
			
		}
	}
	
	private ServiceMessage getServiceMessage(SocketMessage socketMessage) {
		return gson.fromJson(socketMessage.getPayload(), ServiceMessage.class);
	}

	@Override
	public void onCreateSession(SocketSession session) {
		new MessageReader(session);
		RegisterMessage rMessage = new RegisterMessage(serviceName, GROUP_NAME);
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
	
}