package ru.podelochki.otus.homework16.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.google.gson.Gson;

import ru.podelochki.otus.homework16.messages.ServiceMessage;

public class SimpleServerSocketService implements ServerSocketService{
	//private final MessageService messageService;
	private ServerSocket serverSocket;
	private final SocketMessageHandler messageHandler;
	
	public SimpleServerSocketService(SocketMessageHandler messageHandler) {
		this.messageHandler = messageHandler;
		
	}
	
	@Override
	public void start(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(5000);
		while (true) {
			try {
            new SocketSessionImpl(serverSocket.accept(), messageHandler).start();	
			} catch (SocketTimeoutException e) {
				
			}
		}
	}
	
	private class SocketSessionImpl extends Thread implements SocketSession {
        private Socket clientSocket;
        private BufferedWriter out;
        private InputStreamReader in;
        private char[] sizeMarker = new char[4];
        private String stringMessage;
        private SocketMessageHandler mHandler;
        
        public SocketSessionImpl(Socket socket, SocketMessageHandler messageHandler) {
            this.clientSocket = socket;
            this.mHandler = messageHandler;
        }
 
        @Override
        public void run() {
            try {
				out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				in = new InputStreamReader(clientSocket.getInputStream());
				
				while (!clientSocket.isClosed()) {
					try {
					int readed = in.read(sizeMarker);
					if (readed == 4) {
						int bytesToRead = Integer.parseInt(new String(sizeMarker));
						char[] charMessage = new char[bytesToRead];
						in.read(charMessage);
						stringMessage = new String(charMessage);
						System.out.println(new String(sizeMarker));
						System.out.println(stringMessage);
						//ServiceMessage sMessage = gson.fromJson(stringMessage, ServiceMessage.class);
					
						mHandler.onMessage(this, stringMessage);
					}
					} catch (Exception e2) {
						e2.printStackTrace();
						clientSocket.close();
					}
				}
 
				in.close();
				out.close();
				clientSocket.close();
            } catch (IOException e) {

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
