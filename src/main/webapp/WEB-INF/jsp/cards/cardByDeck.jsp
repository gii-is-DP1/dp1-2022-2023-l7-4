<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<petclinic:layout pageName="saerchingDeck">
    <h2>Cartas </h2>
    <script>
        console.log("ssss")
    </script>

      <form:form modelAttribute="halfDeck" action="/card/searchingDeck" method="get" class="form-horizontal"
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

       
    <!-- 
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
            <c:forEach items="${cards}" var="cardByDeck">
            <tr">
                <td>
                    <c:out value="${cards.id}"/>
                </td>
                <td>
                    <spring:url value="/card/searchingCard/{cardId}" var="cardUrl">
                        <spring:param name="cardId" value="${cards.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(cardUrl)}"><c:out value="${cards.name}"/></a>
                </td>
                <td>
                    <c:out value="${cards.cost} "/>
                </td>
                <td>
                    <c:out value="${cards.story} "/>
                </td>
                <td>
                    <c:out value="${cards.rulesText} "/>
                </td>
                <td>
                    <c:out value="${cards.deckVP} "/>
                </td>   
                <td>
                    <c:out value="${cards.innerCirclePV} "/>
                </td>   
                <td>
                    <c:out value="${cards.rarity} "/>
                </td>
                <td>
                    <c:out value="${cards.aspect.name} "/>
                </td>
                <td>
                    <c:out value="${cards.halfDeck.name} "/>
                </td>
            </tr>
            <c:forEach/>
       </tbody>
    </table>
-->
</petclinic:layout>