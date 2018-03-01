package ru.podelochki.otus.homework15.messaging;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import ru.podelochki.otus.homework15.models.Message;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(final String textMessage) throws DecodeException {
    	//Gson gson = new Gson();
        //return gson.fromJson(textMessage, Message.class);
    	Message m = new Message();
    	return m;
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
