package com.parking.parkinglot.servlets.cars;

import com.parking.parkinglot.common.CarDto;
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

@WebServlet(name = "EditCar", value = "/EditCar")
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = {"WRITE_CARS"}),
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_CARS"})
        }
)
public class EditCar extends HttpServlet {
    @Inject
    UsersBean usersBean;

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);

        try {
            // Citim ID-ul din parametrul ?id= din URL
            Long carId = Long.parseLong(request.getParameter("id"));
            CarDto car = carsBean.findById(carId);
            request.setAttribute("car", car);

            request.getRequestDispatcher("/WEB-INF/pages/cars/editCar.jsp").forward(request, response);
        } catch (Exception ex) {
            // Daca ID-ul lipseste sau e gresit, mergem inapoi la lista
            response.sendRedirect(request.getContextPath() + "/Cars");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Preluam datele din formularul editCar.jsp
        String licensePlate = request.getParameter("license_plate");
        String parkingSpot = request.getParameter("parking_spot");
        Long userId = Long.parseLong(request.getParameter("owner_id"));
        Long carId = Long.parseLong(request.getParameter("car_id"));

        // Actualizam masina in baza de date
        carsBean.updateCar(carId, licensePlate, parkingSpot, userId);

        // Ne intoarcem la lista de masini
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}