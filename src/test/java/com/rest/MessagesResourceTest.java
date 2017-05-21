package com.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.domain.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MessagesResourceTest {
	private static final String RESOURCE_URL = "/rest/messages";
	
	@Autowired
	private TestRestTemplate restTemplate;

//	@Before
//	public void setUp() throws Exception {
//		
//	}
	@Test
	public void testCreateMessage() throws Exception {
		String TEXT = "The chosen one";
		Message msg = new Message.MessageBuilder().text(TEXT).build();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
//	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));	
	    // (java.lang.String) {"id":1,"text":"Hello World"}
//	    ResponseEntity<Message> response = restTemplate.exchange(RESOURCE_URL, HttpMethod.POST, new HttpEntity<Message>(msg, headers), Message.class);
//	    ResponseEntity<String> response = restTemplate.exchange(RESOURCE_URL, HttpMethod.POST, new HttpEntity<Message>(msg, headers), String.class);
	    ResponseEntity<String> response = restTemplate.exchange(RESOURCE_URL, HttpMethod.POST, new HttpEntity<String>("{\"text\":\"Hello World\"}", headers), String.class);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    JsonNode node = new ObjectMapper().readValue(response.getBody(), JsonNode.class);
	    assertTrue(node.get("id").asLong() > 0);
//	    assertEquals(TEXT, response.getBody().getText());
	    
//		ResponseEntity<String> entity = testRestTemplate.getForEntity("https://localhost:" + this.port, String.class);

		
//		restTemplate.postForEntity(RESOURCE_URL, msg, responseType)
		
//		String body = restTemplate.getForObject("/rest/messages/", String.class);
		
	}
	
	@Test
	public void testGetMessage() throws Exception {
		long MESSAGE_ID = 1l;
		ResponseEntity<String> response = restTemplate.getForEntity("/rest/messages/" + MESSAGE_ID, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	    JsonNode node = new ObjectMapper().readValue(response.getBody(), JsonNode.class);
		assertEquals(MESSAGE_ID, node.get("id").asLong());
	}

	/*
	OAuth2AccessToken token = context.getAccessToken();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    @SuppressWarnings("rawtypes")	 * 
	ResponseEntity<Map> response = new TestRestTemplate("my-client-with-secret", "secret")
	response.exchange(http.getUrl(checkTokenPath()), HttpMethod.POST, new HttpEntity<String>("token=" + token.getValue(), headers), Map.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
	 */
	
	/*
	headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
    form.set("username", "user");
    form.set("password", "user");
    
    ResponseEntity<String> entity = new TestRestTemplate().exchange(
        "http://localhost:" + this.port + "/login", HttpMethod.POST,
        new HttpEntity<MultiValueMap<String, String>>(form, headers),
        String.class);
        
    assertEquals(HttpStatus.FOUND, entity.getStatusCode());
    assertTrue("Wrong location:\n" + entity.getHeaders(), entity.getHeaders()
	 */
	
	/*
	ResponseEntity<String> entity = testRestTemplate.getForEntity("https://localhost:" + this.port, String.class);
	assertEquals(HttpStatus.OK, entity.getStatusCode());
	assertEquals("Hello, Secret Property: chupacabras", entity.getBody());
	 */

}
