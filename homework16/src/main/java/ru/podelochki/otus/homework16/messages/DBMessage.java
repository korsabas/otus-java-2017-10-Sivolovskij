package ru.podelochki.otus.homework16.messages;


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
