package com.parking.parkinglot.servlets;

import com.parking.parkinglot.common.CarPhotoDto;
import com.parking.parkinglot.ejb.CarsBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DisplayCarPhoto", value = "/DisplayCarPhoto")
public class DisplayCarPhoto extends HttpServlet {

    @Inject
    private CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String carIdStr = request.getParameter("id");
        Integer carId = Integer.parseInt(carIdStr);

        CarPhotoDto photo = carsBean.findPhotoByCarId(carId);

        if (photo != null) {
            response.setContentType(photo.getFileType());
            response.getOutputStream().write(photo.getFileContent());
        }
    }
}