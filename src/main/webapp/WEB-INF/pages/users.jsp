<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>
    <c:forEach var="user" items="${users}">
        <div class="row">
            <div class="col-md-6">
                    ${user.username}
            </div>
            <div class="col-md-6">
                    ${user.email}
            </div>
        </div>
    </c:forEach>
</t:pageTemplate>