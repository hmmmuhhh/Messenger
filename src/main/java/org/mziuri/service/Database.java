package org.mziuri.service;

import org.mziuri.model.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance;
    private final Connection connection;

    private Database() {
        try {
            Class.forName("org.postgresql.Driver");

            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");

            if (dbUrl == null || dbUser == null || dbPassword == null) {
                throw new RuntimeException("Database environment variables are not set!");
            }
            System.out.println("Connecting to database: " + dbUrl);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public boolean addUser(String username, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) return false;
            throw e;
        }
    }

    public boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getString(1).equals(password);
        }
    }

    public boolean addMessage(String receiver, String content) throws SQLException {
        String sql = "INSERT INTO messages (receiver_username, content) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, receiver);
            stmt.setString(2, content);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding message to database:");
            e.printStackTrace();
            throw e;
        }
    }

    public List<Message> getMessages(String username) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT content, timestamp::text FROM messages WHERE receiver_username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String content = rs.getString("content");
                String timestamp = rs.getString("timestamp");
                System.out.println("Retrieved message: " + timestamp + " - " + content);
                messages.add(new Message(content, timestamp));
            }
        }
        return messages;
    }

    public boolean userExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeQuery().next();
        }
    }
}