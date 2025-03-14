package org.mziuri.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import org.mziuri.model.Message;
import org.mziuri.service.Database;
import org.mziuri.validation.Validation;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MessageServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain"); // Set response content type to plain text
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String message = req.getParameter("message");

        try {
            // Validate the username and message
            if (username == null || username.trim().isEmpty()) {
                resp.setStatus(400); // Bad Request
                resp.getWriter().write("Username is required");
                return;
            }
            if (message == null || message.trim().isEmpty()) {
                resp.setStatus(400); // Bad Request
                resp.getWriter().write("Message is required");
                return;
            }

            // Check if the username exists
            if (!Database.getInstance().userExists(username)) {
                resp.setStatus(403); // Forbidden
                resp.getWriter().write("Username does not exist");
                return;
            }

            // Check if the message contains newline characters
            if (message.contains("\n")) {
                resp.setStatus(403); // Forbidden
                resp.getWriter().write("Message cannot contain newline characters");
                return;
            }

            // Add the message to the database
            boolean success = Database.getInstance().addMessage(username, message);
            if (success) {
                resp.setStatus(200); // Success
                resp.getWriter().write("Message sent successfully");
            } else {
                resp.setStatus(500); // Internal Server Error
                resp.getWriter().write("Failed to send message");
            }
        } catch (SQLException e) {
            resp.setStatus(500); // Internal Server Error
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json"); // Set response content type to JSON
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            if (!Database.getInstance().validateUser(username, password)) {
                resp.setStatus(403); // Forbidden
                resp.getWriter().write("Invalid credentials");
                return;
            }

            List<Message> messages = Database.getInstance().getMessages(username);
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(messages);
            resp.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            resp.setStatus(500); // Internal Server Error
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }
}