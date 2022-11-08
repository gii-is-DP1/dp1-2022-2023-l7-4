<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="pieces">
    <h2>PIECES</h2>
    
    <table id="piecesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Type</th>
            <th>Position</th>
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
                 
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>