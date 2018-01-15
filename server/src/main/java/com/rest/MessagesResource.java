package com.rest;

import com.domain.Message;
import com.service.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

@Path("/messages")
public class MessagesResource {
    private final MessageManager messageManager;
    private int counter;

    @Autowired
    public MessagesResource(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMessage(Message msg, @Context UriInfo uriInfo) {
        long id = messageManager.createMessage(msg);
        return Response.status(Status.CREATED)
                .header("Location", uriInfo.getAbsolutePath() + "/" + id)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMessage(@PathParam("id") long id, Message msg) {
        try {
            Message dbMsg = messageManager.getMessage(id);
            if (dbMsg == null) {
                throw new IllegalArgumentException("Resource not found");
            }
            if (msg.getId() != id) {
                throw new IllegalArgumentException("Object id cannot be different than the parameter id");
            }
            msg.setId(dbMsg.getId());
            messageManager.updateMessage(msg);
            return Response.status(Status.OK).build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMessage(@PathParam("id") long id) {
        try {
            Message msg = messageManager.getMessage(id);
            if (msg == null) {
                throw new Exception("Resource not found");
            }
            messageManager.deleteMessage(msg.getId());
            return Response.status(Status.OK).build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage(@PathParam("id") long id) {
        Message msg = messageManager.getMessage(id);
        if (msg != null) {
            return Response.status(Status.OK).entity(msg).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessages() {
        Collection<Message> msgs = messageManager.getMessages();
        return Response.status(Status.OK).entity(msgs.toArray(new Message[msgs.size()])).build();
    }

    @GET
    @Path("/counter")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCounter() {
        counter++;
        return Response.status(Status.OK).entity(counter).build();
    }

}
