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

<body>
    <div class="fullscreen-game">  
        <div class="tophud">
            <div class="tophud-flex">
                <div class="player-div">
                    <text class="player-text">JUGADOR 1</text>
                </div>
            </div>
            <div class="tophud-flex">
                <div class="resume-div">
                    <text class="resume-text">Realiza una acción disponible</text>
                </div>
            </div>
            <div class="tophud-flex">
                <div class="round-div">
                    <text class="round-text">RONDA 1</text>
                </div>
            </div>
        </div>   

        <div class="backColor">
            <div class="resource-layout">

            </div>
        </div>
    </div>      
</body>

	<style>
        body{
            margin : 0;
            padding : 0;
        }
        .fullscreen-game{
            height: 100%;
            width: 100%;
        }
        .backColor{
            width: 100%;
            height: 94.5%;
            background-color: blueviolet;
        }
        .tophud {
            background-color: rgb(25, 24, 24);
            width: 100%;
            height: 5%;
            border-color: darkgoldenrod;
            border-right-width: 0px;
            border-bottom-width: 4px;
            border-left-width: 0px;
            border-top-width: 0px;
            border-style:double;
        }
        .tophud-flex {
            display:inline-flex;
            width: 33%;
            height: 100%;
        }
        .player-div{
            margin-left: 2%;
            margin-top: 0.5%;
        }
        .player-text{
            color: aliceblue;
            font-size: 150%;
        }
        .resume-div{
            display: flex;
            width: 100%;
            height: 55%;
            justify-content: space-around;
            background-color: aliceblue;
            border-color: darkgoldenrod;
            border-width:3px;
            border-style: solid;
        }
        .resume-text{
            font-size: 115%;
        }
        .round-div{
            justify-content: right;
            display: flex;
            width: 100%;
        }
        .round-text{
            font-size: 150%;
            color: aliceblue;
        }
        .resource-layout{
            width: 4%;
            height: 100%;
            background-color:rgb(25, 24, 24);
            border-color: darkgoldenrod;
            border-right-width: 4px;
            border-bottom-width: 0px;
            border-left-width: 0px;
            border-top-width: 0px;
            border-style:double;
        }
    </style>
