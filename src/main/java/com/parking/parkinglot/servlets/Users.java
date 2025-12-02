package com.parking.parkinglot.servlets;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.InvoiceBean;
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
import java.util.Collection;
import java.util.List;

@WebServlet(name = "Users", value = "/Users")
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"READ_USERS"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_USERS"})
        }
)
public class Users extends HttpServlet {
    @Inject
    private UsersBean usersBean;

    @Inject
    private InvoiceBean invoiceBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<UserDto> users = usersBean.findAllUsers();
        request.setAttribute("users", users);

        // Trimite lista de IDs din invoice către JSP
        request.setAttribute("invoiceUserIds", invoiceBean.getUsersIds());

        // ADAUGĂ ASTA - Găsește username-urile pentru userii din invoice
        Collection<String> invoiceUsernames = usersBean.findUsernamesByUserIds(invoiceBean.getUsersIds());
        request.setAttribute("invoices", invoiceUsernames);

        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Preia valorile checkboxurilor selectate
        String[] userIdsAsString = request.getParameterValues("user_ids");

        if (userIdsAsString != null) {
            // Golește lista curentă din invoice
            invoiceBean.getUsersIds().clear();

            // Adaugă toate ID-urile selectate
            for (String userIdString : userIdsAsString) {
                invoiceBean.getUsersIds().add(Long.parseLong(userIdString));
            }
        } else {
            // Dacă nu e nimic selectat, golește lista
            invoiceBean.getUsersIds().clear();
        }

        // Redirecționează înapoi la pagina de users
        response.sendRedirect(request.getContextPath() + "/Users");
    }
}