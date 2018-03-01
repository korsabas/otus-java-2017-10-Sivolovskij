package ru.podelochki.otus.homework15;

import java.text.SimpleDateFormat;
import javax.websocket.OnMessage;
import javax.websocket.ClientEndpoint;

import ru.podelochki.otus.homework15.messaging.*;

//@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ClientEnt {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @OnMessage
    public void onMessage(Message message) {
        System.out.println(String.format("[%s:%s] %s",
         simpleDateFormat.format(message.getReceived()), message.getSender(), message.getContent()));
    }

}
