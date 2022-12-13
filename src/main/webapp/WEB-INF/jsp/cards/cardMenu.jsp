<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<petclinic:layout pageName="cards">
    <link rel="stylesheet" href="/resources/styles/tyrants.css">
    <link rel="stylesheet" href="/resources/styles/scrollbody.css">


    <div class="contenedor"><div class="contenedor-menu"> <h2 class="textmenu">CARTAS Y MAZO</h2> </div></div>   
    <div class="wrapper">
    <form:form modelAttribute="card" action="/cards/all" method="get" class="box a"
               id="search-owner-form">
                <div class="aBotton ">
                        <button type="submit" class="special-btn btn-two size-btn2"><div class="textbtn">Cartas</div></button>
                </div>
    </form:form>

    <form:form modelAttribute="card" action="/cards/decks" method="get" class="box b"
               id="search-owner-form">
                <div class="aBotton">
                    <button type="submit" class="special-btn btn-two size-btn2"><div class="textbtn">Mazos</div></button>
                </div>
    </form:form>

    <form:form modelAttribute="card" action="/cards/all" method="get" class="box c"
               id="search-owner-form">
                <div class="aBotton">
                    <button type="submit" class="special-btn btn-two size-btn2"><div class="textbtn">Crear carta</div></button>
                </div>
    </form:form>

    <form:form modelAttribute="card" action="/cards/decks" method="get" class="box d"
    id="search-owner-form">
    <div class="aBotton">
         <button type="submit" class="special-btn btn-two size-btn2"><div class="textbtn">Crear mazo</div></button>
      </div>
</form:form>
</div>
</petclinic:layout>