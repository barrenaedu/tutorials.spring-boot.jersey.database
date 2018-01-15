package com.rest;

import com.domain.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiPredicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MessagesResourceTest {
	private static final String RESOURCE_URL = "/rest/messages/";
	
	@Autowired
	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testCreateMessage() throws Exception {
		String text = "The chosen one";
		ResponseEntity<String> response = createMessage(text);
	    assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	public void testUpdateMessage() throws Exception {
		// create new message
		ResponseEntity<String> response = createMessage("The message from 'testUpdateMessage' method");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		int id = Integer.valueOf(response.getBody());
		// update
		Message updatedMsg = new Message();
		updatedMsg.setId(id);
		updatedMsg.setText("The updated text");
		response = updateMessage(updatedMsg);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    // check if was updated
	    Message message = getMessage(id).getBody();
	    assertEquals(updatedMsg.getText(), message.getText());
	    // update not present
		updatedMsg.setId(Long.MAX_VALUE);
	    response = updateMessage(updatedMsg);
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void testDeleteMessage() throws Exception {
		// create message
		ResponseEntity<String> response = createMessage("The message from 'testDeleteMessage' method");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		int id = Integer.valueOf(response.getBody());
		// delete
		ResponseEntity<String> responseDel = deleteMessage(id);
	    assertEquals(HttpStatus.OK, responseDel.getStatusCode());
	    // check deleted
	    ResponseEntity<Message> responseGet = getMessage(id);
		assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
	}

	@Test
	public void testGetMessage() throws Exception {
		// create message
		ResponseEntity<String> responseCreated = createMessage("The message from 'testGetMessage' method");
		assertEquals(HttpStatus.CREATED, responseCreated.getStatusCode());
		int id = Integer.valueOf(responseCreated.getBody());
		// get valid message
		ResponseEntity<Message> responseGet = getMessage(id);
		assertEquals(HttpStatus.OK, responseGet.getStatusCode());
		// get invalid message
		responseGet = getMessage(Long.MAX_VALUE);
		assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
	}
	
	@Test
	public void testGetMessages() throws Exception {
		// create messages
		ResponseEntity<String> response = createMessage("The message 1 from 'testGetMessages' method");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Message msg1 = getMessage(Integer.valueOf(response.getBody())).getBody();
		response = createMessage("The message 2 from 'testGetMessages' method");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Message msg2 = getMessage(Integer.valueOf(response.getBody())).getBody();
		// get messages
		ResponseEntity<Collection<Message>> responseGetAll = getMessages();
		assertEquals(HttpStatus.OK, responseGetAll.getStatusCode());
		assertTrue(responseGetAll.getBody().size() >= 2);
	    BiPredicate<Message, Message> equals = (x, y) -> x.getId() == y.getId() && Objects.equals(x.getText(), y.getText());
		assertTrue(responseGetAll.getBody().stream().anyMatch(msg -> equals.test(msg, msg1) || equals.test(msg, msg2)));
	}
	
	// ***********************
	// *** Private methods ***
	// ***********************
	
	private ResponseEntity<Collection<Message>> getMessages() {
		ResponseEntity<Message[]> response = restTemplate.getForEntity("/rest/messages/", Message[].class);
		return ResponseEntity.status(response.getStatusCode()).body(Arrays.asList(response.getBody()));
	}
	
	private ResponseEntity<String> updateMessage(Message msgUpdated) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    return restTemplate.exchange(RESOURCE_URL + msgUpdated.getId(), HttpMethod.PUT, new HttpEntity<Message>(msgUpdated, headers), String.class);
	}
	
	private ResponseEntity<String> createMessage(String text) {
		Message msg = new Message();
		msg.setText(text);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<String> response = restTemplate.exchange(RESOURCE_URL, HttpMethod.POST, new HttpEntity<Message>(msg, headers), String.class);
		if (response.getStatusCode() == HttpStatus.CREATED) {
			String id = response.getHeaders().getLocation().toString();
			id = id.substring(id.lastIndexOf('/') + 1);
			return ResponseEntity.status(response.getStatusCode()).body(id);
		}
		return response;
	}
	
	private ResponseEntity<Message> getMessage(long id) throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity(RESOURCE_URL + id, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.status(response.getStatusCode()).body(jsonMapper.readValue(response.getBody(), Message.class));
		}
		return ResponseEntity.status(response.getStatusCode()).build();
	}
	
	private ResponseEntity<String> deleteMessage(long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return restTemplate.exchange(RESOURCE_URL + id, HttpMethod.DELETE, new HttpEntity<Message>(headers), String.class);
	}

}
