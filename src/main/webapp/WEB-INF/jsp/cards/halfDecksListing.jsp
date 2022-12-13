<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<petclinic:layout pageName="halfDecks">
    <link rel="stylesheet" href="/resources/styles/cardlisting.css">
    <link rel="stylesheet" href="/resources/styles/tyrants.css">
    <link rel="stylesheet" href="/resources/styles/scrollbody.css">

    <div class="contenedor"><div class="contenedor-menu"> <h2 class="textmenu">CARTAS Y MAZOS &nbsp|&nbsp MAZOS</h2> </div></div>   


    <form action="/cards/decks/filter">
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

                    <br/>

                    <input type="text" name="page" value="1" hidden>
        
                    <div class="filter-resume-box">
                        <div style="width: 41%">
                            <div class="filter-cardanddeck-text">
                                <b>Filtro aplicado →</b>
                            </div>
                        </div>
                        <div class="filtered-in-box">
                            <div class="filtered-box">
                                Nombre: ${param.name} 
                                <a href="http://localhost:8080/cards/decks/filter?name=" class="x-button2"><b>x</b></a>
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

    <br><br>

    <table id="positionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Descripción</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="halfDeck" items="${halfDecks}" >
            <tr>
                <td>
                    <c:out value="${halfDeck.id}"/>
                </td>
                <td>
                    <c:out value="${halfDeck.name}"/>
                </td>
                <td>
                    <c:out value="${halfDeck.description} "/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>

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


</style>