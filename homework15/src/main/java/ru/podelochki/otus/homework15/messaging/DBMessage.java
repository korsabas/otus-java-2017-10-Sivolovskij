package ru.podelochki.otus.homework15.messaging;

import ru.podelochki.otus.homework15.models.ChatMessage;

public class DBMessage {
	private String type;
	private ChatMessage message;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ChatMessage getMessage() {
		return message;
	}
	public void setMessage(ChatMessage message) {
		this.message = message;
	}
	public DBMessage(String type, ChatMessage message) {
		super();
		this.type = type;
		this.message = message;
	}
	
	
}
