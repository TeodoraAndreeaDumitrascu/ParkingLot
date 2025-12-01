<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header data-bs-theme="dark">
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">Parking Lot</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">
                        <a class="nav-link ${pageContext.request.requestURI.substring(pageContext.request.requestURI.lastIndexOf("/")) eq '/about.jsp' ? 'active' : ''}"
                           aria-current="page"
                           href="${pageContext.request.contextPath}/about.jsp">About</a>
                    </li>

                    <%-- Arată Cars doar dacă userul are READ_CARS --%>
                    <c:if test="${pageContext.request.isUserInRole('READ_CARS')}">
                        <li class="nav-item">
                            <a class="nav-link ${activePage eq 'cars' ? 'active' : ''}"
                               href="${pageContext.request.contextPath}/Cars">Cars</a>
                        </li>
                    </c:if>

                    <%-- Arată Users doar dacă userul are READ_USERS --%>
                    <c:if test="${pageContext.request.isUserInRole('READ_USERS')}">
                        <li class="nav-item">
                            <a class="nav-link ${activePage eq 'users' ? 'active' : ''}"
                               href="${pageContext.request.contextPath}/Users">Users</a>
                        </li>
                    </c:if>

                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" aria-disabled="true">Disabled</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${pageContext.request.getRemoteUser() != null}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>

            </div>
        </div>
    </nav>
</header>