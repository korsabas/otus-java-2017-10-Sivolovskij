package ru.podelochki.otus.homework16.servlets;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;

import ru.podelochki.otus.homework16.messages.WSMessage;
import ru.podelochki.otus.homework16.services.WebSocketMessageHandler;
import ru.podelochki.otus.homework16.messages.MessageDecoder;
import ru.podelochki.otus.homework16.messages.MessageEncoder;


@ServerEndpoint(value = "/messaging", encoders=MessageEncoder.class, decoders=MessageDecoder.class, configurator = SessionConfigurator.class)
public class WebSocketServlet {
	private WebSocketMessageHandler mHandler;
	private EndpointConfig config;
	//private Refresher refresher;
	private boolean opened;
	
	@Autowired
	public WebSocketServlet(WebSocketMessageHandler messageHandler) {
		this.mHandler = messageHandler;
		
	}
	
	/*private class Refresher implements Runnable {
		private String receiver;
		private String sender;
		
		private Refresher() {
			Thread t = new Thread(this,"Refresher");
			t.setDaemon(true);
			t.start();
		}
		
		@Override
		public void run() {
			while (opened) {
				if (receiver != null && sender!=null){
				mHandler.addWSMessage(new WSMessage(sender, receiver, null));
				}
				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		public String getReceiver() {
			return receiver;
		}
		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}
		public String getSender() {
			return sender;
		}
		public void setSender(String sender) {
			this.sender = sender;
		}
		
		
	}
	*/

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
    	this.config = config;
    	mHandler.addPeer((String)this.config.getUserProperties().get("username"), session);
    	System.out.println("peer added");
    	//mHandler.addWSMessage(new WSMessage((String)this.config.getUserProperties().get("username"), null, null));
    	System.out.println("user " + this.config.getUserProperties().get("username") + " entered chat");
    	opened = true;
    	//refresher = new Refresher();
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {

    	String[] tokens = message.split(";");
    	if (tokens.length <2) {
    		mHandler.addWSMessage(new WSMessage((String)this.config.getUserProperties().get("username"), tokens[0], null));
    		//refresher.setReceiver(tokens[0]);
    		//refresher.setSender((String)this.config.getUserProperties().get("username"));
    	} else {
    		mHandler.addWSMessage(new WSMessage((String)this.config.getUserProperties().get("username"), tokens[0], tokens[1]));
    	}
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
    	opened = false;
    	config.getUserProperties().get("username");
    	mHandler.removePeer((String) this.config.getUserProperties().get("username"));
    	System.out.println("peer removed");
    	System.out.println("user " + this.config.getUserProperties().get("username") + " left chat");
    }

}
