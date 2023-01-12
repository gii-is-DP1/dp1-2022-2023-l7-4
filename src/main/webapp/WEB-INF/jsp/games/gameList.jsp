<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="/resources/styles/cardlisting.css">
<link rel="stylesheet" href="/resources/styles/tyrants.css">

<petclinic:layout pageName="game">
    
    <form action="/games">
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
                    <c:if test="${param.name!='' || param.state!=''}">
                        <div class="filter-resume-box">
                            <div style="width: 41%">
                                <div class="filter-cardanddeck-text">
                                    <b>Filtro aplicado →</b>
                                </div>
                            </div>
                            <div class="filtered-in-box">
                                <c:if test="${param.name!=''}">
                                    <div class="filtered-box">
                                        Nombre de partida: ${param.name}&nbsp 
                                        <a href="http://localhost:8080/games?name=&state=${param.state}" class="x-button"><b>x</b></a>
    
                                    </div>
                                </c:if>
                                
                                <c:if test="${param.state!=''}">
                                    <div class="filtered-box">
                                        Estado: ${param.state}&nbsp 
                                        <a href="http://localhost:8080/games?name=${param.name}&state=" class="x-button"><b>x</b></a>
    
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:if>
                    
        
        
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
    <c:choose>
        <c:when test="${search==true}">
            <div style="display: flex; justify-content: center; width: 100%;">
                <a class="new-game-button" href='/games/create'><b>Crear una partida</b></a>
            </div>
            <div class="card-not-found" style="color: aliceblue; margin-top: 3vmax;">
                <h1>
                    ¡No se han encontrado partidas! :(
                </h1>
            </div>
        </c:when>
        <c:otherwise>
            <div style="display: flex; justify-content: center; width: 100%;">
                <a class="new-game-button" href='/games/create'><b>Crear una partida</b></a>
            </div>
            <div style="display: flex; width: 100%;">
                <h2>${text}</h2>
            </div>
            <table id="gameTable" class="table table-striped">
                <thead>
                <tr>
                    <th style="width: 150px;">Nombre</th>
                    <th style="width: 200px;">Número de jugadores</th>
                    <th style="width: 100px;"></th>
                    <c:choose>
                        <c:when test="${result==true}">
                            <th style="width: 100px;">Creador</th>
                            <th style="width: 100px;">Fecha de creación</th>
                            <th style="width: 100px;">Fecha exacta de última modificación</th>
                            <th style="width: 100px;">Último modificador</th>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${selections}" var="game">
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
                        <c:choose>
                        <c:when test="${result==true}">
                            <td>
                                <c:out value="${game.creator}"/>
                            </td>
                            <td>
                                <c:out value="${game.date}"/>
                            </td>
                            <td>
                                <c:out value="${game.lastModifiedDate}"/>
                            </td>
                            <td>
                                <c:out value="${game.modifier}"/>
                            </td>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
        
                        
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    
</petclinic:layout>
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
    .new-game-button{
    display:flex;
    align-items: center;
    justify-content: center;
    height: 3vmax;
    width: 18vmax;
    font-size: 1.5vmax;
    color: #2b0a2f;
    border-radius: 0.5vmax;
    border: 0.2vmax solid #2b0a2f; 
    background: #e9c3f1;
    background: linear-gradient(0deg, #d592ff 0%, #c382ec 50%, #a65cd4 100%);
    transition-duration: 0.5s;
}
.new-game-button:hover{
    display:flex;
    align-items: center;
    justify-content: center;
    height: 3vmax;
    width: 18vmax;
    font-size: 1.5vmax;
    color: #d5b2dc;
    border-radius: 0.5vmax;
    border: 0.2vmax solid #1b071f; 
    background: #b348c9;
    background: linear-gradient(0deg, #b348c9 0%, #732f81 50%, #3f1049 100%);
    transition-duration: 0.5s;
}
.btnx {
    box-sizing: border-box;
    -webkit-appearance: none;
        -moz-appearance: none;
            appearance: none;
    background-color: transparent;
    border: 2px solid #e74c3c;
    border-radius: 0.6em;
    color: #e74c3c;
    cursor: pointer;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;

    font-size: 1rem;
    font-weight: 400;
    line-height: 1;
    margin: 20px;
    padding: 1.2em 2.8em;
    text-decoration: none;
    text-align: center;
    text-transform: uppercase;
    font-weight: 700;
    }
.btn:hover, .btn:focus {
    color: rgb(0, 0, 0);
    outline: 0;
    }

.third {
width: 80%;
border-color: #1f323e;
justify-content: center;
color: #fff;
box-shadow: 0 0 40px 40px #9f5967 inset, 0 0 0 0 #9f5967;
-webkit-transition: all 150ms ease-in-out;
transition: all 150ms ease-in-out;
}
.third:hover {
box-shadow: 0 0 10px 0 #9f5967 inset, 0 0 10px 4px #9f5967;
}
</style>