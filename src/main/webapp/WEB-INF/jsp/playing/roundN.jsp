<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="/resources/styles/tyrants.css">
<link rel="stylesheet" href="/resources/styles/cardlisting.css">

<head>
    <style>
        .popup {
        background-color: rgba(0, 0, 0, 0.563);
        height: 100%;
        width: 100%;
        left: 0;
        visibility: hidden;
        top: 0;        
        position:fixed;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 2vmax;
    }
    .popup-blue-box{
        background-color: rgba(16, 64, 112, 0.814);
        height: 85%;
        width: 80%;
        display: flex;
        border: 3px;
        font-size: 2vmax;
        border-radius: 1vmax;
    }
    .popup .ls{
        margin: auto;
        display: flex;
        flex-direction: column;
        width: 100%;
        align-items: center;
        justify-content: center;
        color: aliceblue;
    }
    .x {
        margin-left: 10px;
        color: rgb(255, 0, 0);
        user-select: none;
        width: 50px;
        height: 50px;
        position: absolute;
        align-self: flex-start;
        font-family: monospace;
        cursor: pointer;
    }
    p{
        margin: 10px;
    
    }
    #icon{
        width: 50px;
        height: 50px;
        color: red;
    
    }
    .popup-content-box{
        width: 100%;
        height: 100%;
        flex-direction: column;
        display: flex;
        align-items: center ;
        justify-content: space-evenly;
        margin: 3px;
    }
    .popup-content {
        height: 100%;
        width: 100%;
        display: flex;
        align-items: center ;
        border: 3px;
        border-radius: 10px;
        font-size: 2vmax;
    }
        .playing-box-roundN{
            width: 96%;
            display: flex;
            align-items: center;
            justify-content: center;
            background-image: url(/resources/images/round0-background.jpg);
            background-position: center;
            background-size:cover;
        }
        .playing-box-roundN .card-action-box{
            width: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            height: 100%;
        }
        .playing-box-roundN .map-box{
            width: 50%;
            display: flex;
            justify-content: center;
            height: 100%;
        }
        .card-action-box .market-box{
            width: 100%;
            height: 52%;
            display: flex;
            align-items: center;
            justify-content: center;
     
        }
        .market-box .market-zone{
            width: 93%;
            height: 92%;
            background-color:rgb(25, 24, 24);
            border-radius: 1.5vmax;
            border-style: solid;
            border-width: 0.32vmax;
            background-image: url(/resources/images/fondo_mercado.png);
            background-position: center;
            background-size:cover;
            background-repeat: no-repeat;
            display: flex;
            align-items: center;
            justify-content: space-evenly;
            flex-direction: column;
        }
        .market-zone .top-market-cards{
            width: 98%;
            height: 46%;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .card-box{
            width: 15%;
            height: 10vmax;
            background-image: url(/resources/images/carta_mecado.png);
            background-position: center;
            background-size:contain;
            background-repeat: no-repeat;
            display: flex;
            align-items: center;
            font-size: 0.8vmax;
            flex-direction: column;
            word-wrap: break-word;
        }
        .reversed-card{
            width: 15%;
            height: 10vmax;
            background-image: url(/resources/images/card_back.png);
            background-position: center;
            background-size:contain;
            background-repeat: no-repeat;
            display: flex;
            justify-content: center;
        }
        .bold-gold-number {
            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
            margin-bottom: 1.5vmax;
            font-size: 2vmax;
            color: goldenrod;
            margin-top: 1vmax;
        }
        .card-box .card-name-box{
            height: 67%;
            width: 100%;
            justify-content: center;
            display: flex;
        }
        .card-name-box .card-name-center {
            margin-top: 1.75vmax;
            width: 85%;
            text-align: center;
            font-size: 1vmax;
        }
        .card-box .card-cost-vp-box{
            height: 32%;
            width: 100%;
            display: flex;
        }
        .card-cost-vp-box .card-cost{
            width: 33%;
            color: aliceblue;
            text-align: center;
            font-size: 1.2vmax;
            margin-left: 0.15vmax;
            margin-top: 0.30vmax;
        }
        .card-cost-vp-box .card-deckVP{
            width: 33%;
            color: aliceblue;
            text-align: center;
            font-size: 1.15vmax;
            margin-left: -0.09vmax;
            margin-top: 0.35vmax;
        }
        .card-cost-vp-box .card-innerCirclePV{
            margin-top: 0.19vmax;
            width: 30%;
            color: aliceblue;
            text-align: center;
            font-size: 1.2vmax;
            margin-top: 0.30vmax;
        }
        .card-hand-box .card-name-center {
            margin-top: 1.8vmax;
            width: 85%;
            text-align: center;
            font-size: 1vmax;
        }
        .card-hand-box{
            width: 15%;
            height: 10vmax;
            background-image: url(/resources/images/card_in_hand.png);
            background-position: center;
            background-size:contain;
            background-repeat: no-repeat;
            display: flex;
            justify-content: center;
            word-wrap: break-word;
        }
        .top-market-cards .empty-card-box{
            width: 15%;
            height: 10vmax;
            display: flex;
            justify-content: center;
        }
        .card-action-box .player-cards-box{
            width: 100%;
            height: 28%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .card-action-box .player-hand-zone-box{
            width: 93%;
            height: 85%;
            background-image: url(/resources/images/fondo_mano.png);
            background-position: 0px -130px;
            background-size:cover;
            background-repeat: no-repeat;
            background-color:rgb(25, 24, 24);
            border-radius: 1.5vmax;
            border-style: solid;
            border-width: 0.32vmax;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .player-hand-zone-box .player-hand-margin-box{
            width: 98%;
            height: 90%;
            display: flex;
            align-items: center;
            justify-content: space-evenly;
        }
        .card-action-box .basics-actions-and-resume-box{
            width: 100%;
            height: 20%;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .card-action-box .resume-box{
            width: 30%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content:space-evenly;
            flex-direction: column;
        }
        .resume-box .resume-decks-box{
            width: 125%;
            height: 20%;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .resume-decks-box .resume-decks-secuaz-image{
            width: 20%;
            height: 133%;
            background-image: url(/resources/images/card_back.png);
            background-position: center;
            background-size:contain;
            background-repeat: no-repeat;
            margin: 0.2vmax;
        }
        .resume-decks-box .resume-decks-hand-image{
            width: 20%;
            height: 133%;
            background-image: url(/resources/images/card_in_hand.png);
            background-position: center;
            background-size:contain;
            background-repeat: no-repeat;
            margin: 0.2vmax;
        }
        .resume-decks-box .resume-decks-text{
            width: 75%;
            height: 100%;
            display: flex;
            align-items: center;
            background-color: rgb(25, 24, 24);
            color: aliceblue;
            font-size: 1.03vmax;
            border-radius: 0.5vmax;
        }
        .card-action-box .basics-actions-box{
            width: 70%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content:space-evenly;
            flex-direction: column;
        }
        .basics-actions-box .deploy-troop-box{
            width: 92%;
            height: 30%;
            background-color: rgb(25, 24, 24);
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 0.7vmax;
        }
        .power-cost-box{
            width: 6%;
            height: 60%;
            background-image: url(/resources/images/power.png);
            background-position: center;
            background-size:contain;
            background-repeat: no-repeat;
        }
        .do-button-box{
            height: 60%;
            width: 27%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-left: 0.5vmax;
        }
        .do-button{
            height: 100%;
            width: 85%;
            background-color: #8a2be2;
            border-radius: 1vmax;
            border-color: rgb(52, 10, 83);
            border-style: solid;
            border-width: 0.2vmax;
            display: flex;
            align-items: center;
            justify-content: center;
            color: black;
            font-size: 1.1vmax;
            transition-duration: 0.5s;
        }
        .do-button:hover{
            height: 100%;
            width: 85%;
            background-color: #ffffff;
            border-radius: 1vmax;
            border-color: rgb(52, 10, 83);
            border-style: solid;
            border-width: 0.2vmax;
            display: flex;
            align-items: center;
            justify-content: center;
            color: black;
            font-size: 1.1vmax;
            transition-duration: 0.5s;
        }
        .do-button-inhabilited{
            height: 100%;
            width: 85%;
            background-color: #696969;
            border-radius: 1vmax;
            border-color: rgb(32, 32, 32);
            border-style: solid;
            border-width: 0.2vmax;
            display: flex;
            align-items: center;
            justify-content: center;
            color: rgb(244, 244, 244);
            font-size: 1.1vmax;
        }
        .deploy-troop-box .deploy-explication-box{
            width: 50%;
            height: 60%;
            display: flex;
            align-items: center;
            justify-content: left;
            font-size: 1.05vmax;
            margin-left: 1vmax;
            color: aliceblue;
        }
        .basics-actions-box .kill-troop-box{
            width: 92%;
            height: 30%;
            background-color: rgb(25, 24, 24);
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 0.7vmax;
        }
        .kill-troop-box .kill-explication-box{
            width: 50%;
            height: 60%;
            display: flex;
            align-items: center;
            justify-content: left;
            font-size: 1.05vmax;
            margin-left: 1vmax;
            color: aliceblue;
        }
        .basics-actions-box .return-spy-box{
            width: 92%;
            height: 30%;
            background-color:rgb(25, 24, 24);
            display: flex;
            align-items: center;
            justify-content: left;
            border-radius: 0.7vmax;
        }
        .return-spy-box .return-explication-box{
            width: 50%;
            height: 60%;
            display: flex;
            align-items: center;
            justify-content: left;
            font-size: 1.05vmax;
            margin-left: 1vmax;
            color: aliceblue;
    
        }
        .play-buy-card-button{
            color: aliceblue;
            width: 10vmax;
            height: 3vmax;
            display:flex;
            align-items: center;
            justify-content: center;
            font-size: 1.3vmax;
            border-radius: 0.5vmax;
            border: 0.2vmax solid rgb(25, 24, 24);; 
            background: rgb(0,0,0);
            background: linear-gradient(0deg, rgba(0,0,0,1) 0%, rgba(49,39,72,1) 50%, rgba(95,75,139,1) 100%);
            transition-duration: 0.5s;
        }
        .play-buy-card-button:hover{
            color: rgb(0, 0, 0);;
            width: 10vmax;
            height: 3vmax;
            display:flex;
            align-items: center;
            justify-content: center;
            font-size: 1.3vmax;
            border-radius: 0.5vmax;
            border: 0.2vmax solid rgba(95,75,139,1); 
            background: rgb(245, 245, 245);
            background: linear-gradient(0deg, rgb(242, 242, 242) 0%, rgb(179, 175, 189) 50%, rgb(158, 129, 220) 100%);
            transition-duration: 0.5s;
        }
        .play-buy-card-button-inhabilited{
            color: rgb(0, 0, 0);;
            width: 10vmax;
            height: 3vmax;
            display:flex;
            align-items: center;
            justify-content: center;
            font-size: 1.3vmax;
            border-radius: 0.5vmax;
            border: 0.2vmax solid rgb(46, 46, 46); 
            background: rgb(114, 114, 114);
            background: linear-gradient(0deg, rgb(147, 147, 147) 0%, rgb(129, 129, 129) 50%, rgb(86, 86, 86) 100%);
        }
        
.skip-turn-button{
    display:flex;
    align-items: center;
    justify-content: center;
    width: 11vmax;
    font-size: 1.3vmax;
    color: aliceblue;
    border-radius: 0.5vmax;
    border: 0.2vmax solid rgb(54, 24, 72); 
    background:#8a2be2;
    background: linear-gradient(0deg, #a677d2 0%, #8a2be2 50%, #5e15a3 100%);
    transition-duration: 0.5s;
}
.skip-turn-button:hover{
    display:flex;
    align-items: center;
    justify-content: center;
    width: 11vmax;
    font-size: 1.3vmax;
    color: #360c5d;
    border-radius: 0.5vmax;
    border: 0.2vmax solid #360c5d; 
    background: #e2c9fa;
    background: linear-gradient(0deg, #e6d9f3 0%, #cea9f1 50%, #8d65b1 100%);
    transition-duration: 1s;
}
.skip-turn-end-action-button{
    display:flex;
    align-items: center;
    justify-content: center;
    width: 11vmax;
    font-size: 1.3vmax;
    color: #4b4505;
    border-radius: 0.5vmax;
    border: 0.2vmax solid #4b4505; 
    background: #cdc947;
    background: linear-gradient(0deg, #d3d158 0%, #c3c130 50%, #8a8821 100%);
    transition-duration: 1s;
}
.skip-turn-end-action-button:hover{
    display:flex;
    align-items: center;
    justify-content: center;
    width: 11vmax;
    font-size: 1.3vmax;
    color: #4b4505;
    border-radius: 0.5vmax;
    border: 0.2vmax solid #4b4505; 
    background: #dddcaf;
    background: linear-gradient(0deg, #edecc6 0%, #f1ef96 50%, #d7d557 100%);
    transition-duration: 0.5s;
}
    </style>

</head>
<body>
    <script>
        var top = 10;
        function showPopUp(popup) {
            var overlay = document.getElementById(popup);
            // overlay.parentNode.appendChild(overlay)
            overlay.style.visibility = "visible";
            overlay.style.opacity = 1;
        }
           function dontShowPopUp(popup) {
            var overlay = document.getElementById(popup);
            overlay.style.visibility = "hidden";
            overlay.style.opacity = 0;
        }
</script>
<link rel="stylesheet" href="/resources/styles/mapUI.css"></link>
<script src="https://d3js.org/d3.v7.min.js" charset="utf-8"></script>
<script src="/js/map.js"></script>
    <script>
        //INICIA EL MAPA
        init('${game.id}')
    </script>
    <div class="fullscreen-game">  

        <!--LOYOUT ZONA SUPERIOR-->
        <div class="tophud">
            <div class="tophud-box tophud-b1">
                <div class="player-div" style="color: ${player.house.hexColor};">
                    JUGADOR&nbsp${turn} - ${player.name}
                </div>
            </div>
            <div class="tophud-box tophud-b2">
                <div class="resume-div">
                    <div class="resume-text">
                        Realiza alguna acción disponible
                    </div>
                </div>
            </div>
            <div class="tophud-box tophud-b3">
                <c:choose>
                    <c:when test="${game.endTurnAction.subactions.isEmpty()}">
                        <a href="/play/${gameId}/round/${round}/next" class="skip-turn-button" style="margin-right: 2vmax;">
                            PASAR TURNO
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="/play/${gameId}/round/${round}/next" class="skip-turn-end-action-button" style="margin-right: 2vmax; width: 21vmax;">
                            ACCIONES DE FINAL DE TURNO
                        </a>
                    </c:otherwise>
                </c:choose>
                
                <div class="round-div">
                    RONDA ${round}
                </div>
            </div>
        </div>  
         
        
        <div class="game-flex">
            <!--LAYOUT IZQUIERDA (RECURSOS)-->
            <div class="resource-layout" >
                <div class="resources-box">
                    <abbr title="Puntos de influencia: usa los puntos de influencia para comprar cartas del mercado" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image influence"></div>
                        <div class="resource-valor" >
                        ${player.influence}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Puntos de poder: utiliza los puntos de poder para realizar acciones básicas" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image power"></div>
                        <div class="resource-valor">
                            ${player.power}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Tropas disponibles para desplegar" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image troops"></div>
                        <div class="resource-valor">
                            ${player.troops}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Espías disponibles para desplegar" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image spies"></div>
                        <div class="resource-valor">
                            ${player.spies}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Tropas enemigas asesinadas" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image trophyHall"></div>
                        <div class="resource-valor">
                            ${player.trophyHall.size()}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Círculo interno: cartas ascendidas durante la partida" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image innerCircle"></div>
                        <div class="resource-valor">
                            ${totalinnerCirclevp}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Puntos de victoria totales" style="display:flex; width: 100%; height: 100%;">
                        <a onclick="showPopUp('VpPopUp')" href="JavaScript:void(0)" class="resource-image-vp"></a>
                        <div class="resource-valor">
                            ${totalVp}
                        </div>
                    </abbr>
                </div>
            </div>

            <!--CUADRO GRANDE (ZONA DE JUEGO)-->
            <div class="playing-box-roundN">
                <div class="card-action-box">
                    <!--ZONA DE MERCADO - TERMINADO-->
                    <div class="market-box">
                        <div class="market-zone" >
                            <div class="top-market-cards">
                                <!--Guardas de la casa - TERMINADO-->
                                <c:choose>
                                    <c:when test="${game.houseGuards.size()==0}">
                                        <div class="card-box" style="background-image: url(/resources/images/guardias_mercado.png);">

                                        </div>
                                    </c:when>   
                                    <c:otherwise>
                                        <div class="popup" id="SellZoneCardPopUp${game.houseGuards[0].id}">
                                            <div class="popup-blue-box">
                                                <div class="popup-content">
                                                    <a onclick="dontShowPopUp('SellZoneCardPopUp${game.houseGuards[0].id}')" class="x">&times;</a>
                                                    <div class="popup-content-box">
                                                        <div class="principalCard" style="width: 23vmax; height: 32vmax">
                                                        <img src="/resources/images/cardsModel.png" class="cardImage" onclick="showPopUp('CardPopUp${game.houseGuards[0].id}')">
                                                        <spring:url value="/cards/{cardId}" var="cardUrl">
                                                            <spring:param name="cardId" value="${game.houseGuards[0].id}" />
                                                        </spring:url>
                                                            <div class="topTextName">
                                                                <b>
                                                                    <c:out value="${game.houseGuards[0].name}" />
                                                                </b>
                                                            </div>
                                                            <div class="topTextCost">
                                                                <b>
                                                                    <c:out value="${game.houseGuards[0].cost} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextAspect">
                                                                <b>
                                                                    <c:out value="${game.houseGuards[0].aspect.name} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextHalfDeck">
                                                                <b>
                                                                    <c:out value="${game.houseGuards[0].halfDeck.name} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextRulesText">
                                                                <b>
                                                                    <c:out value="${game.houseGuards[0].rulesText} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextRarity">
                                                                <b>
                                                                    <text id="t${game.houseGuards[0].id}" style="font-size: 180%;">
                                                                        <script>
                                                                            var rarity = " &#8226 ".repeat(parseInt("${game.houseGuards[0].rarity}"))
                                                                            document.getElementById("t${game.houseGuards[0].id}").innerHTML = rarity
                                                                        </script>
                                                                    </text>
                                                                </b>
                                                            </div>
                                                            <div class="topTextDeckVP"  style="margin-top: 0.2vmax;">
                                                                <b>
                                                                    <c:out value="${game.houseGuards[0].deckVP} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextInnerCirclePV"  style="margin-top: 0.2vmax;">
                                                                <b>
                                                                    <c:out value="${game.houseGuards[0].innerCirclePV} " />
                                                                </b>
                                                            </div>
                                                        </div>
                                                        <c:choose>
                                                            <c:when test="${game.houseGuards[0].cost<=player.influence}">
                                                                <a type="submit" href="${round}/buy/${game.houseGuards[0].id}" class="play-buy-card-button" style="font-size: 1.1vmax;">COMPRAR CARTA</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="play-buy-card-button-inhabilited" style="font-size: 1.1vmax;">      
                                                                    COMPRAR CARTA
                                                                </div>
                                                                <div style="color: aliceblue; font-size: 1.1vmax;">
                                                                    No tienes suficiente influencia para comprar esta carta.
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="card-box" title="${game.houseGuards[0].rulesText}" onclick="showPopUp('SellZoneCardPopUp${game.houseGuards[0].id}')" style="cursor: pointer;">
                                            <div class="card-name-box">
                                                <div class="card-name-center"><b>${game.houseGuards[0].name}</b></div>
                                            </div>
                                            <div class="card-cost-vp-box">
                                                <div class="card-cost">${game.houseGuards[0].cost}</div>
                                                <div class="card-deckVP">${game.houseGuards[0].deckVP}</div>
                                                <div class="card-innerCirclePV">${game.houseGuards[0].innerCirclePV}</div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <!--Sacerdotisas de Lolth - TERMINADO-->
                                <c:choose>
                                    <c:when test="${game.lolths.size()==0}">
                                        <div class="card-box"  style="background-image: url(/resources/images/lolth_mercado.png);">
                                
                                        </div>
                                    </c:when>   
                                    <c:otherwise>
                                        <div class="popup" id="SellZoneCardPopUp${game.lolths[0].id}">
                                            <div class="popup-blue-box">
                                                <div class="popup-content">
                                                    <a onclick="dontShowPopUp('SellZoneCardPopUp${game.lolths[0].id}')" class="x">&times;</a>
                                                    <div class="popup-content-box">
                                                        <div class="principalCard" style="width: 23vmax; height: 32vmax">
                                                        <img src="/resources/images/cardsModel.png" class="cardImage" onclick="showPopUp('CardPopUp${game.lolths[0].id}')">
                                                        <spring:url value="/cards/{cardId}" var="cardUrl">
                                                            <spring:param name="cardId" value="${game.lolths[0].id}" />
                                                        </spring:url>
                                                            <div class="topTextName">
                                                                <b>
                                                                    <c:out value="${game.lolths[0].name}" />
                                                                </b>
                                                            </div>
                                                            <div class="topTextCost">
                                                                <b>
                                                                    <c:out value="${game.lolths[0].cost} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextAspect">
                                                                <b>
                                                                    <c:out value="${game.lolths[0].aspect.name} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextHalfDeck">
                                                                <b>
                                                                    <c:out value="${game.lolths[0].halfDeck.name} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextRulesText">
                                                                <b>
                                                                    <c:out value="${game.lolths[0].rulesText} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextRarity">
                                                                <b>
                                                                    <text id="t${game.lolths[0].id}" style="font-size: 180%;">
                                                                        <script>
                                                                            var rarity = " &#8226 ".repeat(parseInt("${game.lolths[0].rarity}"))
                                                                            document.getElementById("t${game.lolths[0].id}").innerHTML = rarity
                                                                        </script>
                                                                    </text>
                                                                </b>
                                                            </div>
                                                            <div class="topTextDeckVP"  style="margin-top: 0.2vmax;">
                                                                <b>
                                                                    <c:out value="${game.lolths[0].deckVP} " />
                                                                </b>
                                                            </div>
                                                            <div class="topTextInnerCirclePV"  style="margin-top: 0.2vmax;">
                                                                <b>
                                                                    <c:out value="${game.lolths[0].innerCirclePV} " />
                                                                </b>
                                                            </div>
                                                        </div>
                                                        <c:choose>
                                                            <c:when test="${game.lolths[0].cost<=player.influence}">
                                                                <a type="submit" href="${round}/buy/${game.lolths[0].id}" class="play-buy-card-button" style="font-size: 1.1vmax;">COMPRAR CARTA</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="play-buy-card-button-inhabilited" style="font-size: 1.1vmax;">      
                                                                    COMPRAR CARTA
                                                                </div>
                                                                <div style="color: aliceblue; font-size: 1.1vmax;">
                                                                    No tienes suficiente influencia para comprar esta carta.
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="card-box" title="${game.lolths[0].rulesText}" onclick="showPopUp('SellZoneCardPopUp${game.lolths[0].id}')" style="cursor: pointer;">
                                            <div class="card-name-box">
                                                <div class="card-name-center"><b>${game.lolths[0].name}</b></div>
                                            </div>
                                            <div class="card-cost-vp-box">
                                                <div class="card-cost">${game.lolths[0].cost}</div>
                                                <div class="card-deckVP">${game.lolths[0].deckVP}</div>
                                                <div class="card-innerCirclePV">${game.lolths[0].innerCirclePV}</div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <!--Titulo de Mercado - TERMINADO-->
                                <div style="height: 100%; width: 14vmax; display: flex; justify-content: center;">
                                    <div style="margin-top: 2vmax; font-size: 2vmax; color: aliceblue;"><b> MERCADO </b></div>
                                </div>
                                <!--Cartas devoradas - TERMINADO -->
                                <c:choose>
                                    <c:when test="${game.devoured.size()==0}">
                                        <div class="card-box" style="background-image: url(/resources/images/devoradas_mercado.png);">
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="card-box" style="background-image: url(/resources/images/devored_card.png);" title="Cartas devoradas">
                                            <b class="bold-gold-number" style="color: aliceblue">${game.devoured.size()}</b>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <!--Mazo de robo de mercado - TERMINADO -->
                                <c:choose>
                                    <c:when test="${game.gameDeck.size()==0}">
                                        <div class="card-box" style="background-image: url(/resources/images/mazo_mercado.png);">
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="card-box" style="background-image: url(/resources/images/card_back.png);" title="Mazo de mercado">
                                            <b class="bold-gold-number" style="color: aliceblue">${game.gameDeck.size()}</b>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                
                            </div>
                            <!--Zona de compra de cartas del mercado (6 cartas) - TERMINADO-->
                            <div class="top-market-cards">
                                <c:forEach varStatus="counter" items="${sellZoneCounter}" var="a">
                                    <c:choose>
                                        <c:when test="${game.sellZone[counter.index].id==0}">
                                            <div class="card-box"  style="background-image: url(/resources/images/card-market-sellzone.png);">
                                
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="popup" id="SellZoneCardPopUp${game.sellZone[counter.index].id}">
                                                <div class="popup-blue-box">
                                                    <div class="popup-content">
                                                        <a onclick="dontShowPopUp('SellZoneCardPopUp${game.sellZone[counter.index].id}')" class="x">&times;</a>
                                                        <div class="popup-content-box">
                                                            <div class="principalCard" style="width: 23vmax; height: 32vmax">
                                                            <img src="/resources/images/cardsModel.png" class="cardImage" onclick="showPopUp('CardPopUp${game.sellZone[counter.index].id}')">
                                                            <spring:url value="/cards/{cardId}" var="cardUrl">
                                                                <spring:param name="cardId" value="${game.sellZone[counter.index].id}" />
                                                            </spring:url>
                                                                <div class="topTextName">
                                                                    <b>
                                                                        <c:out value="${game.sellZone[counter.index].name}" />
                                                                    </b>
                                                                </div>
                                                                <div class="topTextCost">
                                                                    <b>
                                                                        <c:out value="${game.sellZone[counter.index].cost} " />
                                                                    </b>
                                                                </div>
                                                                <div class="topTextAspect">
                                                                    <b>
                                                                        <c:out value="${game.sellZone[counter.index].aspect.name} " />
                                                                    </b>
                                                                </div>
                                                                <div class="topTextHalfDeck">
                                                                    <b>
                                                                        <c:out value="${game.sellZone[counter.index].halfDeck.name} " />
                                                                    </b>
                                                                </div>
                                                                <div class="topTextRulesText">
                                                                    <b>
                                                                        <c:out value="${game.sellZone[counter.index].rulesText} " />
                                                                    </b>
                                                                </div>
                                                                <div class="topTextRarity">
                                                                    <b>
                                                                        <text id="t${game.sellZone[counter.index].id}" style="font-size: 180%;">
                                                                            <script>
                                                                                var rarity = " &#8226 ".repeat(parseInt("${game.sellZone[counter.index].rarity}"))
                                                                                document.getElementById("t${game.sellZone[counter.index].id}").innerHTML = rarity
                                                                            </script>
                                                                        </text>
                                                                    </b>
                                                                </div>
                                                                <div class="topTextDeckVP"  style="margin-top: 0.2vmax;">
                                                                    <b>
                                                                        <c:out value="${game.sellZone[counter.index].deckVP} " />
                                                                    </b>
                                                                </div>
                                                                <div class="topTextInnerCirclePV"  style="margin-top: 0.2vmax;">
                                                                    <b>
                                                                        <c:out value="${game.sellZone[counter.index].innerCirclePV} " />
                                                                    </b>
                                                                </div>
                                                            </div>
                                                            <c:choose>
                                                                <c:when test="${game.sellZone[counter.index].cost<=player.influence}">
                                                                    <a type="submit" href="${round}/buy/${game.sellZone[counter.index].id}" class="play-buy-card-button" style="font-size: 1.1vmax;">COMPRAR CARTA</a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div class="play-buy-card-button-inhabilited" style="font-size: 1.1vmax;">      
                                                                        COMPRAR CARTA
                                                                    </div>
                                                                    <div style="color: aliceblue; font-size: 1.1vmax;">
                                                                        No tienes suficiente influencia para comprar esta carta.
                                                                    </div>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                            <div class="card-box" title="${game.sellZone[counter.index].rulesText}" onclick="showPopUp('SellZoneCardPopUp${game.sellZone[counter.index].id}')" style="cursor: pointer;">
                                                <div class="card-name-box">
                                                    <div class="card-name-center"><b>${game.sellZone[counter.index].name}</b></div>
                                                </div>
                                                <div class="card-cost-vp-box">
                                                    <div class="card-cost">${game.sellZone[counter.index].cost}</div>
                                                    <div class="card-deckVP">${game.sellZone[counter.index].deckVP}</div>
                                                    <div class="card-innerCirclePV">${game.sellZone[counter.index].innerCirclePV}</div>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <!--ZONA DE CARTAS DEL JUGADOR - TERMINADO-->
                    <div class="player-cards-box">
                        <div class="player-hand-zone-box">
                            <div class="player-hand-margin-box">
                                <c:forEach var="card" items="${player.hand}">
                                    <div class="popup" id="HandCardPopUp${card.id}">
                                        <div class="popup-blue-box">
                                            <div class="popup-content">
                                                <a onclick="dontShowPopUp('HandCardPopUp${card.id}')" class="x">&times;</a>
                                                <div class="popup-content-box">
                                                    <div class="principalCard" style="width: 23vmax; height: 32vmax">
                                                    <spring:url value="/cards/{cardId}" var="cardUrl">
                                                        <spring:param name="cardId" value="${card.id}" />
                                                    </spring:url>
                                                        <img src="/resources/images/cardsModel.png" class="cardImage" onclick="showPopUp('CardPopUp${card.id}')">
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
                                                        <div class="topTextDeckVP"  style="margin-top: 0.2vmax;">
                                                            <b>
                                                                <c:out value="${card.deckVP} " />
                                                            </b>
                                                        </div>
                                                        <div class="topTextInnerCirclePV"  style="margin-top: 0.2vmax;">
                                                            <b>
                                                                <c:out value="${card.innerCirclePV} " />
                                                            </b>
                                                        </div>
                                                    </div>
                                                    <a type="submit" href="${round}/play-card/${card.id}" class="play-buy-card-button"> JUGAR CARTA</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-hand-box" title="${card.rulesText}" onclick="showPopUp('HandCardPopUp${card.id}')" style="cursor: pointer;">
                                        <div class="card-name-center"><b>${card.name}</b></div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <!--RESUMEN DE MAZOS Y ACCIONES BÁSICAS - TERMINADO-->
                    <div class="basics-actions-and-resume-box">
                        <!--RESUMEN DE MAZOS - TERMINADO-->
                        <div class="resume-box">
                            <div class="resume-decks-box">
                                <div class="resume-decks-text">
                                    <div class="resume-decks-secuaz-image"></div>
                                    <b><div style="margin-left: 0.1vmax;">Mazo de robo:  ${player.deck.size()}</div></b>
                                </div>
                            </div>
                            <div class="resume-decks-box">
                                <div class="resume-decks-text">
                                    <div class="resume-decks-secuaz-image"></div>
                                    <b><div style="margin-left: 0.1vmax;">Mazo de descartes:  ${player.discarded.size()}</div></b>
                                </div>
                            </div>
                            <div class="resume-decks-box">
                                <div class="resume-decks-text">
                                    <div class="resume-decks-hand-image"></div>
                                    <b><div style="margin-left: 0.1vmax;">Cartas jugadas:  ${player.played.size()}</div></b>
                                </div>
                            </div>
                        </div>
                        <!--ACCIONES BÁSICAS - TERMINADO-->
                        <div class="basics-actions-box">
                            <div class="deploy-troop-box">
                                <div class="power-cost-box" style="opacity: 0%; margin-left: 0.3vmax;">
                                </div>
                                <div class="power-cost-box" style="opacity: 0%;">
                                </div>
                                <div class="power-cost-box">
                                </div>
                                <div class="deploy-explication-box"> 
                                    <b>DESPLEGAR TROPA</b>
                                </div>
                                <div class="do-button-box">
                                    <c:choose>
                                        <c:when test="${player.power>=1}">
                                            <a href='/play/${gameId}/round/${round}/basicPlaceTroop' class="do-button">
                                                REALIZAR
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="do-button-inhabilited" title="No tienes suficiente poder para realizar esta acción.">
                                                REALIZAR
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="kill-troop-box">
                                <div class="power-cost-box" style="margin-left: 0.3vmax;">
                                </div>
                                <div class="power-cost-box">
                                </div>
                                <div class="power-cost-box">
                                </div>
                                <div class="kill-explication-box"> 
                                    <b>ELIMINAR TROPA ENEMIGA</b>
                                </div>
                                <div class="do-button-box">
                                    <c:choose>
                                        <c:when test="${player.power>=3}">
                                            <a href='/play/${gameId}/round/${round}/basicKillTroop'  class="do-button">
                                                REALIZAR
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="do-button-inhabilited" title="No tienes suficiente poder para realizar esta acción.">
                                                REALIZAR
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="return-spy-box">
                                <div class="power-cost-box" style=" margin-left: 0.3vmax;">
                                </div>
                                <div class="power-cost-box">
                                </div>
                                <div class="power-cost-box">
                                </div>
                                <div class="return-explication-box"> 
                                    <b>DEVOLVER ESPÍA ENEMIGO</b>
                                </div>
                                <div class="do-button-box">
                                    <c:choose>
                                        <c:when test="${player.power>=3}">
                                            <a href='/play/${gameId}/round/${round}/basicReturnEnemySpy' class="do-button">
                                                REALIZAR
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="do-button-inhabilited" title="No tienes suficiente poder para realizar esta acción.">
                                                REALIZAR
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--MAPA-->
                <div class="map-box">
                    <svg id="graph"></svg>
                    <!-- <motero2k:positionTable>
                    </motero2k:positionTable> -->
                </div>
            </div>

                <!--POPUPS AQUI!!!-->
                <div class="popup" id="VpPopUp">
                    <div class="popup-blue-box">
                        <a onclick="dontShowPopUp('VpPopUp')" href="JavaScript:void(0)" class="x">&times;</a>
                        <div class="ls">
                            <p>Puntos por control simple: ${vp.controlVP}</p>
                            <p>Puntos por control total: ${vp.totalControlVP}</p>
                            <p>Puntos por trofeos: ${vp.trophyHallVP}</p>
                            <p>Puntos en mano: ${vp.handVP}</p>
                            <p>Puntos en la pila de descarte: ${vp.dicardPileVP}</p>
                            <p>Puntos en mazo: ${vp.deckVP}</p>
                            <p>Puntos por cartas ascendidas: ${vp.innerCircleVP}</p>
                            <p>Puntos acumulados por acciones de cartas: ${vp.earnedVP}</p>
                            <p>Puntos acumulados por marcadores de control: ${vp.markerVP}</p>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    </div>

  
</body>

</html>