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
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Collection;

@Path("/messages")
public class MessagesResource implements ExceptionMapper<Throwable> {
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
            if (msg.getId() != id) {
                throw new IllegalArgumentException("Object id cannot be different than the parameter id");
            }
            Message dbMsg = messageManager.getMessage(id);
            if (dbMsg == null) {
                throw new IllegalArgumentException(String.format("Message id '%d' does not exists!", id));
            }
            messageManager.updateMessage(msg);
            return Response.status(Status.OK).build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMessage(@PathParam("id") long id) {
        try {
            Message msg = messageManager.getMessage(id);
            if (msg == null) {
                throw new Exception(String.format("Message id '%d' does not exists!", id));
            }
            messageManager.deleteMessage(msg.getId());
            return Response.status(Status.OK).build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage(@PathParam("id") long id) {
        try {
            Message msg = messageManager.getMessage(id);
            if (msg == null) {
                throw new Exception(String.format("Message id '%d' does not exists!", id));
            }
            return Response.status(Status.OK).entity(msg).build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
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

    @Override
    public Response toResponse(Throwable ex) {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(ex.getMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
