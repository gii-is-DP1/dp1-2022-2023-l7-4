<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <link rel="stylesheet" href="/resources/styles/cardlisting.css">
    <link rel="stylesheet" href="/resources/styles/scrollbody.css">
    <link rel="stylesheet" href="/resources/styles/tyrants.css">

    

    


<petclinic:layout pageName="cards">
    <div class="contenedor">
        <div class="contenedor-menu">
            <h2 class="textmenu">CARTAS Y MAZOS &nbsp|&nbsp CARTAS</h2>
        </div>
    </div>

   

    <!--<audio autoplay loop src="/resources/rules/demons.mp3"></audio>-->

    <form action="/cards/filter">
        <div class="filter-bigbox">
            <div class="filter-box">
                <div class="filter-method">
                    <div class="filter-tittle">
                        <b>FILTRAR CARTAS</b>
                    </div>
                    <br>
                    <div class="nameanddeck-filter-box">
                        <div class="filter-cardanddeck-text">
                            <label for="lname">Nombre de la carta:&nbsp&nbsp</label>
                        </div>
                        <input style="width:135px; height: 24px;" type="text" id="fname" name="name">
                    </div>
                    <br>

                    <div class="nameanddeck-filter-box">
                        <div class="filter-cardanddeck-text">
                            <b>Selecciona un mazo:&nbsp&nbsp</b>
                        </div>
                        <div>
                            <select name="deck" style="width: 100%;">
                                <option value="" label="">Escoge un mazo</option>
                                <c:forEach var="halfDeck" items="${halfDecks}">
                                    <option value="${halfDeck.name}">${halfDeck.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <br/>

                    <input type="text" name="page" value="1" hidden>
        
                    <div class="filter-resume-box">
                        <div style="width: 41%">
                            <div class="filter-cardanddeck-text">
                                <b>Filtro aplicado ???</b>
                            </div>
                        </div>
                        <div class="filtered-in-box">
                            <div class="filtered-box">
                                Nombre: ${param.name} 
                                <a href="http://localhost:8080/cards/filter?name=&deck=${param.deck}&page=1" class="x-button2"><b>x</b></a>
                            </div>
                            <div class="filtered-box">
                                Mazo: ${param.deck} 
                                <a href="http://localhost:8080/cards/filter?name=${param.name}&deck=&page=1" class="x-button2"><b>x</b></a>
                            </div>
                            <div class="filtered-box">
                                <a href="http://localhost:8080/cards/filter?name=&deck=&page=1" class="x-button"><b>x</b></a>
                            </div>
                        </div>
                    </div>
        
        
                </div>
                <div class="filter-search">
                    <div style="width: 80%; height: 80%;">
                        <button type="submit" class="btn third">
                            <div style="font-size: 0.8vmax"><b>APLICAR</b></div>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <br>

    <c:if test="${notFound}">
        <div class="card-not-found" style="color: aliceblue;">
            <h1>
                ??No se han encontrado cartas! :(
            </h1>
        </div>
        <br>
    </c:if>


    <div class="pagination-box">
        <div style="color: aliceblue;"><b>Te
            encuentras en la p??gina: ${currentPage} / ${numberOfPages}
        </b></div>
        <motero2k:pageNav 
            currentPage="${currentPage}" 
            link="/cards/filter?name=${param.name}&deck=${param.deck}&page=" >
        </motero2k:pageNav>
    </div>


    <br>
    <div class="parent">
    <c:forEach var="card" items="${cards}">
        <div class="child">
            <div class="principalCard">
                <spring:url value="/cards/{cardId}" var="cardUrl">
                    <spring:param name="cardId" value="${card.id}" />
                </spring:url>
                    <img src="/resources/images/cardsModel.png" class="cardImage" onclick="showPopUp('CardPopUp${card.id}')" style="cursor: pointer;">
                    <div class="topTextName">
                        <b>
                            <c:out value="${card.name}" />
                        </b>
                    </div>
                    <div class="topTextCost">
                        <b>
                            <c:out value="${card.cost} " />
                        </b>
                    </div>
                    <div class="topTextAspect">
                        <b>
                            <c:out value="${card.aspect.name} " />
                        </b>
                    </div>
                    <div class="topTextHalfDeck">
                        <b>
                            <c:out value="${card.halfDeck.name} " />
                        </b>
                    </div>
                    <div class="topTextRulesText">
                        <b>
                            <c:out value="${card.rulesText} " />
                        </b>
                    </div>
                    <div class="topTextRarity">
                        <b>
                            <text id="t${card.id}" style="font-size: 180%;">
                                <script>
                                    var rarity = " &#8226 ".repeat(parseInt("${card.rarity}"))
                                    document.getElementById("t${card.id}").innerHTML = rarity
                                </script>
                            </text>
                        </b>
                    </div>
                    <div class="topTextDeckVP">
                        <b>
                            <c:out value="${card.deckVP} " />
                        </b>
                    </div>
                    <div class="topTextInnerCirclePV">
                        <b>
                            <c:out value="${card.innerCirclePV} " />
                        </b>
                    </div>
            </div>
        </div>
    </c:forEach>
    <c:forEach var="card" items="${cards}">
        <div class="popup" id="CardPopUp${card.id}">
            <div class="popup-content">
                <a onclick="dontShowPopUp('CardPopUp${card.id}')" class="x">&times;</a>
                <div class="popup-content-box">
                    <div class="principalCard" style="width: 23vmax; height: 33vmax;">
                        <spring:url value="/cards/{cardId}" var="cardUrl">
                            <spring:param name="cardId" value="${card.id}" />
                        </spring:url>
                        <img src="/resources/images/cardsModel.png" class="cardImage"
                            onclick="showPopUp('CardPopUp${card.id}')">
                        <div class="topTextName">
                            <b>
                                <c:out value="${card.name}" />
                            </b>
                        </div>
                        <div class="topTextCost">
                            <b>
                                <c:out value="${card.cost} " />
                            </b>
                        </div>
                        <div class="topTextAspect">
                            <b>
                                <c:out value="${card.aspect.name} " />
                            </b>
                        </div>
                        <div class="topTextHalfDeck">
                            <b>
                                <c:out value="${card.halfDeck.name} " />
                            </b>
                        </div>
                        <div class="topTextRulesText">
                            <b>
                                <c:out value="${card.rulesText} " />
                            </b>
                        </div>
                        <div class="topTextRarity">
                            <b>
                                <text id="t${card.id}" style="font-size: 180%;">
                                    <script>
                                        var rarity = " &#8226 ".repeat(parseInt("${card.rarity}"))
                                        document.getElementById("t${card.id}").innerHTML = rarity
                                    </script>
                                </text>
                            </b>
                        </div>
                        <div class="topTextDeckVP">
                            <b>
                                <c:out value="${card.deckVP} " />
                            </b>
                        </div>
                        <div class="topTextInnerCirclePV">
                            <b>
                                <c:out value="${card.innerCirclePV} " />
                            </b>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${card.getStory().length()>0}">
                            <div class="story">
                                Historia: ${card.story}
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="story">
                                Esta carta no tiene descripci??n
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:forEach>
    </div>

    


    <div class="pagination-box">
        <motero2k:pageNav 
        currentPage="${currentPage}" 
        link="/cards/filter?name=${param.name}&deck=${param.deck}&page=" >
        </motero2k:pageNav>
        <div style="color: aliceblue;"><b>Te
            encuentras en la p??gina: ${currentPage} / ${numberOfPages}
        </b></div>
    </div>

</body>
</petclinic:layout>
<script>
    function showPopUp(popup) {
       var overlay = document.getElementById(popup);
       console.log(overlay);
       overlay.style.visibility= "visible";
       overlay.style.opacity = 1;
   }

   function dontShowPopUp(popup) {
       var overlay = document.getElementById(popup);
       overlay.style.visibility = "hidden";
       overlay.style.opacity = 0;
   }
</script>
</html>

<style>
    .btn {
        box-sizing: border-box;
        -webkit-appearance: none;
            -moz-appearance: none;
                appearance: none;
        background-color: transparent;
        border: 2px solid #e74c3c;
        border-radius: 0.6em;
        color: #e74c3c;
        cursor: pointer;
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
 
        font-size: 1rem;
        font-weight: 400;
        line-height: 1;
        margin: 20px;
        padding: 1.2em 2.8em;
        text-decoration: none;
        text-align: center;
        text-transform: uppercase;
        font-weight: 700;
        }
    .btn:hover, .btn:focus {
        color: rgb(0, 0, 0);
        outline: 0;
        }

.third {
    width: 80%;
    border-color: #1f323e;
    justify-content: center;
    color: #fff;
    box-shadow: 0 0 40px 40px #9f5967 inset, 0 0 0 0 #9f5967;
    -webkit-transition: all 150ms ease-in-out;
    transition: all 150ms ease-in-out;
    }
    .third:hover {
    box-shadow: 0 0 10px 0 #9f5967 inset, 0 0 10px 4px #9f5967;
    }

.popup{
    position: fixed; /* Stay in place */
    left: 0;
    visibility: hidden;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */

}
    
.popup-content {
    background-color: rgba(242, 231, 245, 0.5);
    height: 80%;
    width: 85%;
    overflow: auto;
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
    color: rgb(255, 0, 0);
    user-select: none;
    width: 40px;
    height: 40px;
    text-decoration: none;
    cursor: pointer;
    position: absolute;
    align-self: flex-start;
    margin-left: 10px;
}

.popup-content-box{
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 3px;
}
.story{
    border-radius: 1.5vmax;
    padding: 0.3vmax;
    border: 0.3vmax solid #000000;
    background-color:rgba(16, 64, 112, 0.814);
    margin-left: 2em;
    width: 30vmax;
    font-size: 1.5vmax;
    text-align: center;
    text-justify: auto;
    color: aliceblue;
}

</style>
