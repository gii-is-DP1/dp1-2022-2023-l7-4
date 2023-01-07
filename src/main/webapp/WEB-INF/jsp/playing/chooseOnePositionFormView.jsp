<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="/resources/styles/tyrants.css">
<link rel="stylesheet" href="/resources/styles/passactionbutton.css">
<link rel="stylesheet" href="/resources/styles/cardlisting.css">

<body>
    
<style>
    .card-action-box2{
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
.special-box{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 60%;
    height: 10%;
    background-color: rgba(16, 64, 112, 0.814);
    color: aliceblue;
    margin-bottom: 1vmax;
    font-size: 1.2vmax;
    border-color: black;
    border-radius: 1vmax;
    border-style: solid;
    border-width: 0.3vmax;
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
    <div class="fullscreen-game">  
        <!--HUD superior-->
        <div class="tophud">
            <div class="tophud-box tophud-b1">
                <div class="player-div"  style="color: ${player.house.hexColor};">
                    JUGADOR&nbsp${turn} - ${player.name}
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
                <c:choose>
                    <c:when test="${price==true}">
                        <a href="/play/${gameId}/round/${round}" class="skip-action-button" style="width: 8vmax; margin-right: 2vmax;">CANCELAR</a>
                    </c:when>
                    <c:otherwise>
                        <a href="skip" class="skip-action-button" style="margin-right: 2vmax;">OMITIR ACCIÓN</a>
                    </c:otherwise>
                </c:choose>
                <div class="round-div">
                    RONDA ${round}
                </div>
            </div>
        </div>  
         
        <div class="game-flex">
            <!--Resource layout-->
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
            <div class="positions-round0" style="display: flex; align-items: center;justify-content: center; width: 100%; height: 100%">
                <!--Carta en juego-->
                <c:choose>
                    <c:when test="${basicAction==true}">
                        
                    </c:when>
                    <c:otherwise>
                        <div class="card-action-box2" style="background-color: rgba(255, 255, 255, 0.384);">
                            <div class="this-action-box">
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
                    </c:otherwise>
                </c:choose>
                
                <!--Lista de posiciones-->
                <c:choose>
                    <c:when test="${basicAction==true}">
                        <div style="flex-direction: column;width: 100%;height: 100%;display: flex; align-items: center; justify-content: center;">
                            <c:if test="${special!=null}">
                                <div class="special-box" style="text-align: center;">
                                    <o:out><b>${special}</b></o:out> 
                                </div>
                            </c:if>
                            <c:if test="${noSpyToPlace!=null}">
                                <c:if test="${noSpyToPlace==true}">
                                    <div class="special-box">
                                        <o:out><b>Como no tienes más espías por colocar, puedes mover uno de tus espías</b></o:out> 
                                    </div>
                                </c:if>
                            </c:if>
                            <div class="position-scroll">
                                <div class="position-totally">
                                    <form:form modelAttribute="idposition">
                                        <motero2k:positionTable>
                                        </motero2k:positionTable>
                                    </form:form>
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
                    </c:when>
                    <c:otherwise>
                        <div class="positions-round0" style="flex-direction: column;width: 73%;height: 100%;">
                            <c:if test="${special!=null}">
                                <div class="special-box">
                                    <div style="text-align: center;"><o:out><b>${special}</b></o:out> </div>
                                </div>
                            </c:if>
                            <c:if test="${noSpyToPlace!=null}">
                                <c:if test="${noSpyToPlace==true}">
                                    <div class="special-box">
                                        <div style="text-align: center;"><o:out><b>Como no tienes más espías por colocar, puedes mover uno de tus espías</b></o:out> </div>
                                    </div>
                                </c:if>
                            </c:if>
                            <div class="position-scroll">
                                <div class="position-totally">
                                    <form:form modelAttribute="idposition">
                                        <motero2k:positionTable>
                                        </motero2k:positionTable>
                                    </form:form>
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
                    </c:otherwise>
                </c:choose>
                
            </div>
            
            
        </div>
    </div>     

</body>
