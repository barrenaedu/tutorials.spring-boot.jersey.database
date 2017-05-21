package com;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.domain.Message;
import com.service.MessageManager;

@SpringBootApplication
public class Application {

	@Autowired
	public Application(MessageManager messageManager) {
		messageManager.createMessage(new Message.MessageBuilder().text("Hello World").build());
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
