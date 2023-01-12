<%@ page session="false" trimDirectiveWhitespaces="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<link rel="stylesheet" href="/resources/styles/cardlisting.css">
<link rel="stylesheet" href="/resources/styles/tyrants.css">
<script src="https://d3js.org/d3.v7.min.js" charset="utf-8"></script>
<script src="/js/map.js"></script>
<style>
    .titles{
        background-color: rgb(30, 6, 41);
        width: fit-content;
        color: aliceblue;
        font-size: 1vmax;
        padding: 1vmax;
        margin-bottom: 0.5vmax;
    }
    .map-details{
            width: 50%;
            display: flex;
            justify-content: center;
            height: 100%;
            background-color: aqua;
    }
    .map-box{
            width: 50%;
            display: flex;
            justify-content: center;
            height: 100%;
            background-color: antiquewhite;
    }
    .map-page-container{
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 100%;
        margin: 0;
    }

</style>
<petclinic:layout pageName="map details">
    <style>
    .xd-container{
        margin-top: 0px;
        margin-bottom: 0px;
        padding-left: 0px;
        padding-right: 0px;
    }
    </style>
    <div class="map-page-container">
        <div class="map-details">
            ${map.name}
        </div>
        <div class="map-box">
            <svg id="graph" style="width: 100%; height: 100%;"></svg>
        </div>
        <script>
            //INICIA EL MAPA
            init('${game.id}')
        </script>
        
</div>
</petclinic:layout>