<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <link rel="stylesheet" href="/resources/styles/tyrants.css">
    <h2>Información de usuario</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre de usuario</th>
            <td><strong><c:out value="${user.username}"/></strong></td>
        </tr>
        <tr>
            <th>Contraseña</th>
            <td><c:out value="${user.password}"/></td>
        </tr>
        <tr>
            <th>Nombre</th>
            <td><c:out value="${user.name}"/></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><c:out value="${user.email}"/></td>
        </tr>
        <tr>
            <th>Fecha de nacimiento</th>
            <td><c:out value="${user.birthdate}"/></td>
        </tr>
    </table>

    <c:choose>
        <c:when test="${editOwn==false}">
            <spring:url value="{username}/edit" var="editUrl">
                <spring:param name="username" value="${user.username}"/>
            </spring:url>
            <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar usuario</a>
        </c:when>
        <c:otherwise>
            <spring:url value="editprofile" var="editUrl">
            </spring:url>
            <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar perfil</a>
        </c:otherwise>
    </c:choose>
        <c:choose>
            <c:when test="${user.canBeDeleted()==true}">
                <a href="/deleteprofile" class="btn btn-default"> Borrar usuario</a>

            </c:when>
            <c:otherwise>
                Este usuario no puede eliminado porque está en una partida
            </c:otherwise>
        </c:choose>

</petclinic:layout>
