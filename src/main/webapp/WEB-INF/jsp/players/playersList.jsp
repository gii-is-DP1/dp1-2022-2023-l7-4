<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="players">
    <h2>Players</h2>

    <table id="playersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Email</th>
            <th>BirthDate</th>
            <th>Username</th>
            <th>Password</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="player">
            <tr>
                <td>
                    <spring:url value="/players/{playerId}" var="playerUrl">
                        <spring:param name="playerId" value="${player.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(playerUrl)}"><c:out value="${player.name}"/></a>
                </td>
                <td>
                    <c:out value="${player.email}"/>
                </td>
                <td>
                    <c:out value="${player.birthdate}"/>
                </td>
                <td> 
                    <c:out value="${player.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${player.user.password}"/> 
                </td> 

                
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-default" href='<spring:url value="/players/find" htmlEscape="true"/>'>Find a player</a>
</petclinic:layout>
