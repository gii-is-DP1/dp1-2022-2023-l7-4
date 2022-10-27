<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="positions">
    <h2>Positions</h2>
    
    <table id="positionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Territory</th>
            <th>Occupied</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${positions}" var="position">
            <tr>
                <td>
                    <c:out value="${position.id}"/>
                </td>
                <td>
                    <c:out value="${position.territory} "/>
                </td>
                <td>
                    <c:out value="${position.occupied}"/>
                </td>
                <td>
                    <button name="Ocupar">
                        
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:forEach items="${freePositions}" var="freePosition">
        <c:out value="${freePosition.id}"/>
    </c:forEach>
    <spring:url value="/resources/images/example_map.jpg" htmlEscape="true" var="map"/>
            <img class="img-responsive" src="${map}"/>

</petclinic:layout>