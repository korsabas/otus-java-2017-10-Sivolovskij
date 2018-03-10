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
	
	@Autowired
	public WebSocketServlet(WebSocketMessageHandler messageHandler) {
		this.mHandler = messageHandler;
	}
	

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
    	this.config = config;
    	mHandler.addPeer((String)this.config.getUserProperties().get("username"), session);
    	System.out.println("peer added");
    	//mHandler.addWSMessage(new WSMessage((String)this.config.getUserProperties().get("username"), null, null));
    	System.out.println("user " + this.config.getUserProperties().get("username") + " entered chat");
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {

    	String[] tokens = message.split(";");
    	if (tokens.length <2) {
    		mHandler.addWSMessage(new WSMessage((String)this.config.getUserProperties().get("username"), tokens[0], null));
    	} else {
    		//mHandler.addWSMessage(new WSMessage((String)this.config.getUserProperties().get("username"), tokens[0], tokens[1]));
    	}
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
    	config.getUserProperties().get("username");
    	mHandler.removePeer((String) this.config.getUserProperties().get("username"));
    	System.out.println("peer removed");
    	System.out.println("user " + this.config.getUserProperties().get("username") + " left chat");
    }

}
