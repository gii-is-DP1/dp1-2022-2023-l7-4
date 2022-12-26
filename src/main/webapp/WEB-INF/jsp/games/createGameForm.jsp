<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<petclinic:layout pageName="games">
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
                        <input type="text" name="name" value="${game.name}" style="width: 9vmax;" >
                        
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
                                <div style="width: 49%; margin-bottom: -2px; display: flex; justify-content: center; align-items: center;">
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
                                <div style="width: 44%; margin-bottom: -2px; display: flex; justify-content: center; align-items: center;">
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
        <div>
            <label class="control-label" style="margin-bottom: 0; margin-left: 0;">Players:</label>
            <c:forEach var="player" items="${game.players}" varStatus="counter">
                <div style="margin-left: 20px;">
                    <label class="control-label" style="margin-bottom: 0; margin-left: 0;">Player ${counter.index +1}:</label>
        
                    <c:choose>
                        <c:when test="${counter.index == 0}">
                            <a onclick="alert('no te puedes quitar a ti mismo')"
                                href="JavaScript:void(0)">${player.user.name}</a>
                        </c:when>
                        <c:otherwise>
                            <a onclick="removePlayer('${counter.index +1}')" href="JavaScript:void(0)">${player.user.name}</a>
                        </c:otherwise>
                    </c:choose>
        
                </div>
            </c:forEach>
            <div style="margin-left: 20px;">
                <c:if
                    test="${!(game.players.size()  == 4 || game.mapTemplate!=null && game.players.size() >= game.mapTemplate.startingCityCount(game.players.size()+1))}">
                    <a onclick="showPopUp('usersPopUp')" href="JavaScript:void(0)" class="gameButton">Elegir otro jugador</a>
                </c:if>
            </div>
        </div>

            <div class="popup" id="usersPopUp">
                <a onclick="dontShowPopUp('usersPopUp')" class="x">x</a>
                <div class="ls">
                    <c:forEach items="${availableUsers}" var="user">
                        <a onClick="addPlayer('${user.username}')" href="JavaScript:void(0)">${user.username}</a>
                    </c:forEach>
                </div>
            </div>
            <div class="popup" id="mapsPopUp">
                <a onclick="dontShowPopUp('mapsPopUp')" class="x">x</a>
                <div class="ls">
                    <c:forEach items="${availableMaps}" var="map">
                        <a onClick="addMap('${map.id}')" href="JavaScript:void(0)">${map}</a>
                    </c:forEach>
                </div>
            </div>
            <div class="popup" id="decks1PopUp">
                <a onclick="dontShowPopUp('decks1PopUp')" class="x">x</a>
                <div class="ls">
                    <c:forEach items="${availableDecks}" var="deck">
                        <a onClick="addDeck(1,'${deck.id}')" href="JavaScript:void(0)">${deck.name}</a>
                    </c:forEach>
                </div>
            </div>
            <div class="popup" id="decks2PopUp">
                <a onclick="dontShowPopUp('decksPopUp')" class="x">x</a>
                <div class="ls">
                    <c:forEach items="${availableDecks}" var="deck">
                        <a onClick="addDeck(2,'${deck.id}')" href="JavaScript:void(0)">${deck.name}</a>
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

<style>
    .create-box{
        background-image: url(/resources/images/round0-background.jpg);
        background-position: center;
        background-size:cover;        
        display: flex;
        flex-direction: row;
        border-radius: 1vmax;
        height: 100%;
        width: 100%;
    }
    .create-box .left-box{
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        width: 55%;
        height: 100%;
    }
    .left-box .new-game-and-rules-box{
        display: flex;
        flex-direction: row;
        height: 33%;
        width: 100%;
        align-items: center;
        justify-content: space-between;
    }
    .left-box .new-game-box{
        display: flex;
        background-color: #601484;
        width: 60%;
        height: 100%;
        align-items: center;
        justify-content: center;
        margin-top: 1vmax;
        margin-left: 1vmax;
        margin-right: 1vmax;
        border-radius: 0.5vmax;

    }
    .left-box .new-game-text{
        padding: 0.5vmax;
        display: flex;
        font-size: 1.8vmax;
        color: aliceblue;
        text-align: center;
    }
    .left-box .rules-box{
        background-color: aliceblue;
        border-radius: 1vmax;
        padding: 0.4vmax 0.9vmax;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        cursor: pointer;
        font-size: 1vmax;
        width: 39%;
        margin-top: 1vmax;
        margin-right: 1vmax;
    }
    .rules-box .vio{
        color:#601484;
        margin-bottom: 0px;
    }
    .left-box .new-name-and-map-box{
        display: flex;
        flex-direction: row;
        height: 33%;
        width: 100%;
        align-items: center;
        justify-content: space-between;
    }
    .left-box .new-name-box{
        display: flex;
        background-color: #601484;
        width: 58%;
        height: 100%;
        align-items: center;
        justify-content: center;
        margin: 1vmax;
        border-radius: 0.5vmax;
    }
    .left-box.left-box .new-name-text{
        padding: 0.5vmax;
        display: flex;
        font-size: 1vmax;
        color: aliceblue;
        text-align: center;
        align-items: center;
        justify-content: center;
        padding: 1.25vmax;
    }
    .new-name-text .name{
        font-size: 1.2vmax;
        margin-bottom: 0%;
    }
    .left-box .new-map-box{
        display: flex;
        background-color: #601484;
        width: 39%;
        height: 100%;
        align-items: center;
        justify-content: center;
        border-radius: 0.5vmax;
        margin-right: 1vmax;
        margin-top: 1vmax;
        margin-bottom: 1vmax;

    }
    .left-box .new-map-text{
        display: flex;
        font-size: 1vmax;
        color: aliceblue;
        text-align: center;
        align-items: center;
        justify-content: center;
        padding: 1.2vmax;
    }



    .left-box .full-deck-box{
        background-color: #601484;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 33%;
        width: 95%;
        border-radius: 0.5vmax;
        margin-right: 1vmax;
        margin-left: 1vmax;
        margin-bottom: 1vmax;
    }
    .left-box .first-second-deck-box{
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        width: 100%;
        padding-left: 1vmax;
        padding-bottom: 1vmax;
        padding-right: 1vmax;
    }
    .left-box .deck-title{
        padding: 1vmax;
        color: aliceblue;
        font-size: 1.5vmax;
    }

    .left-box .deck-box{
        width: 48%;
        background-color: rgb(175, 121, 190);
        padding: 1.2vmax;
        border-radius: 0.5vmax;
        display: flex;
        align-items: center;
        justify-content: center;
    }








    .popup {
        background-color: rgba(231, 238, 245,0.5);
        height: 80%;
        width: 85%;
        overflow-y: scroll;
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
    .popup .x {
        color: red;
        user-select: none;
        width: 40px;
        height: 40px;
        position: absolute;
        align-self: flex-start;
    }
    .popup .ls{
        display: flex;
        flex-direction: column;
        width: 100%;
        align-items: center;
        justify-content: center;
    }

        .gameButton {
            background-color: #601484;
            border-radius: 1vmax;
            color: white;
            padding: 0.4vmax 0.9vmax;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            cursor: pointer;
            font-size: 1vmax;
        }
        .gameError {
            border: 3px;
            border-radius: 5px;
            border-color: red;
        }
        a {
            color : aliceblue;
        }
    
</style>


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
