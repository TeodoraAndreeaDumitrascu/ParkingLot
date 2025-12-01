<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Add User">
    <h1>Add User</h1>

    <form method="POST" action="${pageContext.request.contextPath}/AddUser">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Groups</label>
            <select multiple class="form-select" name="user_groups" size="4" required>
                <c:forEach var="group" items="${userGroups}">
                    <option value="${group}">${group}</option>
                </c:forEach>
            </select>
            <small class="form-text text-muted">Hold Ctrl (Windows) or Cmd (Mac) to select multiple groups</small>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
        <a href="${pageContext.request.contextPath}/Users" class="btn btn-secondary">Cancel</a>
    </form>
</t:pageTemplate>