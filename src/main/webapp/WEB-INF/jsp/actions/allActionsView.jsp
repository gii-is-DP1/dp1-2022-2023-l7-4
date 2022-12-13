<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="actions">
    <link rel="stylesheet" href="/resources/styles/scrollbody.css">

    <h2>Actions</h2>    
    <table id="compoundTable" class="table table-striped">
        <thead>
            <tr>
                <th>JOINER</th>
                <th>SUBACTIONS</th>
                
            </tr>
        </thead>
    <c:forEach items="${actions}" var="action">
            <c:if test="${action.isSimple()=='false'}">
                        <tr>
                            <td>
                                <c:out value="${action.actionName}"/>
                            </td>
                            <td>
                                <c:out value="${action}"/>
                            </td>
                        </tr>
                </c:if>
    </c:forEach>
</table>
<table id="compoundTable" class="table table-striped">
    <thead>
        <tr>
            <th>Id</th>
            <th>iterations</th>
            <th>name</th>
            <th>value</th>
            <th>aspect</th>
                
        </tr>
        </thead>
        <c:forEach items="${actions}" var="action">
            <c:if test="${action.isSimple()=='true'}">
                <tr>
                    <td>
                        <c:out value="${action.id}"/>
                    </td>
                    <td>
                        <c:out value="${action.iterations}"/>
                    </td>
                    <td>
                        <c:out value="${action.actionName}"/>
                    </td>
                    <td>
                        <c:out value="${action.value}"/>
                    </td>
                    <td>
                        <c:out value="${action.aspect}"/>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
                </table>
                
       

</petclinic:layout>