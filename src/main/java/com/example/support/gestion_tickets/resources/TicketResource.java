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
        String query = "SELECT id, match_id, price, zone, availability FROM tickets";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("match_id"),
                        rs.getDouble("price"),
                        rs.getString("zone"),
                        rs.getString("availability")
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
        String query = "INSERT INTO tickets (match_id, price, zone, availability) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ticket.getMatchId());
            stmt.setDouble(2, ticket.getPrice());
            stmt.setString(3, ticket.getZone());
            stmt.setString(4, ticket.getAvailability());
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
        String query = "UPDATE tickets SET match_id = ?, price = ?, zone = ?, availability = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ticket.getMatchId());
            stmt.setDouble(2, ticket.getPrice());
            stmt.setString(3, ticket.getZone());
            stmt.setString(4, ticket.getAvailability());
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

    @GET
    @Path("/match/{matchId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getTicketsByMatch(@PathParam("matchId") int matchId) {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT id, match_id, price, zone, availability FROM tickets WHERE match_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, matchId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = new Ticket(
                            rs.getInt("id"),
                            rs.getInt("match_id"),
                            rs.getDouble("price"),
                            rs.getString("zone"),
                            rs.getString("availability")
                    );
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
