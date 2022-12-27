<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="/resources/styles/tyrants.css">
<body>
    <div class="fullscreen-game">  
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
                <div class="round-div">
                    RONDA ${round}
                </div>
            </div>
        </div>  
         
        <div class="game-flex">
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
                        <!-- <div class="resource-image vp"></div> -->
                        <div class="resource-valor">
                            ${totalVp}
                        </div>
                    </abbr>
                </div>
            </div>
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

            <div class="positions-round0">
                <div class="position-scroll">
                    <div class="position-totally">
                        <h1>WORK IN PROGRESS</h1>

                        <form action="/play/${gameId}/round/${round}/next" method="get">
                            <input type="submit" value="PASAR TURNO" >
                        </form>
                    </div>
                </div>
            </div>
            
        </div>
    </div>     
  
</body>

<style>
     .popup {
        background-color: rgba(231, 238, 245,0.5);
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
    }
    .popup .x {
        margin-left: 10px;
        color: black;
        user-select: none;
        width: 50px;
        height: 50px;
        position: absolute;
        align-self: flex-start;
    }
    p{
        margin: 10px;

    }
    #icon{
        width: 50px;
        height: 50px;
        color: red;

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


