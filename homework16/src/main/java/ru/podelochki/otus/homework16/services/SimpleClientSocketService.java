package ru.podelochki.otus.homework16.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;

import ru.podelochki.otus.homework16.messages.SocketMessage;

public class SimpleClientSocketService implements ClientSocketService{
	
	private Socket clientSocket;
	private SocketSession session;
	private SocketMessageHandler messageHandler;
	private final Gson gson = new Gson();
	private OutputStreamWriter out;
    private InputStreamReader in;
	
	public SimpleClientSocketService(SocketMessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	@Override
	public void connect(String address, int port) throws IOException {
		clientSocket = new Socket(address, port);
		session = new ClientSocketSession(clientSocket);
		out = new OutputStreamWriter(clientSocket.getOutputStream());
		in = new InputStreamReader(clientSocket.getInputStream());
		//messageHandler.onCreateSession(session);
	}
	private class ClientSocketSession implements Runnable,SocketSession {
		private char[] sizeMarker = new char[4];
        private String stringMessage;
		public ClientSocketSession(Socket socket) {
			
		}

		@Override
		public void sendMessage(String message) throws IOException {
			String outString = String.format("%04d", message.length());
			outString += message;
			out.write(outString);
			out.flush();		
		}
		public String readMessage() throws IOException {
			int readed = in.read(sizeMarker);
			if (readed == 4) {
				int bytesToRead = Integer.parseInt(new String(sizeMarker));
				char[] charMessage = new char[bytesToRead];
				in.read(charMessage);
				stringMessage = new String(charMessage);
				return stringMessage;
			}
			return null;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
	
	/*
	private class ClientSocketSession implements Runnable,SocketSession {
        private Socket clientSocket;
        private OutputStreamWriter out;
        private InputStreamReader in;
        private char[] sizeMarker = new char[4];
        private String stringMessage;

        
        public ClientSocketSession(Socket socket) {
            this.clientSocket = socket;
    		try {
				out = new OutputStreamWriter(clientSocket.getOutputStream());
				in = new InputStreamReader(clientSocket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		Thread thread = new Thread(this, "ClientSocketSession");
    		thread.start();
    		
        }
 
        @Override
        public void run() {
            try {				
				while (!clientSocket.isClosed()) {
					int readed = in.read(sizeMarker);
					if (readed == 4) {
						int bytesToRead = Integer.parseInt(new String(sizeMarker));
						char[] charMessage = new char[bytesToRead];
						in.read(charMessage);
						stringMessage = new String(charMessage);
						messageHandler.onMessage(null, stringMessage);
					}
				} 
				in.close();
				out.close();
				clientSocket.close();
            } catch (IOException e) {
            	try {
					clientSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			}
        }

		@Override
		public void sendMessage(String message) throws IOException {
			String outString = String.format("%04d", message.length());
			outString += message;
			out.write(outString);
			out.flush();
			
		}

		@Override
		public Socket getSocket() {
			return clientSocket;
			
		}
    }
    */

	@Override
	public SocketMessage sendSyncMessage(SocketMessage message) throws IOException {
		session.sendMessage(gson.toJson(message));
		SocketMessage response = null;
		try {
			response = gson.fromJson(session.readMessage(), SocketMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;		
	}

	@Override
	public SocketMessage sendAyncMessage(SocketMessage message) throws IOException {
		session.sendMessage(gson.toJson(message));
		return null;
	}

}
