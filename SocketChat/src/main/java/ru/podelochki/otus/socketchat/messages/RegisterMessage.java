package ru.podelochki.otus.socketchat.messages;

public class RegisterMessage {
	private String name;
	private String group;
	public RegisterMessage(String name, String group) {
		super();
		this.name = name;
		this.group = group;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
}
