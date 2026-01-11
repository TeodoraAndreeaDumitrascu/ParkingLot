<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>
    <form method="POST" action="${pageContext.request.contextPath}/Cars">

        <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddCar" role="button">Add Car</a>
            <button class="btn btn-danger" type="submit">Delete Cars</button>
        </c:if>

        <div class="container text-center">
            <c:forEach var="car" items="${cars}">
                <div class="row">

                    <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                        <div class="col">
                            <input type="checkbox" name="car_ids" value="${car.id}" />
                        </div>
                    </c:if>

                    <div class="col">
                            ${car.licensePlate}
                    </div>
                    <div class="col">
                            ${car.parkingSpot}
                    </div>
                    <div class="col">
                            ${car.ownerName}
                    </div>

                    <div class="col">
                            <%-- Am corectat calea către servletul de afișare a pozei --%>
                        <img src="${pageContext.request.contextPath}/DisplayCarPhoto?id=${car.id}" width="48"/>
                    </div>

                    <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                        <div class="col">
                                <%-- Am corectat link-ul către adăugarea pozei --%>
                            <a class="btn btn-secondary btn-sm me-1" href="${pageContext.request.contextPath}/AddCarPhoto?id=${car.id}">Add photo</a>
                                <%-- Am corectat link-ul către EditCar folosind parametrul ?id= --%>
                            <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/EditCar?id=${car.id}">Edit Car</a>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </form>

    <%-- Valoarea este acum calculată dinamic în Cars.java (10 - cars.size()) --%>
    <h5>Free parking spots: ${numberOfFreeParkingSpots}</h5>
</t:pageTemplate>