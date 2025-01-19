package com.example.support.gestion_tickets.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.support.gestion_tickets.config.DatabaseConnection;
import com.example.support.gestion_tickets.models.User;

@Path("/users")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id, username, email, password, role FROM users";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),  // Remplacé 'name' par 'username'
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Path("/auth")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User loginRequest) {
        System.out.println("Login request: email = " + loginRequest.getEmail() + ", password = " + loginRequest.getPassword());
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id, username, email, role FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, loginRequest.getEmail());
            stmt.setString(2, loginRequest.getPassword());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    null, // Ne pas inclure le mot de passe dans la réponse
                    rs.getString("role")
                );
                return Response.ok(user).build(); // Succès
            } else {
                System.out.println("Invalid credentials for email: " + loginRequest.getEmail());
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }
}
