<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">

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
    </table>

    <spring:url value="join/{gameId}" var="editUrl">
        <spring:param name="gameId" value="${game.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Join game</a>

</petclinic:layout>
