package com.example.support.gestion_tickets.models;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Matche {
    private int id;
    private LocalDate matchDate; 
    private String title;
    private String stadium;
    private String location;
    private List<Ticket> tickets;

    public Matche() {
    }

    public Matche(int id, LocalDate matchDate, String title, String stadium, String location, List<Ticket> tickets) {
        this.id = id;
        this.matchDate = matchDate;
        this.title = title;
        this.stadium = stadium;
        this.location = location;
        this.tickets = (tickets != null) ? tickets : new ArrayList<>();
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Matche{" +
                "id=" + id +
                ", matchDate=" + matchDate +
                ", title='" + title + '\'' +
                ", stadium='" + stadium + '\'' +
                ", location='" + location + '\'' +
                ", tickets=" + tickets +
                '}';
    }
}
