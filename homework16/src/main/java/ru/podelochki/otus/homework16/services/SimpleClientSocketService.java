package ru.podelochki.otus.homework16.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SimpleClientSocketService implements ClientSocketService{
	
	private Socket clientSocket;
	
	private SocketMessageHandler messageHandler;
	
	public SimpleClientSocketService(SocketMessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	@Override
	public void connect(String address, int port) throws IOException {
		clientSocket = new Socket(address, port);

		SocketSession session = new ClientSocketSession(clientSocket);
		messageHandler.onCreateSession(session);
	}
	
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

}
