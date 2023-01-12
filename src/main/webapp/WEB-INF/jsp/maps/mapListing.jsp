<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<link rel="stylesheet" href="/resources/styles/cardlisting.css">
<link rel="stylesheet" href="/resources/styles/tyrants.css">
<style>
    .titles{
        background-color: rgb(30, 6, 41);
        width: fit-content;
        color: aliceblue;
        font-size: 1vmax;
        padding: 1vmax;
        margin-bottom: 0.5vmax;
    }
</style>
<petclinic:layout pageName="maps">

    <p class="titles">CREAR MAPA</p>
    <a href="/maps/new"  class="btn btn-default">NUEVO MAPA</a>
    <br><br>
    <p class="titles">TODOS LOS MAPAS</p>
    
    <table id="mapsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Descipción</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${maps}" var="map">
            <tr>
                <td>
                    <c:out value="${map.name}"/>
                </td>
                <td> 
                        ${map.description}
                </td>

                <td>
                    <a href="/maps/${map.id}"  class="btn btn-default">Ver</a>
                </td>

                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>