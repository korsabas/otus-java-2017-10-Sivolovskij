package ru.podelochki.otus.homework15.services;

import java.util.List;

import ru.podelochki.otus.homework15.models.AppUser;
import ru.podelochki.otus.homework15.models.ChatMessage;
public interface DBService {
	AppUser getAppUserById(int id);
	AppUser getUserByUsername(String username);
	
	List<ChatMessage> getMessagesForUser(AppUser user);
	List<ChatMessage> getUnreadedMessagesForUser(AppUser user, AppUser fromUser);
	
	void saveMessage(ChatMessage message);
	void updateMessage(ChatMessage cMessage);
}
