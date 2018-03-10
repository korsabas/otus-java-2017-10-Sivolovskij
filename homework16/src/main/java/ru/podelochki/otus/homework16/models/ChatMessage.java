package ru.podelochki.otus.homework16.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="messages")
public class ChatMessage {
	
	@Id
	@Column(name="message_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="sender_id")
	private AppUser sender;
	
	@ManyToOne
	@JoinColumn(name="receiver_id")
	private AppUser receiver;
	
	@Column(name="text")
	private String messageText;
	
	@Column(name="status")
	private int status;
	
	@Column(name="create_date")
	private Date createDate;

	public ChatMessage() {
		super();
	}

	public ChatMessage(long id, AppUser sender, AppUser receiver, String messageText, int status, Date createDate) {
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

	public AppUser getSender() {
		return sender;
	}

	public void setSender(AppUser sender) {
		this.sender = sender;
	}

	public AppUser getReceiver() {
		return receiver;
	}

	public void setReceiver(AppUser receiver) {
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
	
	
}
