package com.example.support.gestion_tickets;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/tickets")
public class TicketResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id, title, description, status, creation_date, assigned_to FROM tickets";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("creation_date").toLocalDateTime(),
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
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO tickets (title, description, status, creation_date, assigned_to) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ticket.getTitle());
            stmt.setString(2, ticket.getDescription());
            stmt.setString(3, ticket.getStatus());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(ticket.getCreationDate()));
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
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE tickets SET title = ?, description = ?, status = ?, assigned_to = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ticket.getTitle());
            stmt.setString(2, ticket.getDescription());
            stmt.setString(3, ticket.getStatus());
            stmt.setInt(4, ticket.getAssignedTo());
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return "Ticket mis à jour avec succès !";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la mise à jour du ticket.";
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteTicket(@PathParam("id") int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM tickets WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return "Ticket supprimé avec succès !";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la suppression du ticket.";
        }
    }
}
