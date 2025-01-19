package com.example.support.gestion_tickets.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.support.gestion_tickets.config.DatabaseConnection;
import com.example.support.gestion_tickets.models.Ticket;

@Path("/tickets")
public class TicketResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT id, title, description, status, creation_date, assigned_to FROM tickets";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("creation_date").toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt("assigned_to")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createTicket(Ticket ticket) {
        String query = "INSERT INTO tickets (title, description, status, creation_date, assigned_to) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ticket.getTitle());
            stmt.setString(2, ticket.getDescription());
            stmt.setString(3, ticket.getStatus());
            stmt.setTimestamp(4, java.sql.Timestamp.from(ticket.getCreationDate().toInstant()));
            stmt.setInt(5, ticket.getAssignedTo());
            stmt.executeUpdate();
            return "Ticket créé avec succès !";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la création du ticket.";
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateTicket(@PathParam("id") int id, Ticket ticket) {
        String query = "UPDATE tickets SET title = ?, description = ?, status = ?, assigned_to = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ticket.getTitle());
            stmt.setString(2, ticket.getDescription());
            stmt.setString(3, ticket.getStatus());
            stmt.setInt(4, ticket.getAssignedTo());
            stmt.setInt(5, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Ticket mis à jour avec succès !";
            } else {
                return "Aucun ticket trouvé avec cet ID.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la mise à jour du ticket.";
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteTicket(@PathParam("id") int id) {
        String query = "DELETE FROM tickets WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                return "Ticket supprimé avec succès !";
            } else {
                return "Aucun ticket trouvé avec cet ID.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la suppression du ticket.";
        }
    }
}