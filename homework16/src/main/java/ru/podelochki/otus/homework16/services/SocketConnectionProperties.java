package ru.podelochki.otus.homework16.services;

import org.springframework.stereotype.Component;

@Component
public class SocketConnectionProperties {
	private String host;
	private int port;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
