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
import com.example.support.gestion_tickets.models.Matche;
import com.example.support.gestion_tickets.models.Ticket;

@Path("/matches")
public class MatchResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Matche> getMatches() {
        List<Matche> matches = new ArrayList<>();
        String query = "SELECT id, match_date, title, stadium, location FROM matches";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Matche matche = new Matche(
                        rs.getInt("id"),
                        rs.getDate("match_date").toLocalDate(),
                        rs.getString("title"),
                        rs.getString("stadium"),
                        rs.getString("location"), null
                        
                );
                matches.add(matche);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createMatch(Matche matche) {
        String query = "INSERT INTO matches (match_date, title, stadium, location) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, java.sql.Date.valueOf(matche.getMatchDate()));
            stmt.setString(2, matche.getTitle());
            stmt.setString(3, matche.getStadium());
            stmt.setString(4, matche.getLocation());
            stmt.executeUpdate();
            return "Match créé avec succès !";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la création du match.";
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateMatch(@PathParam("id") int id, Matche matche) {
        String query = "UPDATE matches SET match_date = ?, title = ?, stadium = ?, location = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, java.sql.Date.valueOf(matche.getMatchDate()));
            stmt.setString(2, matche.getTitle());
            stmt.setString(3, matche.getStadium());
            stmt.setString(4, matche.getLocation());
            stmt.setInt(5, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Match mis à jour avec succès !";
            } else {
                return "Aucun match trouvé avec cet ID.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la mise à jour du match.";
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteMatch(@PathParam("id") int id) {
        String query = "DELETE FROM matches WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                return "Match supprimé avec succès !";
            } else {
                return "Aucun match trouvé avec cet ID.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la suppression du match.";
        }
    }

    @GET
    @Path("/{id}/tickets")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getTicketsByMatch(@PathParam("id") int matchId) {
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
