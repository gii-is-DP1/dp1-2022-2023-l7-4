<%@ page session="false" trimDirectiveWhitespaces="true" %>
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
                            ${player.innerCircle.size()}
                        </div>
                    </abbr>
                </div>
                <div class="resources-box">
                    <abbr title="Puntos de victoria totales" style="display:flex; width: 100%; height: 100%;">
                        <div class="resource-image vp"></div>
                        <div class="resource-valor">
                            ${pv}
                        </div>
                    </abbr>
                </div>
            </div>

            <div class="positions-round0">
                <div class="position-scroll">
                    <div class="position-totally">
                        <form:form modelAttribute="idposition">
                            <motero2k:positionTable>   
                            </motero2k:positionTable>
                        </form:form>
                    </div>
                </div>
            </div>
            
        </div>
    </div>     
  
</body>
<style>
    .fullscreen-game{
    height: 100%;
    width: 100%;
}
/* HUD SUPERIOR */
.tophud{
    height: 6%;
    background-color: rgb(25, 24, 24);
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
}
.tophud-box{
    display: flex;
    width: 33%;
    height: 100%;            
}
.tophud-b1{
    justify-content: flex-start;
    align-items: center;
}
.player-div{
    margin-left: 1%;
    font-size: 1.5vmax;
    color: aliceblue;
}
.tophud-b2{
    justify-content: center;
    align-items: center;
}
.resume-div{
    width: 100%;
    height: 65%;
    background-color: aliceblue;
    display: flex;
    align-items: center;
    justify-content: center;
    border-color:darkgoldenrod;
    border-width: 3px;
    border-style: solid;
}
.resume-text{
    font-size: 1.5vmax;
}
.tophud-b3{
    justify-content: flex-end;
    align-items: center;
}
.round-div{
    margin-right: 1%;
    font-size: 1.5vmax;
    color: aliceblue;
}

/* Box general 2*/
.game-flex{
    height: 94%;
    width: 100%;
    display: flex;
}

/* resources loyout */
.resource-layout{
    width: 5%;
    background-color:rgb(25, 24, 24);
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
}
.resources-box{
    width: 100%;
    height: 10%;
    display: flex;
}
.resource-image{
    display: flex;
    align-items: center;
    justify-content: center;
    width: 55%;
    height: 100%;
    background-position: center;
    background-size: 90%;
    background-repeat:no-repeat;
}
.influence{
    background-image: url(/resources/images/influence.png);
}
.power{
    background-image: url(/resources/images/power.png);
}
.troops{
    background-image: url(/resources/images/troop_white.png);
}
.spies{
    background-image: url(/resources/images/spy_white.png);
}
.trophyHall{
    background-image: url(/resources/images/trophy_white.png);
}
.innerCircle{
    background-image: url(/resources/images/innerCircle.png);
}
.vp{
    background-image: url(/resources/images/vp_white.png);
}
.resource-valor{
    display: flex;
    align-items: center;
    justify-content: center;
    width: 45%;
    height: 100%;
    color: aliceblue;
    font-size: 1.2vmax;
}

/*positions round0*/
.positions-round0{
    width: 96%;
    display: flex;
    align-items: center;
    justify-content: center;
    background-image: url(/resources/images/round0-background.jpg);
    background-position: center;
    background-size:cover;
}
.position-scroll{
    display: flex;
    width: 60%;
    height: 80%;
    border: 2px solid rgba(184, 134, 190, 0.667);
    background-color: #f6a6ff98;
    overflow-y: scroll;
    justify-content: center;
     
}

.position-scroll::-webkit-scrollbar {
    width: 1em;
    height: 1em;
}

.position-scroll::-webkit-scrollbar-track {
    background: rgb(212, 135, 232);
    border-radius: 100vw;
    margin-block: 0.2em;
}

.position-scroll::-webkit-scrollbar-thumb {
    background: rgb(122, 13, 122);
    border: 0.25em solid rgb(212, 135, 232);
    border-radius: 100vw;
}

.position-scroll::-webkit-scrollbar-thumb:hover {
background: hsl(120 100% 10% / 1);
} 

.position-totally{
    display: flex;
    width: 100%;
    height: 100%;
}

input:checked ~label{
    color: red;
    background-color: #96dfe4;
}

.btn-submit-form{
    background-color: #96dfe4;
    border-width: 1px;
}

a:link, a:visited, a:active {
    text-decoration: none;

}
body{
    margin : 0;
    padding : 0;
}
</style>

