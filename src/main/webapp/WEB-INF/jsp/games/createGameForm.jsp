<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <link rel="stylesheet" href="/resources/styles/scrollbody.css">
    <link rel="stylesheet" href="/resources/styles/creategame.css">


    <div class="create-box">
        
        <!--CAJA IZQUIERDA -> Nuevo juego, reglas, nombre, mapa, primer mazo y segundo mazo-->
        <div class="left-box">
            <!--Nuevo juego y reglas-->
            <div class="new-game-and-rules-box">
                <div class="new-game-box">
                    <div class="new-game-text"><b>CREAR PARTIDA</b></div>
                </div>
                <div class="rules-box">
                    <a onclick="showPopUp('restrictions')" href="JavaScript:void(0)" class="vio">Mostrar normas de creación</a>
                </div>
            </div>

            <!--Nombre y mapa-->
            <div class="new-name-and-map-box">
                <div class="new-name-box new-name-text">
                    <form:form modelAttribute="game" class="form-horizontal" id="game-form" method="post" action="/games/create/plus">
                        <label class="name">Nombre del juego:</label>
                        <input type="text" name="name" value="${game.name}" style="width: 9vmax; border-radius: 0.5vmax;" >
                        
                        <input id="selected-map" type="hidden" name="mapTemplate" value="${game.mapTemplate.id}">
                        <input id="selected-deck-1" type="hidden" name="firstHalfDeck" value="${game.firstHalfDeck.id}">
                        <input id="selected-deck-2" type="hidden" name="secondHalfDeck" value="${game.secondHalfDeck.id}">
                        <div id="hidden-players">
                            <c:forEach var="player" items="${game.players}" varStatus="counter">
                                <input id="player-${counter.index+1}" type="hidden" name="players[${counter.index}]" value="${player.user.username}">
                            </c:forEach>
                        </div>
                    </form:form>
                </div>

                <div class="new-map-box new-map-text">
                    <div class="control-label" style="font-size: 1.2vmax; margin-bottom: 0%;">Mapa:</div>
                    <c:choose>
                        <c:when test="${game.mapTemplate != null}">
                            &nbsp
                            <div style="display: flex; margin-bottom: -2px; align-items: center;">
                                <a onclick="removeMap()" href="JavaScript:void(0)" style="color:rgb(179, 146, 188);">${game.mapTemplate}</a>
                                &nbsp<a onclick="removeMap()" href="JavaScript:void(0)" style="color: red;">x</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            &nbsp
                            <a onclick="showPopUp('mapsPopUp')" href="JavaScript:void(0)" style="  margin-bottom: -2px; color:darkseagreen;">Elegir un mapa</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!--Primer y segundo mazo-->
            <div class="full-deck-box">
                <div class="deck-title">
                    <b>MAZOS</b>
                </div>
                <div class="first-second-deck-box">
                    <div class="deck-box">
                        <div style="font-size: 1.2vmax; width: 50%; color: aliceblue;"><b>Primer mazo:</b></div>&nbsp
                        <c:choose>
                            <c:when test="${game.firstHalfDeck != null}">
                                <div style="width: 49%; margin-bottom: -2px; display: flex; justify-content: center; align-items: center;font-size: 1vmax;">
                                    <a onclick="removeDeck(1)" href="JavaScript:void(0)">${game.firstHalfDeck.name}</a>
                                    &nbsp<a onclick="removeDeck(1)" href="JavaScript:void(0)"  style="color: red;">x</a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a onclick="showPopUp('decks1PopUp')" href="JavaScript:void(0)" 
                                style="color: rgb(100, 75, 143);width: 49%; text-align: center; margin-bottom: -2px;">Elegir</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                
                    <div class="deck-box">
                        <div style="font-size: 1.2vmax; width: 60%; color: aliceblue;"><b>Segundo mazo:</b></div>&nbsp
                        <c:choose>
                            <c:when test="${game.secondHalfDeck != null}">
                                <div style="width: 44%; margin-bottom: -2px; display: flex; justify-content: center; align-items: center; font-size: 1vmax;">
                                    <a onclick="removeDeck(2)" href="JavaScript:void(0)">${game.secondHalfDeck.name}</a>
                                    &nbsp<a onclick="removeDeck(2)" href="JavaScript:void(0)"  style="color: red;">x</a>

                                </div>
                            </c:when>
                            <c:otherwise>
                                <a onclick="showPopUp('decks2PopUp')" href="JavaScript:void(0)" 
                                style="color: rgb(100, 75, 143);width: 39%; text-align: center; margin-bottom: -2px;">Elegir</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        
        <!--Lista de jugadores seleccionados-->
        <div class="righ-box">
            <div class="players-tittle-box">
                <div style="margin-right: 3px;"><b>JUGADORES</b></div>
            </div>
            <div class="players-big-box">
                <c:forEach var="player" items="${game.players}" varStatus="counter">
                    <div class="unique-player-selected-box">
                        <div><b>Jugador ${counter.index +1}:&nbsp</b></div>
                        <c:choose>
                            <c:when test="${counter.index == 0}">
                                <a onclick="alert('no te puedes quitar a ti mismo')"
                                    href="JavaScript:void(0)" class="username-text-color">${player.user.name}</a>
                            </c:when>
                            <c:otherwise>
                                <a onclick="removePlayer('${counter.index +1}')" href="JavaScript:void(0)" class="username-text-color">${player.user.name}</a>
                            </c:otherwise>
                        </c:choose>
            
                    </div>
                </c:forEach>
                <div style="margin-left: 20px;">
                    <c:if
                        test="${!(game.players.size()  == 4 || game.mapTemplate!=null && game.players.size() >= game.mapTemplate.startingCityCount(game.players.size()+1))}">
                        <a onclick="showPopUp('usersPopUp')" href="JavaScript:void(0)" class="gameButton" style="margin-top: 1vmax;">Añadir otro jugador</a>
                    </c:if>
                </div>
            </div>
        </div>


            <div class="popup" id="usersPopUp">
                <a onclick="dontShowPopUp('usersPopUp')" class="x">x</a>
                <div class="ls">
                    <c:forEach items="${availableUsers}" var="user">
                        <div class="popup-content-box">
                            <a onClick="addPlayer('${user.username}')" href="JavaScript:void(0)">${user.username}</a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="popup" id="mapsPopUp">
                <a onclick="dontShowPopUp('mapsPopUp')" class="x">x</a>
                <div class="ls">
                    <c:forEach items="${availableMaps}" var="map">
                        <div class="popup-content-box">
                            <a onClick="addMap('${map.id}')" href="JavaScript:void(0)">${map}</a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="popup" id="decks1PopUp">
                <a onclick="dontShowPopUp('decks1PopUp')" class="x">x</a>
                <div class="ls">
                    <c:forEach items="${availableDecks}" var="deck">
                        <div class="popup-content-box">
                            <a onClick="addDeck(1,'${deck.id}')" href="JavaScript:void(0)">${deck.name}</a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="popup" id="decks2PopUp">
                <a onclick="dontShowPopUp('decks2PopUp')" class="x">x</a>
                <div class="ls">
                    <c:forEach items="${availableDecks}" var="deck">
                        <div class="popup-content-box">
                            <a onClick="addDeck(2,'${deck.id}')" href="JavaScript:void(0)">${deck.name}</a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="popup" id="restrictions">
                <a onclick="dontShowPopUp('restrictions')" class="x">x</a>
                <pre class="ls">
                    Para crear la partida necesitas:
                                Nombre de partida    
                                Dos Mazos seleccionados
                                Entre 2 y 4 jugadores
                                Un mapa seleccionado
                                como minimo debe tener una ciudad 
                                inicial por jugador
                                (mientras menos jugadores mas mapas
                                podras seleccionar)
                </pre>
            </div>
    </div>
        
    <c:choose>
        <c:when test="${game.mapTemplate != null && game.name != null && game.players.size() >= 2 && game.firstHalfDeck != null && game.secondHalfDeck != null}">
            <div style="display: flex; justify-content: center; width: 100%; align-items: center;">
                <a onclick="createGame('restrictions')" href="JavaScript:void(0)" class="gameButton"
                    style="margin: 1vmax; background-color:#601484;">INICIAR PARTIDA</a>
            </div>
        </c:when>
        <c:otherwise>
            <div style="display: flex; justify-content: center; width: 100%; align-items: center;">
                <div class="gameButton" style="margin: 1vmax; background-color: gray; ">INICIAR PARTIDA</div>
            </div>
        </c:otherwise>
    </c:choose>
</petclinic:layout>

<script>
    function createGame() {
 
        var form = document.getElementById("game-form");
        form.action="/games/create/confirm";
  
        form.submit();
    }
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

    function addDeck(i,deck) {
        console.log(deck)
        var form = document.getElementById("game-form");
        document.getElementById("selected-deck-"+i).value = deck;
  
        form.submit();
    }
    function removeDeck(i,deck) {
        console.log(deck)
        var form = document.getElementById("game-form");
        document.getElementById("selected-deck-"+i).value = null;
  
        form.submit();
    }
    function addMap(map) {
        console.log(map)
        var form = document.getElementById("game-form");
        document.getElementById("selected-map").value = map;
  
        form.submit();
    }
    function removeMap() {

        var form = document.getElementById("game-form");
        var element = document.getElementById("game-form");
        var child=document.getElementById("selected-map");
        element.removeChild(child);
  
        form.submit();
    }

    function addPlayer(username) {
        console.log(username)
        var form = document.getElementById("game-form");
        var input = document.createElement("input");
                input.type = "hidden";
                input.name = "players[${game.players.size()}]";
                input.value = username;
        document.getElementById("hidden-players").appendChild(input);
  
        form.submit();
    }
    function removePlayer(i) {
        console.log("player-"+i)
        var form = document.getElementById("game-form");
        var element = document.getElementById("hidden-players");
        var child=document.getElementById("player-"+i);
        element.removeChild(child);
  
        form.submit();
    }

</script>
