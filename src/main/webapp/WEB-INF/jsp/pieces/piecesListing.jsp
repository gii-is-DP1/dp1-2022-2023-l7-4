<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="pieces">
    <h2>PIECES</h2>
    <head>
        <style>
            .container{
                justify-content: center;
            }
            .pre-button{
                background-color: blueviolet;
                color:aliceblue;
                text-align: center;
                border-radius: 20%;
                margin-left:30px
            }
        </style>
    </head>
    <table id="piecesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Type</th>
            <th>Position</th>
            <th>Player</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pieces}" var="piece">
            <tr>
                <td>
                    <c:out value="${piece.id}"/>
                </td>
                <td>
                    <c:out value="${piece.pieceType.name}"/>
                </td>
                <td>
                    <c:out value="${piece.position}"/>
                </td>
                <td>
                    <c:out value="${piece.player_id}"/>
                </td>
                <td>
                    <a href='<c:url value="/pieces/${piece.id}/droop"/>' >
                    <div class="pre-button">
                        <c:choose>
                            <c:when test="${piece.pieceType.id==2}">
                                COLOCAR ESPIA
                            </c:when>
                            <c:otherwise>
                                DESPLEGAR TROPA
                            </c:otherwise>
                        </c:choose>
                    </div>
					</a>
                </td>
                 
            </tr>
        </c:forEach>
        </tbody>
    </table>
            <div class="container">
                <a href='<c:url value="/pieces/${firstTroop.id}/droop"/>'>
                    <button class="pre-button">DESPLEGAR TROPA</button>
                </a>
                <a href='<c:url value="/pieces/${firstSpy.id}/droop"/>' >
                    <button class="pre-button">COLOCAR ESPIA</button>
                </a>
                <a href='<c:url value="/pieces/kill"/>' >
                    <button class="pre-button">ELIMINAR TROPA</button>
                </a>
                <a href='<c:url value="/pieces/return/1"/>' >
                    <button class="pre-button">DEVOLVER TROPA</button>
                </a>
                <a href='<c:url value="/pieces/return/2"/>' >
                    <button class="pre-button">DEVOLVER ESPIA</button>
                </a>
            </div>
    
</petclinic:layout>