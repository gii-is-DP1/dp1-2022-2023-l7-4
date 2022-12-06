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


<div class="filt-container">
    <div style="font-size: 25px; color: aliceblue; text-align: center;"><b>FILTRAR CARTAS</b></div>
    <br>

    <form action="/cards/filter?page=1">
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
        <input type="text" name="page" value="1" hidden>
        <div class="aBotton">
            <button type="submit" class="special-btn btn-two"><div class="textbtn"><b>Buscar</b></div></button>
        </div>
    </form> 
    <br>
    <div style="color: aliceblue; margin-left:23px;font-size: 17px;"><b>Te encuentras en la página: ${param.page}</b></div>

    <c:forEach var="nextPage" items="${pages}">
        <a href='<c:url value="/cards/filter?name=${param.name}&deck=${param.deck}&page=${nextPage}"/>' >
                <div class="page-button">
                    <div><div>${nextPage}</div></div>
                    </div>
			</a><br>
    </c:forEach>

</div>

<br><br>

    <c:forEach var="card" items="${cards}" >
        <div class="principalCard">
            <spring:url value="/cards/{cardId}" var="cardUrl">
                <spring:param name="cardId" value="${card.id}" />
            </spring:url>
            <a href="${fn:escapeXml(cardUrl)}">
                <img src="/resources/images/cardsModel.png" class="cardImage">
                <div class="topTextName">
                    <b><c:out value="${card.name}" /></b>
                </div>
                <div class="topTextCost">
                    <b><c:out value="${card.cost} " /></b>
                </div>
                <div class="topTextAspect">
                    <b><c:out value="${card.aspect.name} " /></b>
                </div>
                <div class="topTextHalfDeck">
                    <b><c:out value="${card.halfDeck.name} " /></b>
                </div>
                <div class="topTextRulesText">
                    <b><c:out value="${card.rulesText} " /></b>
                </div>
                <div class="topTextRarity">
                    <b>
                        <text id="t${card.id}" style="font-size: 180%;">
                            <script>
                                var rarity = " &#8226 ".repeat(parseInt("${card.rarity}"))
                                document.getElementById("t${card.id}").innerHTML = rarity
                            </script>
                        </text>
                    </b>
                </div>
                <div class="topTextDeckVP">
                    <b><c:out value="${card.deckVP} " /></b>
                </div>
                <div class="topTextInnerCirclePV">
                    <b><c:out value="${card.innerCirclePV} " /></b>
                </div>
            </a>
        </div>
    </c:forEach>
    </div>

    <style>
        .centerBlock{
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }
        .principalCard{
            width: 27%;
            height: 52%;
            margin-bottom: 2%;
            margin-left: 5%;
            position: relative;
            display: inline-block;
            
        }
        .cardImage {
            width: 100%;
            height: 100%;
        }
        .topTextName{
            position: absolute;
            top: 4%;
            font-size: 127%;
            margin-left: 8%;
            color: aliceblue;
            font-family: "Critter";
        }
        .topTextCost{
            position: absolute;
            top: 3%;
            font-size: 193%;
            margin-left: 86%;
            color: aliceblue;
            font-family: "Critter";
        }
        .topTextAspect{
            position: absolute;
            top: 10%;
            font-size: 103%;
            margin-left: 8%;
            color: aliceblue;
            font-family: "Critter";
        }
        .topTextHalfDeck{
            position: absolute;
            top: 10%;
            font-size: 103%;
            width: 100%;
            text-align: right;
            right: 7%;
            color: aliceblue;
            font-family: "Critter";
        }
        .topTextRulesText{
            position: absolute;
            width: 83%;
            top: 55%;
            left: 8.5%;
            font-size: 90%;
            text-align: justify;
            color: black;
            font-family: "Critter";
        }
        .topTextRarity{
            position: absolute;
            top: 90%;
            width: 100%;
            font-size: 110%;
            color: aliceblue;
            font-family: "Critter";
            text-align: center;
        }
        .topTextDeckVP{
            position: absolute;
            top: 84.5%;
            font-size: 160%;
            margin-left: 66.5%;
            color: aliceblue;
            font-family: "Critter";
        }
        .topTextInnerCirclePV{
            position: absolute;
            top: 84.4%;
            font-size: 160%;
            margin-left: 81.8%;
            color: aliceblue;
            font-family: "Critter";
        }
    </style>

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
            margin-top: -40px;
            margin-bottom: 2%;
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
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

        .special-btn {
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
            border: 2px solid rgba(255, 255, 255, 0.086);
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
            border: 2px solid rgba(202, 189, 189, 0.123);
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

        .page-button{
            background-color: aliceblue;
        }
    </style>
</petclinic:layout>