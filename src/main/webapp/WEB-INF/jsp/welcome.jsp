<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
<div class="mainmenu"><h1>Bienvenido ${username}</h1>
    <a href="/games/list">Jugar</a>
    <div class="botmenu">
        <a href="">Mapas</a>
        <a href="">Reglas</a>
        <a href="/cards/all">Cartas</a>
    </div>
</div>
</petclinic:layout>
<style>
    .mainmenu{
        margin: auto;
        width: 60%;
        height: 60%;
        background-image: url(/resources/images/home_back.png);
        background-position:0px -650px;
        background-size:cover;
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
    .mainmenu h1{
        margin: 5%;
        color: white;
        font-size: xx-large;

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
        border-width: 3px;
        border-style: solid;
        border-color: black;
        background: rgb(95,75,139);
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
        background: black;
        margin-bottom: 50px;
    }
</style>