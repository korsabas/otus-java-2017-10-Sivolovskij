package ru.podelochki.otus.homework15.messaging;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import ru.podelochki.otus.homework15.models.Message;

public class MessageEncoder implements Encoder.Text<WSMessage> {

    @Override
    public String encode(final WSMessage message) throws EncodeException {
    	Gson gson = new Gson();
        return gson.toJson(message);
    }

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
