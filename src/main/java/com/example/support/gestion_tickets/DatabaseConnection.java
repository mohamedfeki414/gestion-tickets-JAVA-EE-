package com.example.support.gestion_tickets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/projet_db?serverTimezone=UTC"; // Vérifiez l'URL
        String username = "root"; // Utilisez votre utilisateur MySQL
        String password = "123456"; // Utilisez votre mot de passe MySQL

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Charger le driver
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        if (connection != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Échec de la connexion !");
        }
    }
}
