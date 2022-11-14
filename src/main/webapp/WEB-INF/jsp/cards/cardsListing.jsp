<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<petclinic:layout pageName="cards">
    
<h2>Cartas</h2>
    <script>
        console.log("ssss")
    </script>

<h2>Buscar carta</h2>

    
<form:form modelAttribute="card" action="/cards/all" method="get" class="form-horizontal"
           id="search-owner-form">
    <div class="form-group">
        <div class="control-group" id="name">
            <label class="col-sm-2 control-label"> Nombre de la carta </label>
            <div class="col-sm-10">
                <form:input class="form-control" path="name" size="30" maxlength="80"/>
                <span class="help-inline"><form:errors path="*"/></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">Buscar</button>
        </div>
    </div>
</form:form>
    
    <table id="positionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Coste</th>
            <th>Historia</th>
            <th>Acción de la carta</th>
            <th>PV</th>
            <th>PV en Círculo interno</th>
            <th>Rareza</th>
            <th>Aspecto</th>
            <th>Mazo</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="card" items="${cards}" >
            <tr>
                <td>
                    <c:out value="${card.id}"/>
                </td>
                <td>
                    <spring:url value="/cards/{cardId}" var="cardUrl">
                        <spring:param name="cardId" value="${card.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(cardUrl)}"><c:out value="${card.name}"/></a>
                </td>
                <td>
                    <c:out value="${card.cost} "/>
                </td>
                <td>
                    <c:out value="${card.story} "/>
                </td>
                <td>
                    <c:out value="${card.rulesText} "/>
                </td>
                <td>
                    <c:out value="${card.deckVP} "/>
                </td>   
                <td>
                    <c:out value="${card.innerCirclePV} "/>
                </td>   
                <td>
                    <c:out value="${card.rarity} "/>
                </td>
                <td>
                    <c:out value="${card.aspect.name} "/>
                </td>
                <td>
                    <c:out value="${card.halfDeck.name} "/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>