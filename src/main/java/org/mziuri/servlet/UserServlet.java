package org.mziuri.servlet;

import jakarta.servlet.http.*;
import org.mziuri.service.Database;
import java.io.IOException;
import java.sql.SQLException;

public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            boolean success = Database.getInstance().addUser(username, password);
            if (success) {
                resp.setStatus(200); // Success
                resp.getWriter().write("User registered successfully");
            } else {
                resp.setStatus(403); // Forbidden
                resp.getWriter().write("Username already exists");
            }
        } catch (SQLException e) {
            resp.setStatus(500); // Internal Server Error
            resp.getWriter().write("Database error: " + e.getMessage());
        }
    }
}