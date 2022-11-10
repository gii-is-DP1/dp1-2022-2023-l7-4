<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="pieces">
    <h2>POSICIONES DISPONIBLES</h2>
    
    <table id="positionsTable" class="table table-striped">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Occupied</th>
                    <th>For Spy?</th>
                    <th>Path</th>
                    <th>City<th>
                    <th>Adjacency</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${freePositions}" var="position">
                    <tr>
                        <td>
                            <c:out value="${position.id}"/>
                        </td>
                        <td>
                            <c:out value="${position.occupied}"/>
                        </td>
                        <td>
                            <c:out value="${position.forSpy}"/>
                        </td>
                        <td>
                            <c:out value="${position.path}"/>
                        </td>
                        <td>
                            <c:out value="${position.city}"/>
                        </td>
                        
                    </tr>
                </c:forEach>
        </tbody>
    </table>
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
            height: 800px;
            width: 800px;
            position:relative;
        }
        </style>
        <form:form modelAttribute="piece" id="add-piece">
            <input type="hidden" name="id" value="${piece.id}"/>
            <input type="hidden" name="pieceType" value="${piece.pieceType.id}"/>
            <input type="hidden" name="player_id" value="${piece.player_id}"/>
            <label for="pos">SELECCIONA UNA POSICIÓN</label>
            <select name="position" id="pos">
                    <c:forEach items="${freePositions}" var="Fposition">
                                <option value="${Fposition}" label="${Fposition.id}"> 
                                    ${Fposition.id}
                                   </option>
                    </c:forEach>
                
            </select>
            <div class="board">
                <c:forEach items="${freePositions}" var="Fposition">
                                <div class="btn-pos-${Fposition.id}">
                                    <button value="${Fposition}" itemref="piece.position" type="button">
                                        ${Fposition.id}
                                    </button>
                                </div>
                    </c:forEach>
            </div>
            <button class="btn btn-default" type="submit">Add Product</button>
        </form:form>
</petclinic:layout>