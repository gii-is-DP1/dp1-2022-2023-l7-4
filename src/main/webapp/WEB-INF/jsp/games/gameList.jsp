<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="game">
    <h2>Partidas</h2>
    <link rel="stylesheet" href="/resources/styles/tyrants.css">
    <a class="btn btn-default" href='<spring:url value="/games/find" htmlEscape="true"/>'>Buscar una partida</a>
    <a class="btn btn-default" href='<spring:url value="/games/create" htmlEscape="true"/>'>Crear una partida</a>

    <table id="gameTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre</th>
            <th style="width: 200px;">Número de jugadores</th>
            <th style="width: 100px;"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="game">
            <tr>
                <td>
                    <spring:url value="/games/{gameId}" var="gameUrl">
                        <spring:param name="gameId" value="${game.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(gameUrl)}"><c:out value="${game.name}"/></a>
                </td>
                <td>
                    <c:out value="${game.size}"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${game.finished=='FALSE'}">
                        <div id="greenball"></div>
                        <br />
                        </c:when>    
                         <c:otherwise>
                             <div id="redball"></div>
                            <br />
                        </c:otherwise>
                    </c:choose>
                </td>

                
            </tr>
        </c:forEach>
        </tbody>
    </table>

    
</petclinic:layout>
<style>
    #redball{
        background-color: red;
        width: 10px;
        height: 10px;
        box-shadow: 0px 0px 10px darkred;
        border-radius: 50%;
    }
    #greenball{
        background-color: green;
        width: 10px;
        height: 10px;
        box-shadow: 0px 0px 10px darkgreen;
        border-radius: 50%;
    }
</style>