package ru.podelochki.otus.homework15;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import ru.podelochki.otus.homework15.messaging.MessageDecoder;
import ru.podelochki.otus.homework15.messaging.MessageEncoder;
import ru.podelochki.otus.homework15.services.WebSocketMessageHandler;


//@ServerEndpoint(value = "/chat", encoders=MessageEncoder.class, decoders=MessageDecoder.class, configurator = SpringConfigurator.class)
public class WebSocketTester {
	private WebSocketMessageHandler mHandler;
	
	
	@Autowired
	public WebSocketTester(WebSocketMessageHandler messageHandler) {
		this.mHandler = messageHandler;
	}
	

    @OnOpen
    public void onOpen(Session session) {
    	mHandler.addPeer(session);
    	System.out.println("peer added");
    }
    
    private class MessageSender implements Runnable {
    	public MessageSender() {
    		
    	}

		@Override
		public void run() {

			
		}
    }

    @OnMessage
    public void onMessage(Session session) throws IOException, EncodeException {

    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
    	mHandler.removePeer(session);
    	System.out.println("peer removed");
    }

}
