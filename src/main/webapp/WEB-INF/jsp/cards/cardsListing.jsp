<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<petclinic:layout pageName="cards">
    <h2>Cards</h2>
    <script>
        console.log("ssss")
    </script>
    
    <table id="positionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>id</th>
            <th>Carta</th>
            <th>Coste</th>
            <th>Historia</th>
            <th>Acción de la carta</th>
            <th>PV</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cards}" var="card">
            <tr>
                <td>
                    <c:out value="${card.id}"/>
                </td>
                <td>
                    <c:out value="${card.name} "/>
                </td>
                <td>
                    <c:out value="${card.cost} "/>
                </td>
                <td>
                    <c:out value="${card.story} "/>
                </td>
                <td>
                    <c:out value="${card.rulesText} "/>
                </td>
                <td>
                    <c:out value="${card.deckVP} "/>
                </td>   
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <body>
        
    <style>
        .btn-pos-1{
            background-color: aqua;
            border: 8px;
            text-align: center;
            position: absolute;
            border-radius: 50%;
            top:0px;
        }
        .btn-pos-2{
            background-color: aqua;
            border: 8px;
            text-align: center;
            position: absolute;
            border-radius: 50%;
            top:30px;
        }
        .btn-pos-3{
            background-color: aqua;
            border: 8px;
            text-align: center;
            position: absolute;
            border-radius: 50%;
            top:60px;
        }
        .board{
            background-image: url("/resources/images/example_map.jpg");
            background-size: contain;
            background-repeat: no-repeat;
            height: 600px;
            width: 600px;
            position:relative;
        }
    </style>
    </body>
</petclinic:layout>