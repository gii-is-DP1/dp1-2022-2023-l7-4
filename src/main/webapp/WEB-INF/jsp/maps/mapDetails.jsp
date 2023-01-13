<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<link rel="stylesheet" href="/resources/styles/cardlisting.css">
<link rel="stylesheet" href="/resources/styles/tyrants.css">
<link rel="stylesheet" href="/resources/styles/mapUI.css">
<script src="https://d3js.org/d3.v7.min.js" charset="utf-8"></script>
<script src="/js/map.js"></script>
<petclinic:layout pageName="Mapa">
    <style>
        .titles {
            background-color: rgb(30, 6, 41);
            width: fit-content;
            color: aliceblue;
            font-size: 1vmax;
            padding: 1vmax;
            margin-bottom: 0.5vmax;
        }

        .map-details {
            width: 100%;
            display: flex;
            align-items: center;
            flex-direction: column;
            height: 100%;
            overflow-y: scroll;
            padding: 20px;


        }

        .map-box {
            width: 100%;
            display: flex;
            justify-content: center;
            height: 100%;
            background-color: rgb(82, 2, 109);
        }

        .map-page-container {
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
            width: 90%;
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
            color: rgb(54, 0, 104);

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
        .col-sm-10{
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .xd-container {
            width: 100%;
            height: 90%;
            display: flex;
            margin: 0;
            margin-top: 1%;
            padding: 0;
        }
        .custom-form{
            width: 90%;
            background-color: rgba(28, 28, 28, 0.716);
            display: flex;
            color: #ffffff;
            flex-direction: column;
            padding: 10%;
        }
        .custom-form .number-field{
            size: 10%;
        }
    </style>

        <!doctype html>
        <html>

        <body>

            <div class="map-page-container">
                <div class="map-details" id="hideable">
                    <script>
                        let display = "${editable}"=="false"?"none":""
                        console.log("${editable}"=="false"?"none":"")
                        document.getElementById("hideable").style.display = display;
                    </script>
                     <c:if test="${map.name=='' || map.description==''}">
                    <form:form modelAttribute="map"  class="form-horizontal custom-form"
                        method="post" action="/maps/${map.id}">
                        <label>Nombre y Descripcion del MAPA</label>
                        <motero2k:inputField label="id" name="id" hidden="hidden" />
                        <motero2k:inputField label="Nombre" name="name" />
                        <motero2k:inputField label="Descripcion" name="description" />
                        <div style="color: rgba(205, 248, 255, 0.93); width: 100%; text-align: center; font-size: 1.2vmax; margin-bottom: 10px; text-rendering: optimizeSpeed;"><b>Nombre y descripcion no pueden estar vacios</b></div>
                        <button class="btn btn-default" type="submit">Crear mapa</button>
                    </form:form>
                </c:if>
                    <c:if test="${map.name!='' && map.description!=''}">
                    <form:form modelAttribute="cityTemplate"  class="form-horizontal custom-form"
                        method="post" action="/maps/${map.id}/addCity">
                        <label>Nueva CIUDAD</label>
                        <motero2k:inputField label="Nombre" name="name" />
                        <motero2k:inputField label="Capacidad" name="capacity" />
                        <motero2k:inputField label="Tropas enemigas" name="unaligned" />
                        <motero2k:inputField label="VP" name="vpEndgameValue" />
                        <motero2k:inputField label="VP marcador" name="markerVp" />
                        <motero2k:checkbox label="Ciudad Inicial" name="startingCity" />
                        <motero2k:inputField label="Influencia Marcador" name="markerInfluence" />
                        <button class="btn btn-default" type="submit">Confirmar</button>
                    </form:form>
                    <form:form modelAttribute="pathTemplate"  class="form-horizontal custom-form"
                    method="post" action="/maps/${map.id}/addPath">
                    <label>Nuevo Camino</label>
                    <motero2k:inputField label="Capacidad" name="capacity" />
                    <motero2k:selectField label="Primera Ciudad" name="firstCityTemplate" options="${map.cityTemplates}" size="3"/>
                    <motero2k:selectField label="Segunda Ciudad" name="secondCityTemplate" options="${map.cityTemplates}" size="3"/>

                    <button class="btn btn-default" type="submit">Confirmar</button>
                </form:form>
                    <h2>CIUDADES</h2>
                    <table class="styled-table">

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
                                        <c:out value="${city.zone}" />
                                    </td>
                                    <td>
                                        <c:out value="${city.name}" />
                                    </td>
                                    <td>
                                        <c:out value="${city.capacity}" />
                                    </td>
                                    <td>
                                        <c:out value="${city.vpEndgameValue}" />
                                    </td>
                                    <td>
                                        <c:out value="${city.isStartingCity()?'Si':'No'}" />
                                    </td>
                                    <td>
                                        <c:out value="${city.markerVp}" />
                                    </td>
                                    <td>
                                        <c:out value="${city.markerInfluence}" />
                                    </td>
                                    <td>
                                        <a href="/maps/${map.id}/deleteCity/${city.id}"
                                            class="btn btn-default">Borrar</a>
                                    </td>


                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <h2>CAMINOS</h2>
                    <table class="styled-table">
                        <thead>
                            <tr>

                                <th>Capacidad</th>
                                <th>Ciudad 1</th>
                                <th>Ciudad 2</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${map.pathTemplates}" var="path">
                                <tr>
                                    <td>
                                        <c:out value="${path.capacity}" />
                                    </td>
                                    <td>
                                        <c:out value="${path.firstCityTemplate}" />
                                    </td>
                                    <td>
                                        <c:out value="${path.secondCityTemplate}" />
                                    </td>

                                    <td>
                                        <a href="/maps/${map.id}/deletePath/${path.id}"
                                            class="btn btn-default">Borrar</a>
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
                </c:if>
            </div>

        </body>

        </html>
    </petclinic:layout>