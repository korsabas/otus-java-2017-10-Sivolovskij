package ru.podelochki.otus.homework16.messages;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;


public class MessageDecoder implements Decoder.Text<WSMessage> {

    @Override
    public WSMessage decode(final String textMessage) throws DecodeException {

    	return null;
    }

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean willDecode(String s) {
		// TODO Auto-generated method stub
		return false;
	}

}
