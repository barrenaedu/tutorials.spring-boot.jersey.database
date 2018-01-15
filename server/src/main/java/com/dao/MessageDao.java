package com.dao;

import com.domain.Message;

import java.util.Collection;

public interface MessageDao {

    long createMessage(Message msg);

    void updateMessage(Message msg);

    void deleteMessage(long id);

    Message getMessage(long id);

    Collection<Message> getMessages();

}
