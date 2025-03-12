package org.mziuri.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mziuri.model.Brand;
import org.mziuri.model.Car;

import java.io.IOException;

public class DataServlet extends HttpServlet {

    private ObjectMapper mapper;
    private Car car;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mapper = new ObjectMapper();
        car = new Car(Brand.MAZDA, "Mazda 3", 2010);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        mapper.writeValue(resp.getWriter(), car);
        resp.setContentType("application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        car = mapper.readValue(req.getReader(), Car.class);

        mapper.writeValue(resp.getWriter(), car);
        resp.setContentType("application/json");
    }
}
