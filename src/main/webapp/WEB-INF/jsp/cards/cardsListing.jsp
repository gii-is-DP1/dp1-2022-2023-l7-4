<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <link rel="stylesheet" href="/resources/styles/tyrants.css">
<petclinic:layout pageName="cards">
    <div class="contenedor">
        <div class="contenedor-menu">
            <h2 class="textmenu">CARTAS Y MAZOS &nbsp|&nbsp CARTAS</h2>
        </div>
    </div>

    <form action="/cards/filter">
        <div class="filter-bigbox">
            <div class="filter-box">
                <div class="filter-method">
                    <div class="filter-tittle">
                        <b>FILTRAR CARTAS</b>
                    </div>
                    <br>
                    <div class="nameanddeck-filter-box">
                        <div class="filter-cardanddeck-text">
                            <label for="lname">Nombre de la carta:&nbsp&nbsp</label>
                        </div>
                        <input style="width:135px; height: 24px;" type="text" id="fname" name="name">
                    </div>
                    <br>

                    <div class="nameanddeck-filter-box">
                        <div class="filter-cardanddeck-text">
                            <b>Selecciona un mazo:&nbsp&nbsp</b>
                        </div>
                        <div>
                            <select name="deck" style="width: 100%;">
                                <option value="" label="">Escoge un mazo</option>
                                <c:forEach var="halfDeck" items="${halfDecks}">
                                    <option value="${halfDeck.name}">${halfDeck.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <br/>

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
                                <a href="http://localhost:8080/cards/filter?name=&deck=${param.deck}&page=1" class="x-button2"><b>x</b></a>
                            </div>
                            <div class="filtered-box">
                                Mazo: ${param.deck} 
                                <a href="http://localhost:8080/cards/filter?name=${param.name}&deck=&page=1" class="x-button2"><b>x</b></a>
                            </div>
                            <div class="filtered-box">
                                <a href="http://localhost:8080/cards/filter?name=&deck=&page=1" class="x-button"><b>x</b></a>
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
                ¡No se han encontrado cartas! :(
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
            link="/cards/filter?name=${param.name}&deck=${param.deck}&page=" >
        </motero2k:pageNav>
    </div>


    <br>
    <div class="parent">
    <c:forEach var="card" items="${cards}">
        <div class="child">
            <div class="principalCard">
                <spring:url value="/cards/{cardId}" var="cardUrl">
                    <spring:param name="cardId" value="${card.id}" />
                </spring:url>
                <a href="${fn:escapeXml(cardUrl)}">
                    <img src="/resources/images/cardsModel.png" class="cardImage">
                    <div class="topTextName">
                        <b>
                            <c:out value="${card.name}" />
                        </b>
                    </div>
                    <div class="topTextCost">
                        <b>
                            <c:out value="${card.cost} " />
                        </b>
                    </div>
                    <div class="topTextAspect">
                        <b>
                            <c:out value="${card.aspect.name} " />
                        </b>
                    </div>
                    <div class="topTextHalfDeck">
                        <b>
                            <c:out value="${card.halfDeck.name} " />
                        </b>
                    </div>
                    <div class="topTextRulesText">
                        <b>
                            <c:out value="${card.rulesText} " />
                        </b>
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
                        <b>
                            <c:out value="${card.deckVP} " />
                        </b>
                    </div>
                    <div class="topTextInnerCirclePV">
                        <b>
                            <c:out value="${card.innerCirclePV} " />
                        </b>
                    </div>
                </a>
            </div>
        </div>
    </c:forEach>
    </div>


    <div class="pagination-box">
        <motero2k:pageNav 
        currentPage="${currentPage}" 
        link="/cards/filter?name=${param.name}&deck=${param.deck}&page=" >
        </motero2k:pageNav>
        <div style="color: aliceblue;"><b>Te
            encuentras en la página: ${currentPage} / ${numberOfPages}
        </b></div>
    </div>

</body>
</petclinic:layout>
</html>




    <style>
        .parent {
            display: flex;
            flex-wrap: wrap;
        }

        .child {
            display: flex;
            align-items: center;
            justify-content: center;
            flex: 1 0 33%;
            /* explanation below */
            margin: 0px;
        }


        .centerBlock {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        /*CARTA*/
        .principalCard {
            /* width: 27%;
            height: 52%; */
            /* width: 23*0.7vw;
            height: 23vw; */
            /* margin-bottom: 2%; */
            /* margin-left: 5%; */
            height: 95%;
            width: 85%;
            position: relative;
            /* display: inline-block; */

        }

        .cardImage {
            width: 100%;
            height: 100%;
        }

        .topTextName {
            position: absolute;
            top: 3%;
            /* font-size: 127%; */
            font-size: 1.25vmax;
            margin-left: 8%;
            color: aliceblue;
            font-family: "Critter";
        }

        .topTextCost {
            position: absolute;
            top: 3%;
            /* font-size: 193%; */
            font-size: 1.5vmax;
            margin-left: 86%;
            color: aliceblue;
            font-family: "Critter";
        }

        .topTextAspect {
            position: absolute;
            top: 10%;
            /* font-size: 103%; */
            font-size: 1.1vmax;
            margin-left: 8%;
            color: aliceblue;
            font-family: "Critter";
        }

        .topTextHalfDeck {
            position: absolute;
            top: 10%;
            /* font-size: 103%; */
            font-size: 1.1vmax;
            width: 100%;
            text-align: right;
            right: 7%;
            color: aliceblue;
            font-family: "Critter";
        }

        .topTextRulesText {
            position: absolute;
            width: 83%;
            top: 55%;
            left: 8.5%;
            /* font-size: 90%; */
            font-size: 0.8vmax;
            text-align: justify;
            color: black;
            font-family: "Critter";
        }

        .topTextRarity {
            position: absolute;
            top: 90%;
            width: 100%;
            /* font-size: 110%; */
            font-size: 1vmax;
            color: aliceblue;
            font-family: "Critter";
            text-align: center;
        }

        .topTextDeckVP {
            position: absolute;
            top: 84.5%;
            font-size: 160%;
            font-size: 1.5vmax;
            margin-left: 66.5%;
            color: aliceblue;
            font-family: "Critter";
        }

        .topTextInnerCirclePV {
            position: absolute;
            top: 84.4%;
            font-size: 160%;
            font-size: 1.5vmax;
            margin-left: 81.8%;
            color: aliceblue;
            font-family: "Critter";
        }
    </style>

    <style>
        .textmenu {
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

        .pagination-box{
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .card-not-found{
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .filter-bigbox{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .filter-box{
            width: 60%;
            height: 100%;
            border-radius: 5vmax;
            border-color: darkgoldenrod;
            border-style: double;
            border-width: 0.4vmax;
            background-image: url(/resources/images/round0-background.jpg);
            background-position: center;
            background-size:cover;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .filter-method{
            display: flex;
            width: 69%;
            height: 100%;
            flex-direction: column;
            padding: 5%;
        }
        .filter-search{
            display: flex;
            height: 100%;
            width: 30%;
            align-items: flex-end;
        }
        .nameanddeck-filter-box{
            display: flex;
            flex-direction: row;
        }
        .filter-tittle{
            font-size: 1.7vmax;
            color: aliceblue;
        }
        .filter-cardanddeck-text{
            font-size: 1.3vmax;
            color: aliceblue;
        }
        .filter-resume-box{
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            justify-content: space-between;
            align-items: center;
        }
        .filtered-box{
            font-size: 1vmax;
            display: flex;
            align-items: center;
            background-color: rgb(168, 193, 214);
            border-color: #2c243d;
            border-style: solid;
            border-width: 0.18vmax;
            padding: 1.5%;
            border-radius: 0.5vmax;
        }
        .x-button{
            color: #8e385e;
        }
        .x-button2{
            color: #8e385e;
            margin-left: 0.3vmax;
        }
        .filtered-in-box{
            width: 58%;
            display: flex;
            flex-direction: row;
            justify-content: space-around;
            flex-wrap: wrap;
        }
    </style>

    <style>
            .btn {
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