<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="game">
    <h2>${text}</h2>
    
    <link rel="stylesheet" href="/resources/styles/tyrants.css">
    <form action="/games/${gameId}/process-mode">
        <div class="filter-bigbox">
            <div class="filter-box">
                <div class="filter-method">
                    <div class="filter-tittle">
                        <b>MODO DE COLOCACIÓN DE TROPAS BLANCAS</b>
                    </div>
                    <br>
                    <div class="nameanddeck-filter-box">
                        <div class="filter-cardanddeck-text">
                            <b>Escoge un modo:&nbsp&nbsp</b>
                        </div>
                        <div>
                            <select name="mode" style="width: 100%;">
                                <option value="" label="">Escoge un modo</option>
                                <c:forEach var="mode" items="${modes}">
                                    <option value="${mode}">${mode}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
        
        
                </div>
                <div class="filter-search">
                    <div style="width: 80%; height: 80%;">
                        <button type="submit" class="btnx third">
                            <div style="font-size: 0.8vmax"><b>APLICAR</b></div>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <br/> 
   

    
</petclinic:layout>
<style>
    #redball{
        background-color: red;
        width: 10px;
        height: 10px;
        box-shadow: 0px 0px 10px darkred;
        border-radius: 50%;
    }
    #greenball{
        background-color: green;
        width: 10px;
        height: 10px;
        box-shadow: 0px 0px 10px darkgreen;
        border-radius: 50%;
    }
</style>