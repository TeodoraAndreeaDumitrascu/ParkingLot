<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>

    <div class="mb-3">
            <%-- Arată butonul Add User doar dacă userul are WRITE_USERS --%>
        <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddUser" role="button">Add User</a>
        </c:if>

            <%-- Buton pentru Invoice --%>
        <button type="submit" form="usersForm" class="btn btn-success btn-lg">Invoice</button>
    </div>

    <%-- Formularul care înconjoară tot conținutul --%>
    <form method="POST" action="${pageContext.request.contextPath}/Users" id="usersForm">
        <div class="container text-center">
            <c:forEach var="user" items="${users}">
                <div class="row border-bottom py-3 align-items-center">
                    <div class="col-md-1">
                            <%-- Checkbox pentru selectare user --%>
                        <input type="checkbox"
                               name="user_ids"
                               value="${user.id}"
                            ${invoiceUserIds.contains(user.id) ? 'checked' : ''} />
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

    <%-- Secțiunea Invoices --%>
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
</t:pageTemplate>