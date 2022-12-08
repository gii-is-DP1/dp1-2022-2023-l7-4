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


<petclinic:layout pageName="home">
    <link rel="stylesheet" href="/styles/menu.css">

    <div class="wrapper">
    <form:form modelAttribute="card" action="/games/list" method="get" class="box a"
               id="search-owner-form">
                <div class="aBotton ">
                        <button type="submit" class="special-btn btn-two size-btn1"><div class="textbtn">Jugar</div></button>
                </div>
    </form:form>

    <form:form modelAttribute="card" action="/games/list" method="get" class="box b"
               id="search-owner-form">
                <div class="aBotton">
                    <button type="submit" class="special-btn btn-two size-btn1"><div class="textbtn">Reglas</div></button>
                </div>
    </form:form>

    <form:form modelAttribute="card" action="/cards/menu" method="get" class="box c"
               id="search-owner-form">
                <div class="aBotton">
                    <button type="submit" class="special-btn btn-two size-btn1"><div class="textbtn">Cartas y mazos</div></button>
                </div>
    </form:form>

    <form:form modelAttribute="card" action="/cards/menu" method="get" class="box d"
    id="search-owner-form">
    <div class="aBotton">
         <button type="submit" class="special-btn btn-two size-btn1"><div class="textbtn">Mapas</div></button>
      </div>
</form:form>
</div>
</petclinic:layout>