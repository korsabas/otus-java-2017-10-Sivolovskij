package ru.podelochki.otus.homework15.messaging;

import ru.podelochki.otus.homework15.models.ChatMessage;
import ru.podelochki.otus.homework15.models.PlainChatMessage;

public class DBMessage {
	private String type;
	private PlainChatMessage message;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public PlainChatMessage getMessage() {
		return message;
	}
	public void setMessage(PlainChatMessage message) {
		this.message = message;
	}
	public DBMessage(String type, PlainChatMessage message) {
		super();
		this.type = type;
		this.message = message;
	}
	
	
}
