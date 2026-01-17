package com.parking.parkinglot.servlets.users;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "Users", value = "/Users")
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"READ_USERS"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"READ_USERS"})
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

        if (!invoiceBean.getUserIds().isEmpty()) {
            Collection<String> invoiceUsernames = usersBean.findUsernamesByUserIds(invoiceBean.getUserIds());
            request.setAttribute("invoices", invoiceUsernames);
            request.setAttribute("invoiceUserIds", invoiceBean.getUserIds());
        }

        request.getRequestDispatcher("/WEB-INF/pages/users/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] userIdsAsString = request.getParameterValues("user_ids");

        // Șterge lista curentă
        invoiceBean.getUserIds().clear();

        if (userIdsAsString != null) {
            List<Long> userIds = new ArrayList<>();
            for (String userIdAsString : userIdsAsString) {
                userIds.add(Long.parseLong(userIdAsString));
            }
            invoiceBean.getUserIds().addAll(userIds);
        }

        response.sendRedirect(request.getContextPath() + "/Users");
    }
}