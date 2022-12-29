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
<body>
    <div class="fullscreen-game">  

        <!--LOYOUT ZONA SUPERIOR-->
        <div class="tophud">
            <div class="tophud-box tophud-b1">
                <div class="player-div">
                    JUGADOR${turn} - ${player.name}
                </div>
            </div>
            <div class="tophud-box tophud-b2">
                <div class="resume-div">
                    <div class="resume-text">
                        Selecciona una ubicación inicial
                    </div>
                </div>
            </div>
            <div class="tophud-box tophud-b3">
                <div style="margin-top: 1vmax;">
                    <form action="/play/${gameId}/round/${round}/next" method="get">
                        <input type="submit" value="PASAR TURNO" style="width: 10vmax; height: 1.5vmax; font-size: 1vmax; margin-right: 2vmax;">
                    </form>
                </div>
                <div class="round-div">
                    RONDA ${round}
                </div>
            </div>
        </div>  
         
        
        <div class="game-flex">
            <!--LAYOUT IZQUIERDA (RECURSOS)-->
            <div class="resource-layout">
                <div class="resources-box">
                    <abbr title="Puntos de influencia: usa los puntos de influencia para comprar cartas del mercado" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image influence"></div>
                        <div class="resource-valor">
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
                        <a onclick="showPopUp('VpPopUp')" href="JavaScript:void(0)" class="resource-image vp"></a>
                        <div class="resource-valor">
                            ${totalVp}
                        </div>
                    </abbr>
                </div>
            </div>

            <!--CUADRO GRANDE (ZONA DE JUEGO)-->
            <div class="playing-box-roundN">
                <div class="card-action-box">
                    <div class="market-box">
                        <div class="market-zone" >
                            <div class="top-market-cards">
                                <div class="card-box">

                                </div>
                                <div class="card-box">

                                </div>
                                <div class="empty-card-box">

                                </div>
                                <div class="empty-card-box">

                                </div>
                                <div class="card-box">

                                </div>
                                <div class="card-box">

                                </div>
                            </div>
                            <div class="top-market-cards">
                                <c:forEach var="card" items="${game.sellZone}">
                                    <div class="popup" id="CardPopUp${card.id}">
                                        <div class="popup-content">
                                            <a onclick="dontShowPopUp('CardPopUp${card.id}')" class="x">&times;</a>
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
                                                </div>
                                                <!--ANDRES EL BOTON DE COMPRAR ES ESTE!!!-->
                                                <button type="submit"> COMPRAR CARTA</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-box" title="${card.rulesText}" onclick="showPopUp('CardPopUp${card.id}')" style="cursor: pointer;">
                                        ${card.name}
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="player-cards-box">
                        <div class="player-hand-zone-box">
                            <div class="player-hand-margin-box">
                                <c:forEach var="card" items="${player.hand}">
                                    <div class="popup" id="CardPopUp${card.id}">
                                        <div class="popup-content">
                                            <a onclick="dontShowPopUp('CardPopUp${card.id}')" class="x">&times;</a>
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
                                                </div>
                                                <!--ANDRES EL BOTON DE JUGAR ES ESTE!!!-->
                                                <button type="submit"> JUGAR CARTA</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-hand-box" title="${card.rulesText}" onclick="showPopUp('CardPopUp${card.id}')" style="cursor: pointer;">
                                        ${card.name}
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="basics-actions-and-resume-box">
                        <div class="resume-box">
                            <div class="cards-player-deck">

                            </div>
                            <div class="cards-player-dicarded">

                            </div>
                            <div class="cards-player-promoted">

                            </div>
                            <div class="cards-deck-market">

                            </div>
                        </div>
                        <div class="basics-actions-box">
                            <div class="deploy-troop-box">
                                <a href='/play/${gameId}/round/${round}/basicPlaceTroop' >
                                    DESPLEGAR TROPA - 1 DE PODER
                                </a>
                            </div>
                            <div class="kill-troop-box">
                                <a href='/play/${gameId}/round/${round}/basicKillTroop' >
                                    MATAR TROPA ENEMIGA - 3 DE PODER
                                </a>
                            </div>
                            <div class="return-spy-box">
                                <a href='/play/${gameId}/round/${round}/basicReturnEnemySpy' >
                                    DEVOLVER ESPÍA ENEMIGO - 3 DE PODER
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="map-box">
                    <motero2k:positionTable>
                    </motero2k:positionTable>
                </div>
            </div>

                <!--POPUPS AQUI!!!-->
                <div class="popup" id="VpPopUp">
                    <a onclick="dontShowPopUp('VpPopUp')" href="JavaScript:void(0)" class="x">x</a>
                    <div class="ls">
                        <p>Puntos por control simple: ${vp.controlVP}</p>
                        <p>Puntos por control total: ${vp.totalControlVP}</p>
                        <p>Puntos por trofeos: ${vp.trophyHallVP}</p>
                        <p>Puntos en mano: ${vp.handVP}</p>
                        <p>Puntos en la pila de descarte: ${vp.dicardPileVP}</p>
                        <p>Puntos en mazo: ${vp.deckVP}</p>
                        <p>Puntos por cartas ascendidas: ${vp.innerCircleVP}</p>
                    </div>
                </div>




            </div>
        </div>
    </div>
</body>
<style>
    .popup {
    background-color: rgba(16, 64, 112, 0.814);
    height: 80%;
    width: 85%;
    visibility: hidden;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    display: flex;
    border: 3px;
    border-radius: 10px;
    font-size: 2vmax;
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
.popup .x {
    margin-left: 10px;
    color: rgb(255, 0, 0);
    user-select: none;
    width: 50px;
    height: 50px;
    position: absolute;
    align-self: flex-start;
    font-family: monospace;
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
</style>
<script>
    function showPopUp(popup) {
        var overlay = document.getElementById(popup);
        overlay.style.visibility = "visible";
        overlay.style.opacity = 1;
    }
       function dontShowPopUp(popup) {
        var overlay = document.getElementById(popup);
        overlay.style.visibility = "hidden";
        overlay.style.opacity = 0;
    }
</script>

<style>
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
        background-color: aquamarine;
        overflow-y: scroll;
    }
    .card-action-box .market-box{
        width: 100%;
        height: 52%;
        background-color: #8a2be2;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .market-box .market-zone{
        width: 93%;
        height: 92%;
        background-color: orange;
        display: flex;
        align-items: center;
        justify-content: space-evenly;
        flex-direction: column;
    }
    .market-zone .top-market-cards{
        width: 98%;
        height: 46%;
        background-color:rgb(255, 255, 255);
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .card-box{
        width: 15%;
        height: 100%;
        background-image: url(/resources/images/card_in_sellzone.png);
        background-position: center;
        background-size:contain;
        background-repeat: no-repeat;
    }
    .card-hand-box{
        width: 15%;
        height: 100%;
        background-image: url(/resources/images/card_in_hand.png);
        background-position: center;
        background-size:contain;
        background-repeat: no-repeat;
    }
    .top-market-cards .empty-card-box{
        width: 15%;
        height: 100%;
    }
    .card-action-box .player-cards-box{
        width: 100%;
        height: 28%;
        background-color: black;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .card-action-box .player-hand-zone-box{
        width: 93%;
        height: 85%;
        background-color:indigo;
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
        background-color: chartreuse;
        display: flex;
        align-items: center;
        justify-content:space-evenly;
        flex-direction: column;
    }
    .resume-box .cards-player-deck{
        width: 90%;
        height: 20%;
        background-color: deeppink;
    }
    .resume-box .cards-player-dicarded{
        width: 90%;
        height: 20%;
        background-color:gold;
    }
    .resume-box .cards-player-promoted{
        width: 90%;
        height: 20%;
        background-color:forestgreen;
    }
    .resume-box .cards-deck-market{
        width: 90%;
        height: 20%;
        background-color:rgb(144, 154, 104);
    }
    .card-action-box .basics-actions-box{
        width: 70%;
        height: 100%;
        background-color: brown;
        display: flex;
        align-items: center;
        justify-content:space-evenly;
        flex-direction: column;
    }
    .basics-actions-box .deploy-troop-box{
        width: 90%;
        height: 30%;
        background-color: cadetblue;
    }
    .basics-actions-box .kill-troop-box{
        width: 90%;
        height: 30%;
        background-color: coral;
    }
    .basics-actions-box .return-spy-box{
        width: 90%;
        height: 30%;
        background-color:darkslateblue;
    }

</style>

</html>




