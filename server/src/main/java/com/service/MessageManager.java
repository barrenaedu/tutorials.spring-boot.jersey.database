package com.service;

import java.util.Collection;

import com.domain.Message;

public interface MessageManager {

	long createMessage(Message msg);
	
	void updateMessage(Message msg);

	void deleteMessage(long id);

	Message getMessage(long id);
	
	Collection<Message> getMessages();
}
