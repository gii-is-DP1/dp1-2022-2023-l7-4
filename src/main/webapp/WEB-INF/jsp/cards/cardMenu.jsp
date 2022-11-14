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
    <div class="contenedor"><div class="contenedor-menu"> <h2 class="textmenu">Menú de cartas y mazos</h2> </div></div>   
    <div class="wrapper">
    <form:form modelAttribute="card" action="/cards/all" method="get" class="box a"
               id="search-owner-form">
                <div class="aBotton ">
                        <button type="submit" class="btn btn-two"><div class="textbtn">Cartas</div></button>
                </div>
    </form:form>

    <form:form modelAttribute="card" action="/cards/decks" method="get" class="box b"
               id="search-owner-form">
               <div class="aBotton">
                    <button type="submit" class="btn btn-two"><div class="textbtn">Mazos</div></button>
                 </div>
    </form:form>

    <form:form modelAttribute="card" action="/cards/all" method="get" class="box c"
               id="search-owner-form">
               <div class="aBotton">
                    <button type="submit" class="btn btn-two"><div class="textbtn">Crear carta</div></button>
                 </div>
    </form:form>

    <form:form modelAttribute="card" action="/cards/decks" method="get" class="box d"
    id="search-owner-form">
    <div class="aBotton">
         <button type="submit" class="btn btn-two"><div class="textbtn">Crear mazo</div></button>
      </div>
</form:form>
</div>
	<style>
        .textmenu{
            color: aliceblue;
            margin-top: 2px;
        }
        .contenedor {
            background-color: rgb(25, 24, 24);
            margin-left: -14.5%;
            margin-right: -14.5%;
            margin-top: -40px;
            margin-bottom: 2%;

        }
        .contenedor-menu {
            display: flex;
            justify-content: space-around;
            height: 30px;
            margin: 0 auto;
        }

        .wrapper {
            display: grid;
            grid-gap: 10px;
            grid-template-columns: repeat(4, [col] auto ) ;
            grid-template-rows: repeat(3, [row] 270%  );
            color: #444;
        }

        .box {
            background-color: #444;
            border-radius: 7px;
            padding: 20px;
            font-size: 150%;

        }

        .a {
            grid-column: col / span 2;
            grid-row: row ;
            background-image: url(/resources/images/cards_menu1.jpg);
            background-position: center;
            background-size:cover;
            text-align: center;
            position: relative;
        }
        .aBotton {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
                }
        .b {
            grid-column: col 3 / span 2;
            grid-row: row ;
            background-image: url(/resources/images/cards_menu2.jpg);
            background-position: center;
            background-size:cover;
            text-align: center;
            position: relative;            
        }

        .c {
            grid-column: col  / span 2;
            grid-row: row 2;
            background-image: url(/resources/images/cards_menu3.jpg);
            background-position: center;
            background-size:cover;
            text-align: center;
            position: relative;            
        }

        .d {
            grid-column: col 3 / span 2;
            grid-row: row 2;
            background-image: url(/resources/images/cards_menu5.jpg);
            background-position:center;
            background-size:cover;
            text-align: center;
            position: relative;            
        }

        .textbtn {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            opacity: 100%ç;
        }
        @import 'https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300';

        .body {
        width: 100%;
        height: 100%;
        overflow: hidden;
        margin: 0;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        font-family: 'Open Sans Condensed', sans-serif;
        }

        .box-2 { background-color: #282121; }

        .btn {
            opacity: 90%;
            line-height: 50px;
            height: 50px;
            text-align: center;
            width: 250px;
            cursor: pointer;
        }

        /*Botton*/
        .btn-two {
            color: rgb(197, 172, 63);
            transition: all 0.5s;
            position: relative;	
            background-color: #282121;
            font-size: 20px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            border-radius: 10%;
        }
        .btn-two span {
            z-index: 2;	
            display: block;
            position: absolute;
            width: 100%;
            height: 100%;
            border-radius: 10%;
            
        }
        .btn-two::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 1;
            transition: all 0.5s;
            border: 1px solid rgba(255, 255, 255, 0.086);
            background-color: rgba(255, 255, 255, 0);
            border-radius: 10%;

        }
        .btn-two::after {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 1;
            transition: all 0.5s;
            border: 1px solid rgba(202, 189, 189, 0.123);
            background-color: rgba(200, 177, 177, 0.122);
            border-radius: 10%;

        }
        .btn-two:hover::before {
        transform: rotate(-45deg);
        background-color: rgba(255, 255, 255, 0.105);
        border-radius: 10%;

        }
        .btn-two:hover::after {
        transform: rotate(45deg);
        background-color: rgba(255, 255, 255, 0.105);
        border-radius: 10%;

        }

    </style>
</petclinic:layout>