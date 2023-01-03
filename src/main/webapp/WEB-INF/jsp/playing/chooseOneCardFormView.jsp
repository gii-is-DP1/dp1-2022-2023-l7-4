<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/styles/tyrants.css">
<body>

        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Nombre</th>
                    <th>PV de círculo de ascensión</th>
                </tr>
                </thead>
                <tbody>
                <c:out value="${size}"/>
                <c:forEach var="card" items="${cards}" >
                    <tr>
                        <td>
                            <c:out value="${card.id}"/>
                        </td>
                        <td>
                            <c:out value="${card.name}"/>
                        </td>
                        <td>
                            <c:out value="${card.innerCirclePV} "/>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${devore}">
                                    <a type="submit" href="chosenCardToDevore/${card.id}" class="play-buy-card-button" style="font-size: 1.1vmax;">DEVORAR</a>
                                
                            </c:when>
                            <c:otherwise>
                                <a type="submit" href="chosenCardToPromote/${card.id}" class="play-buy-card-button" style="font-size: 1.1vmax;">PROMOVER</a>
                            </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
        </table>


</body>