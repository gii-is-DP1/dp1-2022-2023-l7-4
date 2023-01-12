<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<link rel="stylesheet" href="/resources/styles/cardlisting.css">
<link rel="stylesheet" href="/resources/styles/tyrants.css">
<script src="https://d3js.org/d3.v7.min.js" charset="utf-8"></script>
<script src="/js/map.js"></script>
<style>
    .titles{
        background-color: rgb(30, 6, 41);
        width: fit-content;
        color: aliceblue;
        font-size: 1vmax;
        padding: 1vmax;
        margin-bottom: 0.5vmax;
    }
    .map-details{
            width: 50%;
            display: flex;
            justify-content: center;
            flex-direction: column;
            height: 100%;
            overflow-y: scroll;
            padding: 20px;
            padding-top: 20%;
    }
    .map-box{
            width: 50%;
            display: flex;
            justify-content: center;
            height: 100%;
            background-color: rgb(82, 2, 109);
    }
    .map-page-container{
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 100%;
        margin: 0;
    }
    .styled-table {
    border-collapse: collapse;
    margin: 25px 0;
    font-size: 0.9em;
    font-family: sans-serif;
    min-width: 400px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}
.styled-table thead tr {
    background-color: #540098a6;
    color: #ffffff;
    text-align: left;
}
.styled-table th,
.styled-table td {
    padding: 3px 7px;
    color:rgb(54, 0, 104);
    
}
.styled-table tbody tr {
    border-bottom: 1px solid #ffffff;
    background-color: #ffffff
}
.styled-table td {

    justify-content: center;
    border-bottom: 1px solid #ffffff;
    background-color: #ffffff
}


</style>
<petclinic:layout pageName="Mapa">
    <style>

        .xd-container{
            width: 100%;
            height: 90%;
            display: flex;
            margin:0;
            margin-top: 5%;
            padding: 0;
        }
    </style>

    <!doctype html>
    <html>
<body >
    
    
    <div class="map-page-container">
        <div class="map-details">

            <form:form modelAttribute="cityTemplate" class="form-horizontal" method="post" action="/maps/${map.id}/addCity">

                <petclinic:inputField  label="Nombre" name="name"/>
                <petclinic:inputField  label="Capacidad" name="capacity"/>
                <petclinic:inputField  label="VP" name="vpEndgameValue"/>
                <petclinic:inputField  label="M VP" name="markerVp"/>
                <motero2k:checkbox label="Ciudad Inicial" name="startingCity" />
                <petclinic:inputField  label="Inf" name="markerInfluence"/>
                <button class="btn btn-default" type="submit">Confirmar</button>
        </form:form>
        <table id="mapsTable" class="styled-table">
                <thead>
                    <tr>
                        
                        <th>Zona</th>
                    <th>Nombre</th>
                    <th>Capacidad</th>
                    <th>VP</th>
                    <th>Inicial</th>
                    <th>M-VP</th>
                    <th>M-Inf</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${map.cityTemplates}" var="city">
                    <tr>
                        <td> 
                            <c:out value="${city.zone}"/>
                        </td>
                        <td>
                            <c:out value="${city.name}"/>
                        </td>
                        <td> 
                            <c:out value="${city.capacity}"/>
                        </td>
                        <td> 
                            <c:out value="${city.vpEndgameValue}"/>
                        </td>
                        <td> 
                            <c:out value="${city.isStartingCity()?'Si':'No'}"/>
                        </td>
                        <td> 
                            <c:out value="${city.markerVp}"/>
                        </td>
                        <td> 
                            <c:out value="${city.markerInfluence}"/>
                        </td>
                        <td>
                            <a href="${map.id}/deleteCity/${city.id}"  class="btn btn-default">Borrar</a>
                        </td>
                        
                        
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="map-box">
            <svg id="graph" style="width: 100%; height: 100%;"></svg>
        </div>
        <script>
            //INICIA EL MAPA
            init('${game.id}')
            </script>
        
    </div>
    
</body>
</html>
</petclinic:layout>