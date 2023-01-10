<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <style>
        .special-box{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 60%;
    height: 10%;
    background-color: rgba(16, 64, 112, 0.814);
    color: aliceblue;
    margin-bottom: 1vmax;
    font-size: 1.2vmax;
    border-color: black;
    border-radius: 1vmax;
    border-style: solid;
    border-width: 0.3vmax;
}
    </style>
    <h2>Users</h2>
    <link rel="stylesheet" href="/resources/styles/tyrants.css">
    <head>
            
    </head>
    <body>
        <c:if test="${text!=null}">
                                <div class="special-box" style="margin-top: 1vmax;" >
                                    <div style="text-align: center;"><o:out><b>${text}</b></o:out> </div>
                                </div>
            </c:if>
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
                <c:if test="${user.canBeDeleted()==true}">
                        <a href="/users/${user.username}/delete"  class="btn btn-default">Delete user</a>
                </c:if>
                <c:if test="${user.canBeDeleted()==false}">
                        <c:out value="Este jugador todavia esta jugando"/>
                </c:if>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-default" href='<spring:url value="/users/find" htmlEscape="true"/>'>Find a user</a>
    
</body>
</petclinic:layout>

