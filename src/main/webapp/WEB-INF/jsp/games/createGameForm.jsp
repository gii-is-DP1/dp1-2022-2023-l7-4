<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="games">
    


    <div style="background-color: aliceblue; padding: 20px;">
        <h2>Nuevo juego</h2>
        <a onclick="showPopUp('restrictions')" href="JavaScript:void(0)" class="gameButton">Mostrar normas de creacion</a>
        <br>
        <form:form modelAttribute="game" class="form-horizontal" id="game-form" method="post" action="/games/create/plus">
            <label class="control-label" style="margin-bottom: 0; margin-left: 0;">Nombre del juego:</label>
        <input type="text" name="name" value="${game.name}" style="margin: 10px;" >
        
        <input id="selected-map" type="hidden" name="mapTemplate" value="${game.mapTemplate.id}">
        <input id="selected-deck-1" type="hidden" name="firstHalfDeck" value="${game.firstHalfDeck.id}">
        <input id="selected-deck-2" type="hidden" name="secondHalfDeck" value="${game.secondHalfDeck.id}">
        <div id="hidden-players">
            <c:forEach var="player" items="${game.players}" varStatus="counter">
                <input id="player-${counter.index+1}" type="hidden" name="players[${counter.index}]" value="${player.user.username}">
            </c:forEach>
        </div>
    </form:form>



            <div>
                <label class="control-label" style="margin-bottom: 0; margin-left: 0;">Primer mazo:</label>
                <c:choose>
                    <c:when test="${game.firstHalfDeck != null}">
                        <a onclick="removeDeck(1)" href="JavaScript:void(0)">${game.firstHalfDeck.name}</a>
                    </c:when>
                    <c:otherwise>
                        <a onclick="showPopUp('decks1PopUp')" href="JavaScript:void(0)" class="gameButton">Elegir</a>
                    </c:otherwise>
                </c:choose>
            </div>

            <div>

                <label class="control-label" style="margin-bottom: 0; margin-left: 0;">Segundo mazo:</label>
                <c:choose>
                    <c:when test="${game.secondHalfDeck != null}">
                        <a onclick="removeDeck(2)" href="JavaScript:void(0)">${game.secondHalfDeck.name}</a>
                    </c:when>
                    <c:otherwise>
                        <a onclick="showPopUp('decks2PopUp')" href="JavaScript:void(0)" class="gameButton">Elegir</a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div>
            <label class="control-label" style="margin-bottom: 0; margin-left: 0;">Players:</label>
            <c:forEach var="player" items="${game.players}" varStatus="counter">
                <div style="margin-left: 20px;">
                    <label class="control-label" style="margin-bottom: 0; margin-left: 0;">Player ${counter.index +1}:</label>

                        <c:choose>
                            <c:when test="${counter.index == 0}">
                                <a onclick="alert('no te puedes quitar a ti mismo')" href="JavaScript:void(0)" >${player.user.name}</a>
                            </c:when>
                        <c:otherwise>
                            <a onclick="removePlayer('${counter.index +1}')" href="JavaScript:void(0)" >${player.user.name}</a>
                        </c:otherwise>
                        </c:choose>  
                
                </div>
            </c:forEach>
                <div style="margin-left: 20px;">
                    <c:if test="${!(game.players.size()  == 4 || game.mapTemplate!=null && game.players.size() >= game.mapTemplate.startingCityCount(game.players.size()+1))}">
                        <a onclick="showPopUp('usersPopUp')" href="JavaScript:void(0)" class="gameButton">Elegir otro jugador</a>
                    </c:if>
                </div>
            </div>

            <div>
                <label class="control-label" style="margin-bottom: 0; margin-left: 0;">Mapa:</label>
                <c:choose>
                    <c:when test="${game.mapTemplate != null}">
                        <a onclick="removeMap()" href="JavaScript:void(0)">${game.mapTemplate}</a>
                    </c:when>
                    <c:otherwise>
                        <a onclick="showPopUp('mapsPopUp')" href="JavaScript:void(0)" class="gameButton">Elegir un mapa</a>
                    </c:otherwise>
                </c:choose>
            </div>
            
            
            <c:choose>
                    <c:when test="${game.mapTemplate != null && game.name != null && game.players.size() >= 2 && game.firstHalfDeck != null && game.secondHalfDeck != null}">

                        <a onclick="createGame('restrictions')" href="JavaScript:void(0)" class="gameButton" style="margin: 25px; background-color: green">Crear Juego</a>
                    </c:when>
                    <c:otherwise>
                       
                    </c:otherwise>
                </c:choose>





            
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
                <pre class="ls">Para crear la partida necesitas:
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
    
</petclinic:layout>

<style>
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
            border: 3px;
            border-radius: 10px;
            color: white;
            padding: 5px 15px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            margin: 4px 2px;
            cursor: pointer;
        }
        .gameError {
            border: 3px;
            border-radius: 5px;
            border-color: red;
        }
        a {
            color : green;
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
