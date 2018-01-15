package com.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.Message;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageManagerImpl implements MessageManager {

    private MessageDao messageDao;

    @Autowired
    public MessageManagerImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Transactional
    @Override
    public long createMessage(Message msg) {
        return messageDao.createMessage(msg);
    }

    @Transactional
    @Override
    public void updateMessage(Message msg) {
        messageDao.updateMessage(msg);
    }

    @Transactional
    @Override
    public void deleteMessage(long id) {
        messageDao.deleteMessage(id);
    }

    @Override
    public Message getMessage(long id) {
        return messageDao.getMessage(id);
    }

    @Override
    public Collection<Message> getMessages() {
        return messageDao.getMessages();
    }

}
