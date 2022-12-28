<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>Users</h2>
    <link rel="stylesheet" href="/resources/styles/tyrants.css">

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Username</th>
            <th style="width: 200px;">Password</th>
            <th>Name</th>
            <th>Email</th>
            <th>BirthDate</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="user">
            <tr>
                <td>
                    <spring:url value="/users/{username}" var="userUrl">
                        <spring:param name="username" value="${user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(userUrl)}"><c:out value="${user.username}"/></a>
                </td>
                <td>
                    <c:out value="${user.password}"/>
                </td>
                <td>
                    <c:out value="${user.name}"/>
                </td>
                <td> 
                    <c:out value="${user.email}"/> 
                </td>
                <td> 
                   <c:out value="${user.birthdate}"/> 
                </td>
                <td>
                    <a href="/users/${user.username}/delete"  class="btn btn-default">Delete user</a>
                </td>

                
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-default" href='<spring:url value="/users/find" htmlEscape="true"/>'>Find a user</a>
</petclinic:layout>

