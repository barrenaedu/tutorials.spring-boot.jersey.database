package com.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.domain.Message;

@Component
public class MessageManagerImpl implements MessageManager {
	private Map<Long, Message> messages = new HashMap<>();

	@Override
	public Message createMessage(Message msg) {
		Message newMsg = new Message.MessageBuilder().id(messages.size() + 1).text(msg.getText()).build();
		messages.put(newMsg.getId(), newMsg);
		return newMsg;
	}

	@Override
	public void updateMessage(Message msg) {
		messages.replace(msg.getId(), msg);
	}
	
	@Override
	public void deleteMessage(long id) {
		messages.remove(id);		
	}

	@Override
	public Message getMessage(long id) {
		return messages.get(id);
	}

	@Override
	public Collection<Message> getMessages() {
		return messages.values();
	}

}
