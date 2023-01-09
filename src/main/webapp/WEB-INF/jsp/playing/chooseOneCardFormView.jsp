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
<link rel="stylesheet" href="/resources/styles/passactionbutton.css">
<link rel="stylesheet" href="/resources/styles/home-icon-pop.css">

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
.card-action-box{
    width: 27%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    background-color: rgba(255, 255, 255, 0.384);
}
.this-action-box{
    display: flex; 
    justify-content: center; 
    align-items: center; 
    margin-bottom: 1vmax; 
    padding: 1vmax;
    background-color:  rgba(255, 255, 255, 0.863);
    border-color: rgb(25, 24, 24);
    border-style: solid;
    border-width: 0.2vmax;
    border-radius: 0.5vmax;
}
.position-scroll2{
    display: flex;
    width: 60%;
    height: 80%;
    overflow-y: scroll;
    justify-content: center;
     
}
.promote-button{
    width: 8vmax;
    background-color: #e2c62b;
    border-radius: 1vmax;
    border-color: rgb(73, 83, 10);
    border-style: solid;
    border-width: 0.2vmax;
    display: flex;
    align-items: center;
    justify-content: center;
    color: black;
    font-size: 1.1vmax;
    transition-duration: 0.5s;
}
.promote-button:hover{
    width: 8vmax;
    background-color: #ffffff;
    border-radius: 1vmax;
    border-color: rgb(73, 83, 10);
    border-style: solid;
    border-width: 0.2vmax;
    display: flex;
    align-items: center;
    justify-content: center;
    color: black;
    font-size: 1.1vmax;
    transition-duration: 0.5s;
}
.devourer-button{
    width: 7vmax;
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
.devourer-button:hover{
    width: 7vmax;
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
</style>
<body>

    <div class="fullscreen-game">  
        <!--Loyout HUD parte superior-->
        <div class="tophud">
            <div class="tophud-box tophud-b1">
                <a class="home-icon-menu" onclick="showPopUp('HomePopUp')" href="JavaScript:void(0)">
                    
                </a>
                <div class="player-div"  style="color: ${player.house.hexColor};">
                    JUGADOR&nbsp${turn} - ${player.name}
                </div>
            </div>
            <div class="tophud-box tophud-b2">
                <div class="resume-div">
                    <c:choose>
                        <c:when test="${promoteLocation=='played'}">
                            <div class="resume-text">
                                Asciende una carta jugada durante este turno
                            </div>
                        </c:when>
                        <c:when test="${promoteLocation=='discarded'}">
                            <div class="resume-text">
                                Asciende una carta del mazo de descartes
                            </div>
                        </c:when>
                        <c:when test="${promoteLocation=='deck'}">
                            <div class="resume-text">
                                Asciende una carta jugada del mazo de robo
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="resume-text">
                                Devora una carta del mercado
                            </div>
                        </c:otherwise>
                    </c:choose>
                    
                </div>
            </div>
            <div class="tophud-box tophud-b3">
                <c:choose>
                    <c:when test="${price==true}">
                        <a href="/play/${gameId}/round/${round}" class="skip-action-button" style="width: 8vmax; margin-right: 2vmax;">CANCELAR</a>
                    </c:when>
                    <c:otherwise>
                        <a href="/play/${game.id}/round/${round}/skip" class="skip-action-button" style="margin-right: 2vmax;">OMITIR ACCIÓN</a>
                    </c:otherwise>
                </c:choose>
                <div class="round-div">
                    RONDA ${round}
                </div>
            </div>
        </div>  
        
        <div class="game-flex">
            <!--Loyout HUD parte izquierda-->
            <div class="resource-layout">
                <div class="resources-box">
                    <abbr title="Puntos de influencia: usa los puntos de influencia para comprar cartas del mercado">
                        <div class="resource-image influence"></div>
                        <div class="resource-valor">
                        ${player.influence}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Puntos de poder: utiliza los puntos de poder para realizar acciones básicas">
                        <div class="resource-image power"></div>
                        <div class="resource-valor">
                            ${player.power}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Tropas disponibles para desplegar">
                        <div class="resource-image troops"></div>
                        <div class="resource-valor">
                            ${player.troops}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Espías disponibles para desplegar">
                        <div class="resource-image spies"></div>
                        <div class="resource-valor">
                            ${player.spies}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Tropas enemigas asesinadas">
                        <div class="resource-image trophyHall"></div>
                        <div class="resource-valor">
                            ${player.trophyHall.size()}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Círculo interno: cartas ascendidas durante la partida">
                        <div class="resource-image innerCircle"></div>
                        <div class="resource-valor">
                            ${totalinnerCirclevp}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Puntos de victoria totales" >
                        <a onclick="showPopUp('VpPopUp')" href="JavaScript:void(0)" class="resource-image-vp"></a>
                        <div class="resource-valor">
                            ${totalVp}
                        </div>
                    </abbr>
                </div>
            </div>
            <!--Caja grande de juego-->
            <div class="positions-round0" style="justify-content: space-between;">
                <!--Carta en juego-->
                <div class="card-action-box">
                    <div class="this-action-box"  style="font-size: 1.1vmax;">
                        <b>CARTA EN JUEGO:</b>
                    </div>
                    <div class="principalCard" style="width: 23vmax; height: 32vmax">
                        <img src="/resources/images/cardsModel.png" class="cardImage">
                            <div class="topTextName">
                                <b>
                                    <c:out value="${game.cardInPlay.name}" />
                                </b>
                            </div>
                            <div class="topTextCost">
                                <b>
                                    <c:out value="${game.cardInPlay.cost} " />
                                </b>
                            </div>
                            <div class="topTextAspect">
                                <b>
                                    <c:out value="${game.cardInPlay.aspect.name} " />
                                </b>
                            </div>
                            <div class="topTextHalfDeck">
                                <b>
                                    <c:out value="${game.cardInPlay.halfDeck.name} " />
                                </b>
                            </div>
                            <div class="topTextRulesText">
                                <b>
                                    <c:out value="${game.cardInPlay.rulesText} " />
                                </b>
                            </div>
                            <div class="topTextRarity">
                                <b>
                                    <text id="t${game.cardInPlay.id}" style="font-size: 180%;">
                                        <script>
                                            var rarity = " &#8226 ".repeat(parseInt("${game.cardInPlay.rarity}"))
                                            document.getElementById("t${game.cardInPlay.id}").innerHTML = rarity
                                        </script>
                                    </text>
                                </b>
                            </div>
                            <div class="topTextDeckVP" style="margin-top: 0.2vmax;">
                                <b>
                                    <c:out value="${game.cardInPlay.deckVP} " />
                                </b>
                            </div>
                            <div class="topTextInnerCirclePV" style="margin-top: 0.2vmax;">
                                <b>
                                    <c:out value="${game.cardInPlay.innerCirclePV} " />
                                </b>
                            </div>
                    </div>
                </div>
                
                <!--Listado de cartas para ascender o devorar-->
                <div class="position-scroll2" style="width: 72%;height: 100%;">
                    <div class="position-totally">
                        <div style="display: flex; flex-wrap: wrap; width: 100%;">
                            <c:forEach var="card" items="${cards}">
                                <div style="width: 33%; height: 33vmax; display: flex; justify-content: center;align-items: center; flex-direction: column;">
                                    <div class="principalCard" style="width: 20vmax; height: 28vmax">
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
                                            <div class="topTextDeckVP" style="margin-top: 0.15vmax;">
                                                <b>
                                                    <c:out value="${card.deckVP} " />
                                                </b>
                                            </div>
                                            <div class="topTextInnerCirclePV" style="margin-top: 0.15vmax;">
                                                <b>
                                                    <c:out value="${card.innerCirclePV} " />
                                                </b>
                                            </div>
                                    </div>
                                    <c:choose>
                                        <c:when test="${devore}">
                                            <a type="submit" href="chosenCardToDevore/${card.id}" class="devourer-button" style="font-size: 1.3vmax; margin-top: 0.5vmax;">DEVORAR</a>
                                        
                                        </c:when>
                                        <c:otherwise>
                                            <a type="submit" href="chosenCardToPromote/${card.id}" class="promote-button" style="font-size: 1.3vmax; margin-top: 0.5vmax;">ASCENDER</a>
                                        </c:otherwise>
                                    </c:choose>
                                    
                                </div>
                            </c:forEach>
                        </div>
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
                            <p>Puntos en la pila de descarte: ${vp.discardPileVP}</p>
                            <p>Puntos en mazo: ${vp.deckVP}</p>
                            <p>Puntos por cartas ascendidas: ${vp.innerCircleVP}</p>
                            <p>Puntos acumulados por acciones de cartas: ${vp.earnedVP}</p>
                            <p>Puntos acumulados por marcadores de control: ${vp.markerVP}</p>
                        </div>
                    </div>
                </div>

                <div class="popup" id="HomePopUp">
                    <div class="popup-blue-box" style="width: 50%; height: 50%; background-color: rgba(0, 0, 0,0.6);">
                        <a onclick="dontShowPopUp('HomePopUp')" href="JavaScript:void(0)" class="x">&times;</a>
                        <div class="ls" style="justify-content: start; width: 100%; height: 100%;">
                            <div style="margin-top: 1vmax; font-size: 3vmax;"><b>AJUSTES</b></div>
                            <a href="http://localhost:8080" class="home-return-button" style="margin-top: 7vmax;">
                                Salir de la partida
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    </div>   

</body>
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