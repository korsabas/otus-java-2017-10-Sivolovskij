package ru.podelochki.otus.socketchat.messages;

import java.util.Date;

import ru.podelochki.otus.socketchat.models.ChatMessage;

public class PlainChatMessage {
	
	private long id;
	
	private String sender;
	
	private String receiver;

	private String messageText;
	
	private int status;
	
	private Date createDate;

	public PlainChatMessage() {
		super();
	}

	public PlainChatMessage(long id, String sender, String receiver, String messageText, int status, Date createDate) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.messageText = messageText;
		this.status = status;
		this.createDate = createDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public ChatMessage getChatMessage() {
		return new ChatMessage(id, null, null, messageText, status, createDate);
	}
	
}
