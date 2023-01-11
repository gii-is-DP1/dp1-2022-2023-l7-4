<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/resources/styles/tyrants.css">
<link rel="stylesheet" href="/resources/styles/home-icon-pop.css">

<body>
    <div class="fullscreen-game">  
        <div class="tophud">
            <div class="tophud-box tophud-b1">
                <a class="home-icon-menu" onclick="showPopUp('HomePopUp')" href="JavaScript:void(0)">
                    
                </a>
                <div class="player-div" style="color: ${player.house.hexColor};">
                    JUGADOR&nbsp${turn} - ${player.name}
                </div>
            </div>
            <div class="tophud-box tophud-b2">
                <div class="resume-div">
                    <c:choose>
                        <c:when test="${round<0}">
                            <div class="resume-text">
                                Te quedan ${whiteTroopsLeft} tropas por colocar
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="resume-text">
                                Selecciona una posición inicial
                            </div>
                        </c:otherwise>
                    </c:choose>
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

            <div class="positions-round0">
                <div style="width: 100%; height: 100%; ">
                    <!--MAPA===========================================================================================-->
                    <!-- IMPORTS NECESARIOS DE MAPA -->
                      <script src="https://d3js.org/d3.v7.min.js" charset="utf-8"></script>
                      <link rel="stylesheet" href="/resources/styles/mapUI.css"></link>
                      <script src="/js/map.js"></script>
                      <script>const selectedPositions = [];</script>

                      
                      <svg id="graph"></svg>
                      <form:form id="choose-position-form" method="post">

                      <c:forEach items="${positions}" var="position">
                          <script>
                              selectedPositions.push(parseInt("${position.id}"))
                          </script>
                      </c:forEach>
  
                      <script>
                           init('${game.id}',selectedPositions)
                      </script>
                      <input type="hidden" id="chosen-position" name="positionId"/>
  
                  </form:form>
                  <!-- ================================================================================================ -->
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