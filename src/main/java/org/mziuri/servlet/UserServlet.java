package org.mziuri.servlet;

import jakarta.servlet.http.*;
import org.mziuri.service.Database;
import java.io.IOException;
import java.sql.SQLException;

public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (username == null || password == null) {
                resp.sendError(400, "Username and password are required");
                return;
            }

            boolean success = Database.getInstance().addUser(username, password);

            if (success) resp.setStatus(200);
            else resp.sendError(403, "Username already exists");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(500, "Database error");
        }
    }
}