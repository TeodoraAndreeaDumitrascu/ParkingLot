package com.parking.parkinglot.servlets;

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
import java.util.Arrays;
import java.util.Collection;

@WebServlet(name = "AddUser", value = "/AddUser")
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = {"WRITE_USERS"}),
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_USERS"})
        }
)
public class AddUser extends HttpServlet {

    @Inject
    private UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Setăm grupurile disponibile pentru a fi afișate în formular
        request.setAttribute("userGroups", new String[]{"READ_CARS", "WRITE_CARS", "READ_USERS", "WRITE_USERS"});
        request.getRequestDispatcher("/WEB-INF/pages/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recuperăm valorile din formular
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String[] userGroupsArray = request.getParameterValues("user_groups");

        if (userGroupsArray != null) {
            Collection<String> userGroups = Arrays.asList(userGroupsArray);
            usersBean.createUser(username, email, password, userGroups);
        }

        // Redirecționăm către pagina Users
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}