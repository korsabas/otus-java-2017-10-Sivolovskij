package ru.podelochki.otus.homework15;

import java.net.URI;
import java.util.Scanner;

import javax.websocket.ClientEndpoint;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

import com.google.gson.Gson;

import ru.podelochki.otus.homework15.messaging.Message;

public class Client {

    public static final String SERVER = "ws://localhost:8080/homework15/chat";

    public static void main(String[] args) throws Exception {
        ClientManager client = ClientManager.createClient();
        String message;

        // connect to server
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tiny Chat!");
        System.out.println("What's your name?");
        String user = scanner.nextLine();
        Session session = client.connectToServer(ClientEnt.class, new URI(SERVER));
        System.out.println("You are logged in as: " + user);

        // repeatedly read a message and send it to the server (until quit)
        do {
            message = scanner.nextLine();
            session.getBasicRemote().sendText(new Gson().toJson(new Message()));
        } while (!message.equalsIgnoreCase("quit"));
    }

}
