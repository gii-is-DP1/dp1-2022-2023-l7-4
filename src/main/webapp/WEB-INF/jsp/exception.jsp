<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">
    <style>
        .super{
            background-color: rgb(0, 0, 0);
            width: 100%;
            height: 95%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        h2{
            color:blueviolet;
            padding: 1vmax;
        }
    </style>
    <div class="super">

        <spring:url value="/resources/images/pewpew.jpg" var="petsImage"/>
        <img src="${petsImage}"/>
        
        <h2>Vaya, algo ha pasado...</h2>
        <h2>Esto no es muy taki taki rumba de nuestra parte, puedes volver al <a href="/">inicio</a> si quieres...</h2>
        
        <p>${exception.message}</p>
    </div>

</petclinic:layout>
