<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="actions">
    <link rel="stylesheet" href="/resources/styles/scrollbody.css">
    <link rel="stylesheet" href="/resources/styles/tyrants.css">

    <h2>Actions</h2>    


<table id="compoundTable" class="table table-striped">
    <thead>
        <tr>
            <th>Id</th>
            <th>rule text</th>
            <th>full</th>
                
        </tr>
        </thead>
        <c:forEach items="${cards}" var="card">
            <c:if test="${card.action!=null}">
                <tr>
                    <td>
                        <c:out value="${card.action.id}"/>
                    </td>
                    <td>
                        <c:out value="${card.rulesText}"/>
                    </td>
                    <td>
                        <c:out value="${card.action}"/>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
                </table>
                
       

</petclinic:layout>