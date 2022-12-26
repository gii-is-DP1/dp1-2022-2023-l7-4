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
                        <div class="resource-image vp"></div>
                        <div class="resource-valor">
                            ${vp}
                        </div>
                    </abbr>
                </div>
            </div>

            <div class="positions-round0">
                <div class="position-scroll">
                    <div class="position-totally">

                        <form action="/play/${gameId}/round/${round}/next" method="get">
                            <input type="submit" value="PASAR TURNO" >
                        </form>
                    </div>
                </div>
                <a href='/play/${gameId}/round/${round}/placeTroop?reachable=true&price=true&numberOfMoves=1' >
                <div class="aBotton">
                    <div class="especial-btn btn-two"><div class="textbtn">DESPLEGAR TROPA - PRECIO: 2 DE PODER</div></div>
                    </div>
			</a><br>
            </div>
            
        </div>
    </div>
    <style>
        .textbtn {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            opacity: 100%;
        }
        @import 'https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300';

        .body {
        width: 100%;
        height: 100%;
        overflow: hidden;
        margin: 0;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        font-family: 'Open Sans Condensed', sans-serif;
        }

        .box-2 { background-color: #282121; }

        .especial-btn {
            opacity: 90%;
            line-height: 50px;
            height: 50px;
            text-align: center;
            width: 100%;
            cursor: pointer;
        }
        .container-button{
            margin-left: 0%;
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
        .aBotton{
            text-align: center;
        }

    </style>     
  
</body>


