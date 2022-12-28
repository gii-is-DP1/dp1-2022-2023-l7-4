<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <link rel="stylesheet" href="/resources/styles/tyrants.css">
    <h2>User Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Username</th>
            <td><strong><c:out value="${user.username}"/></strong></td>
        </tr>
        <tr>
            <th>Password</th>
            <td><c:out value="${user.password}"/></td>
        </tr>
        <tr>
            <th>Name</th>
            <td><c:out value="${user.name}"/></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><c:out value="${user.email}"/></td>
        </tr>
        <tr>
            <th>BirthDate</th>
            <td><c:out value="${user.birthdate}"/></td>
        </tr>
    </table>

    <spring:url value="{username}/edit" var="editUrl">
        <spring:param name="username" value="${user.username}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit user</a>

</petclinic:layout>
