package com;

import com.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

public class Application {
    private final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static String RESOURCE_URL = "http://localhost:8080/rest/messages";
    private Client client;

    public Application() {
        client = ClientBuilder.newClient();
    }

    public URI createMessage(String text) {
        LOGGER.info("*** Create message '{}'", text);
        Message msg = new Message();
        msg.setText(text);
        Response response = client.target(RESOURCE_URL).request().post(Entity.json(msg));
        logResponse(response);
        if (response.getStatus() == Status.CREATED.getStatusCode()) {
            LOGGER.info("Location: {}", response.getLocation());
            return response.getLocation();
        }
        return null;
    }

    public void updateMessage(URI uri, Message msg) {
        LOGGER.info("*** Update message id: {} to text '{}'", msg.getId(), msg.getText());
        Response response = client.target(uri.toString()).request().put(Entity.json(msg));
        logResponse(response);
        if (response.getStatus() == Status.OK.getStatusCode()) {
            LOGGER.info("Message updated!");
        }
    }

    public void deleteMessage(URI uri) {
        LOGGER.info("*** Delete message: {}", uri.toString());
        Response response = client.target(uri.toString()).request().delete();
        logResponse(response);
        if (response.getStatus() == Status.OK.getStatusCode()) {
            LOGGER.info("Message deleted!");
        }
    }

    public void getMessage(URI uri, MediaType mediaType) {
        LOGGER.info("*** Get message: {}", uri.toString());
        Response response = client.target(uri.toString()).request(mediaType).get();
        logResponse(response);
        if (response.getStatus() == Status.OK.getStatusCode()) {
            //	Message msg = response.readEntity(Message.class);
            LOGGER.info("Body: {}", response.readEntity(String.class));
        }
    }

    public void getMessages(MediaType mediaType) {
        LOGGER.info("*** Get all messages");
        Response response = client.target(RESOURCE_URL).request(mediaType).get();
        logResponse(response);
        if (response.getStatus() == Status.OK.getStatusCode()) {
            Collection<Message> list = Arrays.asList(response.readEntity(Message[].class));
            list.stream().forEach(m -> LOGGER.info("Id: {}, Text: '{}'", m.getId(), m.getText()));
        }
    }

    private void logResponse(Response response) {
        LOGGER.info("Response Status: {}", response.getStatus());
        LOGGER.info("Response Info: {}", response.getStatusInfo());
        LOGGER.info("Response Headers: {}", response.getHeaders().toString());
    }

    public static void main(String[] args) throws Exception {
        Application app = new Application();
        URI uri1 = app.createMessage("Hello World 1");
        URI uri2 = app.createMessage("Hello World 2");
        app.createMessage("Hello World 3");
        // Get and delete message URI_2
        app.getMessage(uri2, MediaType.APPLICATION_JSON_TYPE);
        app.deleteMessage(uri2);
        // Update message URI_1
        Message msg = new Message();
        msg.setId(Long.valueOf(uri1.toString().substring(uri1.toString().lastIndexOf('/') + 1)));
        msg.setText("New text of message");
        app.updateMessage(uri1, msg);
        // Get message URI_1
        app.getMessage(uri1, MediaType.APPLICATION_XML_TYPE);
        // Get all messages
        app.getMessages(MediaType.APPLICATION_XML_TYPE);
    }

}
