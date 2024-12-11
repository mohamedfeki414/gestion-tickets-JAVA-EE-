package com.example.support.gestion_tickets;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "JohnDoe", "john.doe@example.com"));
        users.add(new User(2, "JaneSmith", "jane.smith@example.com"));
        return users;
    }
}

