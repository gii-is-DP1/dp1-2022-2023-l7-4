<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="motero2k" tagdir="/WEB-INF/tags/motero2k" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/styles/tyrants.css">
<body>

    <form:form modelAttribute="cardData">
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Nombre</th>
                    <th>PV de círculo de ascensión</th>
                </tr>
                </thead>
                <tbody>
                
                <c:forEach items="${cards}" var="card">
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
                            <input type="radio" id="radio-${card.id}" name="cardId" value="${card.id}" hidden/>
                            <label for="radio-${card.id}" class="btn btn-default">
                                    Eligeme
                            </label>
                        </td>
                    </tr>
                </c:forEach>
        </table>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">CONFIRMAR</button>
            </div>
        </div>
    </form:form>

</body>