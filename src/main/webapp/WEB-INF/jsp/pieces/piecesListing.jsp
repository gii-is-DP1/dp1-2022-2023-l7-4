<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="pieces">
    <h2>PIECES</h2>
    <head>
        <style>
            .pre-button{
                background-color: blueviolet;
                color:aliceblue;
                text-align: center;
                position: absolute;
                border-radius: 20%;
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
                        hola
                    </div>
					</a>
                </td>
                 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>