<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<petclinic:layout pageName="games">
    <link rel="stylesheet" href="/resources/styles/tyrants.css">

    <h2>Buscar partida</h2>

    <form action="/games/find">
        <div class="filter-bigbox">
            <div class="filter-box">
                <div class="filter-method">
                    <div class="filter-tittle">
                        <b>BUSCAR PARTIDAS</b>
                    </div>
                    <br>
                    <div class="name-filter-box">
                        <div class="filter-cardanddeck-text">
                            <label for="lname">Nombre de la partida:&nbsp&nbsp</label>
                            <input style="width:135px; height: 24px; font-size: 0.9vmax;" type="text" id="fname" name="name">
                        </div>
                    </div>
                    
                    <c:if test="${param.name!=''}">
                        <div class="filter-resume-box">
                            <div style="width: 41%">
                                <div class="filter-cardanddeck-text">
                                    <b>Filtro aplicado →</b>
                                </div>
                            </div>
                            <div class="filtered-in-box">
                                
                                <div class="filtered-box">
                                    Nombre de partida: ${param.name}&nbsp 

                                </div>
                            </div>
                        </div>
                    </c:if>
                    <div class="nameanddeck-filter-box">
                        <div class="filter-cardanddeck-text">
                            <b>Escoge un estado:&nbsp&nbsp</b>
                        </div>
                        <div>
                            <select name="state" style="width: 100%;">
                                <option value="" label="">Escoge un estado</option>
                                <c:forEach var="isFinish" items="${states}">
                                    <option value="${isFinish}">${isFinish}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
        
        
                </div>
                <div class="filter-search">
                    <div style="width: 80%; height: 80%;">
                        <button type="submit" class="btnx third">
                            <div style="font-size: 0.8vmax"><b>APLICAR</b></div>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <br/> 
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/games/create" htmlEscape="true"/>'>Crear juego</a>
	</sec:authorize>

            <body>
                <c:choose>
                    <c:when test="${search==true}">
                        <div class="card-not-found" style="color: aliceblue;">
                            <h1>
                                ¡No se han encontrado partidas con el nombre buscado! :(
                            </h1>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table id="gameTable" class="table table-striped">
                            <thead>
                            <tr>
                                <th style="width: 150px;">Nombre</th>
                                <th style="width: 200px;">Número de jugadores</th>
                                <th style="width: 100px;"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="game" items="${games}" >
                                <tr>
                                    <td>
                                        <spring:url value="/games/{gameId}" var="gameUrl">
                                            <spring:param name="gameId" value="${game.id}"/>
                                        </spring:url>
                                        <a href="${fn:escapeXml(gameUrl)}"><c:out value="${game.name}"/></a>
                                    </td>
                                    <td>
                                        <c:out value="${game.size}"/>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${game.finished=='FALSE'}">
                                            <div id="greenball"></div>
                                            <br />
                                            </c:when>    
                                             <c:otherwise>
                                                 <div id="redball"></div>
                                                <br />
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    
                    
                                    
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </body>
                    </c:otherwise>
                </c:choose>


    
<style>
    #redball{
        background-color: red;
        width: 10px;
        height: 10px;
        box-shadow: 0px 0px 10px darkred;
        border-radius: 50%;
    }
    #greenball{
        background-color: green;
        width: 10px;
        height: 10px;
        box-shadow: 0px 0px 10px darkgreen;
        border-radius: 50%;
    }
</style>
	
</petclinic:layout>
