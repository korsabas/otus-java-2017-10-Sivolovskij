package ru.podelochki.otus.homework16.messages;

public class WSMessage {
	private String sender;
	private String text;
	private String receiver;
	private long id;
	
	public WSMessage(String sender, String receiver, String text) {
		super();
		this.sender = sender;
		this.text = text;
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
