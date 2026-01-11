<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>

    <div class="mb-3">
            <%-- Afișează butonul Add User doar dacă userul are rolul WRITE_USERS --%>
        <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
            <%-- MODIFICARE: URL-ul este acum /AddUser (conform refactoring-ului servletului) --%>
            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddUser" role="button">Add User</a>
        </c:if>

            <%-- SARCINA 2: Arată butonul Invoice doar dacă userul are grupul INVOICING --%>
        <c:if test="${pageContext.request.isUserInRole('INVOICING')}">
            <button type="submit" form="usersForm" class="btn btn-success btn-lg">Invoice</button>
        </c:if>
    </div>

    <%-- MODIFICARE: Action-ul trebuie să fie /Users pentru a găsi servletul refactorizat --%>
    <form method="POST" action="${pageContext.request.contextPath}/Users" id="usersForm">
        <div class="container text-center">
            <c:forEach var="user" items="${users}">
                <div class="row border-bottom py-3 align-items-center">
                    <div class="col-md-1">
                            <%-- SARCINA 2: Checkbox-ul apare doar pentru cei cu drept de INVOICING --%>
                        <c:if test="${pageContext.request.isUserInRole('INVOICING')}">
                            <input type="checkbox"
                                   name="user_ids"
                                   value="${user.id}"
                                ${invoiceUserIds.contains(user.id) ? 'checked' : ''} />
                        </c:if>
                    </div>
                    <div class="col-md-5">
                            ${user.username}
                    </div>
                    <div class="col-md-6">
                            ${user.email}
                    </div>
                </div>
            </c:forEach>
        </div>
    </form>

    <%-- SARCINA 2: Secțiunea Invoices apare doar pentru grupul INVOICING --%>
    <c:if test="${pageContext.request.isUserInRole('INVOICING')}">
        <div class="mt-5">
            <h3>Invoices</h3>
            <c:choose>
                <c:when test="${not empty invoices}">
                    <c:forEach var="username" items="${invoices}" varStatus="status">
                        ${status.count}. ${username}<br/>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p class="text-muted">No users selected for invoice</p>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</t:pageTemplate>