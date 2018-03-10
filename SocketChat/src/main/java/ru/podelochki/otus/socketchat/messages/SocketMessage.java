package ru.podelochki.otus.socketchat.messages;

public class SocketMessage {
	public static final String REGISTER = "register";
	public static final String SEND_MESSAGE = "send";
	public static final String RECEIVE_MESSAGE = "receive";
	public static final String RESPONSE_MESSAGE = "response";
	private String action;
	private String payload;
	public SocketMessage(String action) {
		super();
		this.action = action;
	}
	public SocketMessage(String action, String payload) {
		super();
		this.action = action;
		this.payload = payload;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
}
