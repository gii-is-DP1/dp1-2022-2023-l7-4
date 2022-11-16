<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<petclinic:layout pageName="cards">
<div class="contenedor"><div class="contenedor-menu"> <h2 class="textmenu">CARTAS Y MAZOS &nbsp|&nbsp CARTAS</h2> </div></div>   

    <script>
        console.log("ssss")
    </script>
<div class="filt-container">
    <div style="font-size: 25px; color: aliceblue; text-align: center;"><b>FILTRAR CARTAS</b></div>
    <br>

    <form action="/cards/filter">
        <label for="lname" style="color: aliceblue; margin-left:23px;font-size: 17px;">Nombre de la carta:&nbsp&nbsp</label>
        <input type="text" id="fname" name="name" value=${param.name}><br>
        <div style="color: aliceblue; margin-left:23px;font-size: 17px;"><b>Selecciona un mazo:</b></div>
        <div style="padding-bottom: 20px;">
            <select name="deck" style="margin-left:23px">
                <option value="${param.deck}" label="${param.deck}">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspEscoge un mazo&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</option>
                <c:forEach var="halfDeck" items="${halfDecks}">
                    <option value="${halfDeck.name}">${halfDeck.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="aBotton">
            <button type="submit" class="btn btn-two"><div class="textbtn"><b>Buscar</b></div></button>
        </div>
    </form> 
</div>

<br><br>
    <table id="positionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Coste</th>
            <th>Historia</th>
            <th>Acción de la carta</th>
            <th>PV</th>
            <th>PV en Círculo interno</th>
            <th>Rareza</th>
            <th>Aspecto</th>
            <th>Mazo</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="card" items="${cards}" >
            <tr>
                <td>
                    <c:out value="${card.id}"/>
                </td>
                <td>
                    <spring:url value="/cards/{cardId}" var="cardUrl">
                        <spring:param name="cardId" value="${card.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(cardUrl)}"><c:out value="${card.name}"/></a>
                </td>
                <td>
                    <c:out value="${card.cost} "/>
                </td>
                <td>
                    <c:out value="${card.story} "/>
                </td>
                <td>
                    <c:out value="${card.rulesText} "/>
                </td>
                <td>
                    <c:out value="${card.deckVP} "/>
                </td>   
                <td>
                    <c:out value="${card.innerCirclePV} "/>
                </td>   
                <td>
                    <c:out value="${card.rarity} "/>
                </td>
                <td>
                    <c:out value="${card.aspect.name} "/>
                </td>
                <td>
                    <c:out value="${card.halfDeck.name} "/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <style>
        .aBotton {
            position: relative;
            left: 65%;
            margin-top: -10%;
        }

        .filt-container {
            width: 60%;
            height: 20%;
            background-color: rgb(106, 86, 127);
            border-radius: 30px;
            padding: 20px;
            margin-left: 20%;
            border-color: rgb(37, 37, 37);
            border-width: 10px;
            border-style: double;
        }

        .textmenu{
            color: aliceblue;
            margin-top: 3px;
        }

        .contenedor {
            background-color: rgb(25, 24, 24);
            margin-left: -13.9%;
            margin-right: -13.88%;
            margin-top: -40px;
            margin-bottom: 2%;
        }

        .contenedor-menu {
            display: flex;
            justify-content: space-around;
            height: 30px;
            margin: 0 auto;
        }

        .textbtn {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            opacity: 100%;
            margin-top: 1px;
        }

        .btn {
            opacity: 90%;
            line-height: 50px;
            height: 40px;
            text-align: center;
            width: 200px;
            cursor: pointer;
        }

        /*Botton*/
        .btn-two {
            color: rgb(197, 172, 63);
            transition: all 0.5s;
            position: relative;	
            background-color: #282121;
            font-size: 20px;
            border-radius: 10%;
        }
        .btn-two span {
            z-index: 2;	
            display: block;
            position: absolute;
            width: 100%;
            height: 100%;
            border-radius: 10%;
            
        }
        .btn-two::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 1;
            transition: all 0.5s;
            border: 1px solid rgba(255, 255, 255, 0.086);
            background-color: rgba(255, 255, 255, 0);
            border-radius: 10%;

        }
        .btn-two::after {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 1;
            transition: all 0.5s;
            border: 1px solid rgba(202, 189, 189, 0.123);
            background-color: rgba(200, 177, 177, 0.122);
            border-radius: 10%;

        }
        .btn-two:hover::before {
        transform: rotate(-45deg);
        background-color: rgba(255, 255, 255, 0.105);
        border-radius: 10%;

        }
        .btn-two:hover::after {
        transform: rotate(45deg);
        background-color: rgba(255, 255, 255, 0.105);
        border-radius: 10%;

        }
    </style>
</petclinic:layout>