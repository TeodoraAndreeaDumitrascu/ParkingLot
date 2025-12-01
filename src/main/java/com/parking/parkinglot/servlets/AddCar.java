package com.parking.parkinglot.servlets;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.CarsBean;
import com.parking.parkinglot.ejb.UsersBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "AddCar", value = "/AddCar")
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = {"WRITE_CARS"}),
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_CARS"})
        }
)
public class AddCar extends HttpServlet {
    @Inject
    CarsBean carsBean;

    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users=usersBean.findAllUsers();
        request.setAttribute("users",users);
        request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperăm valorile din formular
        String licensePlate = request.getParameter("license_plate");
        String parkingSpot = request.getParameter("parking_spot");
        Long userId = Long.parseLong(request.getParameter("owner_id"));
        // Apelăm CarsBean pentru a crea mașina
        carsBean.createCar(licensePlate, parkingSpot, userId);
        // Redirecționăm utilizatorul înapoi la pagina Cars
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}