package ru.podelochki.otus.homework15.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import ru.podelochki.otus.homework15.models.AppUser;

@Service
public class ActiveUsers {
	private final Map<String, AppUser> usersSessions;
	private final Map<String, HttpSession> sessionsUsers;
	
	public ActiveUsers() {
		System.out.println("Active users initialized");
		usersSessions = new ConcurrentHashMap<>();
		sessionsUsers = new ConcurrentHashMap<>();
	}
	
	public void setUserForSession(HttpSession session,AppUser  user) {
		usersSessions.put(session.getId(), user);
		sessionsUsers.put(user.getUsername(), session);
	}
	
	public AppUser getUserBySession(String sessionId) {
		return usersSessions.get(sessionId);
	}
	
}
