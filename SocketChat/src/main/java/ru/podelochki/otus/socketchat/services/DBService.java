package ru.podelochki.otus.socketchat.services;

import java.util.List;

import ru.podelochki.otus.socketchat.models.AppUser;
import ru.podelochki.otus.socketchat.models.ChatMessage;

public interface DBService {
	AppUser getAppUserById(int id);
	AppUser getUserByUsername(String username);
	
	List<ChatMessage> getMessagesForUser(AppUser user);
	List<ChatMessage> getUnreadedMessagesForUser(AppUser user, AppUser fromUser);
	
	void saveMessage(ChatMessage message);
	void updateMessage(ChatMessage cMessage);
	void updateNode(AppUser user);
}
