<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <link rel="stylesheet" href="/resources/styles/tyrants.css">

    <h2>Detalles de partida</h2>
 
    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><strong><c:out value="${game.name}"/></strong></td>
        </tr>
        <tr>
            <th>Número de jugadores</th>
            <td><c:out value="${game.size}"/></td>
        </tr>
        <tr>
            <th>Mazo 1</th>
            <td><c:out value="${game.firstHalfDeck.name}"/></td>
        </tr>
        <tr>
            <th>Mazo 2</th>
            <td><c:out value="${game.secondHalfDeck.name}"/></td>
        </tr>
        <tr>
            <th>Jugadores</th>
            <td><c:out value="${game.players}"/></td> 
        </tr>
        <tr>
            <th>Creador</th>
            <td><c:out value="${game.players[0]}"/></td> 
        </tr>
    </table>

    <spring:url value="/play/{gameId}" var="editUrl">
        <spring:param name="gameId" value="${game.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Entrar</a>

</petclinic:layout>
