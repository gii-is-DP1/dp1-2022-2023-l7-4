<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <link rel="stylesheet" href="/styles/roundzero.css">
</head>
<body>
    <div class="fullscreen-game">  
        <div class="tophud">
            <div class="tophud-box tophud-b1">
                <div class="player-div">
                    JUGADOR ${player.name} - ${player.user.name}
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
                    <abbr title="Usa puntos de influencia para comprar cartas en el mercado" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image influence"></div>
                        <div class="resource-valor">
                        ${player.influence}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Usa puntos de poder realizar acciones básicas" style="display:flex; width: 100%; height: 100%;">
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
                    <abbr title="Círculo interno (cartas ascendidas durante la partida)" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image innerCircle"></div>
                        <div class="resource-valor">
                            ${player.innerCircle.size()}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Punto de victoria totales" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image pv"></div>
                        <div class="resource-valor">
                            ${pv}
                        </div>
                    </abbr>
                </div>
            </div>
            <div class="positions-round0">
                <div class="position-scroll">
                    <form:form modelAttribute="idposition">
                        <motero2k:positionTable>   
                        </motero2k:positionTable>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default">CONFIRMAR</button>
                             </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>      
</body>