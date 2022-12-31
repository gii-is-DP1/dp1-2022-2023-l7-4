<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
<div class="mainmenu">
    <img src="/resources/images/logo-menu.png" alt="">
    <a href="/games/list">Jugar</a>
    <div class="botmenu">
        <a href="" >Mapas</a>
        <a href="/resources/rules/tyrants-of-the-underdark-reglas-es.pdf" target="_blank">Reglas</a>
        <a href="/cards/all">Cartas</a>
    </div>
</div>
</petclinic:layout>
<style>
    .mainmenu{
        margin: auto;
        width: 60%;
        height: 60%;
        background-image: linear-gradient(rgba(255, 255, 255, 0.1), rgba(255, 255, 255, 0.1)), url(/resources/images/welcome.jpg);;
        background-size: cover;
        background-repeat: no-repeat;
        -webkit-box-shadow: 0px 27px 20px -20px rgba(0,0,0,0.51);
        -moz-box-shadow: 0px 27px 20px -20px rgba(0,0,0,0.51);
        box-shadow: 0px 27px 20px -20px rgba(0,0,0,0.51);
        border-radius: 3rem;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }
    .mainmenu img{
        margin: 5%;

    }
    .mainmenu a{
        color: white;
        margin: auto;
        margin-bottom: 50px;
        width: 250px;
        height: 100px;
        display: flex;
        font-size: 5rem;
        justify-content: center;
        align-items: center;
        text-decoration: none;
        border-radius: 10px;
        border-width: 2px;
        border-style: solid;
        border-color: black;
        background: rgb(95,75,139);
        transition-duration: 0.5s;

    }
    .mainmenu a:hover{
        color: rgba(95,75,139,1);
        margin: auto;
        margin-bottom: 50px;
        width: 250px;
        height: 100px;
        display: flex;
        font-size: 5rem;
        justify-content: center;
        align-items: center;
        text-decoration: none;
        border-radius: 10px;
        border-width: 2px;
        border-style: solid;
        border-color: rgb(0, 0, 0);
        background: rgb(255, 255, 255);
        transition-duration: 0.5s;

    }
    .botmenu{
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: row;

    }
    .botmenu a{
        margin: auto;
        font-size: 2rem;
        width: 150px;
        height: 50px;
        border: 1.5px solid rgba(95,75,139,1); 
        background: rgb(0,0,0);
        background: linear-gradient(0deg, rgba(0,0,0,1) 0%, rgba(49,39,72,1) 50%, rgba(95,75,139,1) 100%);
        margin-bottom: 50px;
    }
    .botmenu a:hover{
        margin: auto;
        font-size: 2rem;
        width: 150px;
        height: 50px;
        color: black;
        border: 0.2vmax solid rgba(95,75,139,1); 
        background: rgb(245, 245, 245);
        background: linear-gradient(0deg, rgb(242, 242, 242) 0%, rgb(179, 175, 189) 50%, rgb(158, 129, 220) 100%);
        transition-duration: 0.5s;
        margin-bottom: 50px;
    }
</style>