<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <link rel="stylesheet" href="/resources/styles/tyrants.css">

    <h2>Game Information</h2>
 
    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><strong><c:out value="${game.name}"/></strong></td>
        </tr>
        <tr>
            <th>Size</th>
            <td><c:out value="${game.size}"/></td>
        </tr>
        <tr>
            <th>Deck 1</th>
            <td><c:out value="${game.firstHalfDeck.name}"/></td>
        </tr>
        <tr>
            <th>Deck 2</th>
            <td><c:out value="${game.secondHalfDeck.name}"/></td>
        </tr>
        <tr>
            <th>Players</th>
            <td><c:out value="${game.players}"/></td>
        </tr>
    </table>

    <spring:url value="/play/{gameId}" var="editUrl">
        <spring:param name="gameId" value="${game.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Entrar</a>

</petclinic:layout>
