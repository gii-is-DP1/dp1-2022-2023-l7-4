<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>

<petclinic:layout pageName="users">
    <form action="/users/pagination">
        <div class="filter-bigbox">
            <div class="filter-box">
                <div class="filter-method">
                    <div class="filter-tittle">
                        <b>FILTRAR USUARIOS</b>
                    </div>
                    <br>
                    <div class="name-filter-box">
                        <div class="filter-cardanddeck-text">
                            <label for="lname">Nombre de usuario:&nbsp&nbsp</label>
                        </div>
                        <input style="width:135px; height: 24px;" type="text" id="fname" name="name">
                    </div>

                    <input type="text" name="page" value="1" hidden>
        
                    <div class="filter-resume-box">
                        <div style="width: 41%">
                            <div class="filter-cardanddeck-text">
                                <b>Filtro aplicado →</b>
                            </div>
                        </div>
                        <div class="filtered-in-box">
                            <div class="filtered-box">
                                Nombre: ${param.name} 
                            </div>
                            <div class="filtered-box">
                                <a href="http://localhost:8080/users/pagination?name=&page=1" class="x-button"><b>x</b></a>
                            </div>
                        </div>
                    </div>
        
        
                </div>
                <div class="filter-search">
                    <div style="width: 80%; height: 80%;">
                        <button type="submit" class="btn third">
                            <div style="font-size: 0.8vmax"><b>APLICAR</b></div>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <br>

    <c:if test="${notFound}">
        <div class="card-not-found" style="color: aliceblue;">
            <h1>
                ¡No se han encontrado usuarios! :(
            </h1>
        </div>
        <br>
    </c:if>


    <div class="pagination-box">
        <div style="color: aliceblue;"><b>Te
            encuentras en la página: ${currentPage} / ${numberOfPages}
        </b></div>
        <motero2k:pageNav 
            currentPage="${currentPage}" 
            link="/users/pagination?name=${param.name}&page=" >
        </motero2k:pageNav>
    </div>
    <br>
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
    <h2>Usuarios</h2>
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
            <th style="width: 150px;">Nombre de usuario</th>
            <th style="width: 200px;">Contraseña</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Fecha de nacimiento</th>
            <th>Eliminar</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
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
                        <a href="/users/${user.username}/delete"  class="btn btn-default">Eliminar usuario</a>
                </c:if>
                <c:if test="${user.canBeDeleted()==false}">
                        <c:out value="Este jugador todavia esta jugando"/>
                </c:if>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination-box">
        <motero2k:pageNav 
        currentPage="${currentPage}" 
        link="/users/pagination?name=${param.name}&page=" >
        </motero2k:pageNav>
        <div style="color: aliceblue;"><b>Te
            encuentras en la página: ${currentPage} / ${numberOfPages}
        </b></div>
    </div>
    
</body>
</petclinic:layout>

