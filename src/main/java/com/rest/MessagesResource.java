package com.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.domain.Message;
import com.service.MessageManager;

@Path("/messages")
@Component
public class MessagesResource {
	private final MessageManager messageManager;

	@Autowired
	public MessagesResource(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getMessages() {
//		Collection<Message> msgs = messageManager.getMessages();
//		return Response.ok().entity(msgs).build();
//	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessage(@PathParam("id") Long id) {
		Message msg = messageManager.getMessage(id);
		return Response.ok().entity(msg).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMessage(Message msg) {
		Message newMsg = messageManager.createMessage(msg);
		return Response.ok().entity(newMsg).build();
	}

//
//	@PUT
//	@Path("/{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateMessage(@PathParam("id") Long id, @PathParam("message") Message msg) {
//		messageManager.updateMessage(msg);
//		return Response.ok().build();
//	}
//
//	@DELETE
//	@Path("/{id}")
//	public Response deleteMessage(@PathParam("id") Long id) {
//		messageManager.deleteMessage(id);
//		// TODO Should be code 202?
//		return Response.ok().build();
//	}

}
