<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="paths">
    <link rel="stylesheet" href="/resources/styles/scrollbody.css">

    <h2>
        <c:if test="${path['new']}">New </c:if> path
    </h2>
    <form:form modelAttribute="path" class="form-horizontal" id="add-path-form">
        <div class="form-group has-feedback"> 

            
            <petclinic:inputField label="First City" name="firstCity"/>
            <petclinic:inputField label="Second City" name="secondCity"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${path['new']}">
                        <button class="btn btn-default" type="submit">Add path</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update path</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
