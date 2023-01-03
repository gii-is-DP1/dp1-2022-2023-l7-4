<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="/resources/styles/tyrants.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<body>
    <div class="score-container">
        <div class="podium">
            <c:choose>
                <c:when test="${players.size()==2}">
            <div class="podium-item">
                <div class="podium-player">
                    <c:forEach var="rank" items="${ranking}" begin="1" end="1">
                        <c:out value="${rank.key}"></c:out>
                    </c:forEach>
                </div>
                <div class="podium-rank second">2
                </div>
            </div>
            <div class="podium-item">
                <div class="podium-player">
                    <c:forEach var="rank" items="${ranking}" begin="0" end="0">
                        <c:out value="${rank.key}"></c:out>
                    </c:forEach>
                </div>
                <div class="podium-rank first">1
                </div>
            </div>
            </c:when>
            <c:otherwise>
                <div class="podium-item">
                    <div class="podium-player">
                        <c:forEach var="rank" items="${ranking}" begin="1" end="1">
                            <c:out value="${rank.key}"></c:out>
                        </c:forEach>
                    </div>
                    <div class="podium-rank second">2
                    </div>
                </div>
                <div class="podium-item">
                    <div class="podium-player">
                        <c:forEach var="rank" items="${ranking}" begin="0" end="0">
                            <c:out value="${rank.key}"></c:out>
                        </c:forEach>
                    </div>
                    <div class="podium-rank first">1
                    </div>
                </div>
            <div class="podium-item">
                <div class="podium-player">
                    <c:forEach var="rank" items="${ranking}" begin="2" end="2">
                        <c:out value="${rank.key}"></c:out>
                    </c:forEach>
                </div>
                <div class="podium-rank third">3
                </div>
            </div>
            </c:otherwise>
            </c:choose>

        </div>
        <div class="scoreboard">
            <table class="table table-striped">
            
                <thead>
                    <th>Jugador</th>
                    <th>Puntos por control simple</th>
                    <th>Puntos por control total</th>
                    <th>Puntos por trofeos</th>
                    <th>Puntos en mano</th>
                    <th>Puntos en la pila de descarte</th>
                    <th>Puntos en mazo</th>
                    <th>Puntos por cartas ascendidas</th>
                    <th>Puntos totales</th>
                </thead>
                <tbody>
                    <c:forEach var="rank" items="${ranking}">
                    <tr>
                    <td style="font-weight: bold;"><c:out value="${rank.key}"></c:out></td>
                    <td><c:out value="${rank.value.controlVP}"></c:out></td>
                    <td><c:out value="${rank.value.totalControlVP}"></c:out></td>
                    <td><c:out value="${rank.value.trophyHallVP}"></c:out></td>
                    <td><c:out value="${rank.value.handVP}"></c:out></td>
                    <td><c:out value="${rank.value.dicardPileVP}"></c:out></td>
                    <td><c:out value="${rank.value.deckVP}"></c:out></td>
                    <td><c:out value="${rank.value.innerCircleVP}"></c:out></td>
                    <td><c:out value="${rank.value.totalVp}"></c:out></td>
                    </tr>
                    </c:forEach>
                </tbody>
                
            </table>

        </div>

    </div>

</body>

<style>
    .score-container{
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        
    }
    .scoreboard{
        margin-top: 2em;
        width: 80%;
    }
    .podium{
        display: flex;
        align-items: flex-end;
        margin-top: 2em;
    }
    .podium-player{
        text-align: center;
        margin-bottom: 2em;
        font-size: 2rem;
        color: #ffffff;
    }
    .podium-rank{
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 10rem;
    }
    .podium .second{
        width: 20rem;
        height: 30rem;
        background-color: #bcbab8;
        font-size: 7rem;

    }
    .podium .first{
        width: 20rem;
        height: 45rem;
        background-color: #f9db5c;

    }
    .podium .third{
        width: 20rem;
        height: 15rem;
        background-color: #cd7f32;
        font-size: 4rem;

    }
    body{
        background-image: url(/resources/images/round0-background.jpg);
    }
    td{
        display: table-cell;
        justify-content: center;
        align-items: center;
        text-align: center;
    }
.table-striped > tbody > tr:nth-child(1) {
    background-color: #f9db5c;
 }
 .table-striped > tbody > tr:nth-child(2) {
    background-color: #bcbab8;
 }
 .table-striped > tbody > tr:nth-child(3) {
    background-color: #cd7f32;
 }
 .table-striped > tbody > tr:nth-child(4) {
    background-color: #ffffff;
 }
</style>