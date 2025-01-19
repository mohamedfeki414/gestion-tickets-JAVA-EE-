package com.example.support.gestion_tickets.models;

public class Ticket {
    private int id;
    private int matchId;
    private double price;
    private String zone; // gradin, chaise, derriere les buts, etc.
    private String availability; // disponible, non disponible

    public Ticket() {
    }

    public Ticket(int id, int matchId, double price, String zone, String availability) {
        this.id = id;
        this.matchId = matchId;
        this.price = price;
        this.zone = zone;
        this.availability = availability;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", matchId=" + matchId +
                ", price=" + price +
                ", zone='" + zone + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }
}

