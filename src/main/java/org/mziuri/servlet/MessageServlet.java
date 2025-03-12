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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            Database db = Database.getInstance();
            if (db.validateUser(username, password)) {
                List<Message> messages = db.getMessages(username);
                resp.setContentType("application/json");
                mapper.writeValue(resp.getWriter(), messages);
            } else {
                resp.sendError(403, "Invalid credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(500, "Database error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String username = req.getParameter("username");
            String message = req.getParameter("message");

            Database db = Database.getInstance();
            Validation validation = Validation.getInstance();

            if (!db.userExists(username) || !validation.isMessageValid(message)) {
                resp.sendError(403);
                return;
            }

            db.addMessage(username, message);
            resp.setStatus(200);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(500, "Database error");
        }
    }
}