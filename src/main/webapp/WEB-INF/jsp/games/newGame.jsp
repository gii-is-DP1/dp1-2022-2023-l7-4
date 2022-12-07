<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName = "newGame">
    <form:form modelAttribute="GameForm" id="GameForm" action="/games/create">
        <c:forEach items="${players}" var="player" varStatus="status">
				<input type="hidden" name="users[${status.index}]" value="${player.username}"/>
		</c:forEach>
        <div class="form-container">
            <div class="player" id="player1">
                <span class="glyphicon glyphicon-user"></span>
				<p class="playerName">${players[0].username}</p>

            </div>
            <div class="player" id="player2">
                <c:choose>
                    <c:when test="${players[1] != null}">
                        <img class="playerImg" src="/resources/images/azul.png" />


                        <a onClick="removePlayer(1)" href="JavaScript:void(0)" class="playerName">${players[1].username}</a>
                    </c:when>
                    <c:otherwise>
                        <img class="playerImg" src="/resources/images/add.png" />

                        <a onclick="showPopUp()" href="JavaScript:void(0)"
                            class="playerName">Add player</a>
                    </c:otherwise>
                </c:choose>
            </div>
            
            
            <div class="player" id="player3">
                <c:choose>
                    <c:when test="${players[2] != null}">
                        <img class="playerImg" src="/resources/images/rojo.png" />

                        <a onClick="removePlayer(2)" href="JavaScript:void(0)" class="playerName">${players[2].username}</a>
                    </c:when>
                    <c:otherwise>
                        <img class="playerImg" src="/resources/images/add.png" />

                        <a onclick="showPopUp()" href="JavaScript:void(0)"
                            class="playerName">Add player</a>
                    </c:otherwise>
                </c:choose>
            </div>

            
            <div class="player" id="player4">
                <c:choose>
                    <c:when test="${players[3] != null}">
                        <img class="playerImg" src="/resources/images/naranja.png" />

                        <a onClick="removePlayer(3)" href="JavaScript:void(0)" class="playerName">${players[3].username}</a>
                    </c:when>
                    <c:otherwise>
                        <img class="playerImg" src="/resources/images/add.png" />

                        <a onclick="showPopUp()" href="JavaScript:void(0)"
                            class="playerName">Add player</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="popup-players" id="popup">
            <div class="addplayer-popup">
                <c:forEach items="${users}" var="user" varStatus="status">
					<div class="userToAdd">
						<a onClick="addNewPlayer(${user.username})" href="JavaScript:void(0)" class="playerName">${user.username}</a>
					</div>
				</c:forEach>
            </div>
        </div>
    </form:form>
    
    <div class="bottomButtons">
        <button onClick="gameSubmit()" type="submit" class="btn btn-default">Create game</button>
    </div>

    <script>
        function addNewPlayer(index) {
            var url = "/games/create/add/" + index;
            
            var form = document.getElementById("GameForm");
            form.action = url;
            form.submit();
        }
    </script>
    
    <script>
        function removePlayer(index) {
            var url = "/games/create/remove/" + index;
            
            var form = document.getElementById("GameForm");
            form.action = url;
            form.submit();
        }
    </script>
    
    <script>
        function showPopUp() {
            var overlay = document.getElementById("popup");
            overlay.style.display = "block";
            
            return false;
        }
    </script>
    
    <script>
        function gameSubmit(){
            var form = document.getElementById("GameForm");
            form.submit();
        }
    </script>

    <style>
        .popup-players {
        width: 50%;
        height: 200px;
        justify-content: center;
        position: fixed;
        display: none; /* Hidden by default */
        background-color: rgb(255, 0, 0); /* Fallback color */
        background-color: rgba(255, 0, 0, 0.4); /* Black w/ opacity */
        }
    </style>

</petclinic:layout>