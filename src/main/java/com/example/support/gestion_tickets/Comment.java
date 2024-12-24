package com.example.support.gestion_tickets;

import java.util.Date;

public class Comment {
    private int id;
    private int ticketId;
    private int userId;
    private String content;
    private Date creationDate;

    public Comment() {
    }

    public Comment(int id, int ticketId, int userId, String content, Date creationDate) {
        this.id = id;
        this.ticketId = ticketId;
        this.userId = userId;
        this.content = content;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
