package com.example.support.gestion_tickets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données
            String url = "jdbc:mysql://localhost:3306/tickets_db";
            String username = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                System.out.println("Connexion réussie !");
            } else {
                System.out.println("Échec de la connexion !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
