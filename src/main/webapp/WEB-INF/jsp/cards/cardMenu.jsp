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

    <h2 class="menu">Menú de cartas</h2>
    <div class="grid-container">
    <form:form modelAttribute="card" action="/cards/all" method="get" class="form-horizontal"
               id="search-owner-form">
        <div class="form-group">
            <div class="col-sm-offset-1 col-sm-10">
                <button type="submit" class="btn btn-default">Mostrar todas las cartas</button>
                <!-- <form:input class="form-control" path="name" size="30" maxlength="80"  style="visibility:hidden"/> -->
                <!-- ya no hace falta, hay un redirect -->
            </div>
        </div>
    </form:form>

    <form:form modelAttribute="card" action="/cards/decks" method="get" class="form-horizontal"
               id="search-owner-form">
        <div class="form-group">
            <div class="col-sm-offset-5 col-sm-5">
                <button type="submit" class="btn btn-default">Buscar cartas por mazo</button>
            </div>
        </div>
    </form:form>
</div>
	<style>
        .grid-container {
            display: grid;
            grid-template-rows: 0px 100px;
            grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr;
            grid-gap: 0px;
        }
        .menu{
            margin-left: 25px;
        }
    </style>
</petclinic:layout>


