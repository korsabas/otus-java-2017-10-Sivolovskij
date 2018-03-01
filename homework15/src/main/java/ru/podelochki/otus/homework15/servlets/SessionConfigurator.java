package ru.podelochki.otus.homework15.servlets;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import ru.podelochki.otus.homework15.services.ActiveUsers;

public class SessionConfigurator extends SpringConfigurator{
	
	@Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        System.out.println("modifyHandshake() Current thread " + Thread.currentThread().getName());
        //String user = request.getParameterMap().get("user").get(0);
        Map<String, List<String>>  maps = request.getParameterMap();
       HttpSession session =(HttpSession) request.getHttpSession();
       //System.out.println("user logget as " + session.getAttribute("username") + "started chat");
        //sec.getUserProperties().put(user, request.getHttpSession());
       //sec.getConfigurator().getEndpointInstance(endpointClass)
       sec.getUserProperties().put("username", session.getAttribute("username"));
        int a= 0;
        ///.out.println("modifyHandshake() User " + user + " with http session ID " + ((HttpSession) request.getHttpSession()).getId());
    }

}
