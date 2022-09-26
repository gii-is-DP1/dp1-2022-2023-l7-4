<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
        <h2>Proyect ${title}</h2>
        <p><h2>Group ${group}</h2></p>
        <p><ul>
        <c:forEach items="${persons}" var= "person">
            <li>${person.firstName} ${person.lastName}</li> 
        </c:forEach>
    </ul></p>
    </div>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/pets.png" htmlEscape="true" var="petsImage"/>
            
            <img class="img-responsive" src="${petsImage}"/>
            
            
        </div>
        <div>
            <spring:url value="/resources/images/pewpew.jpg" htmlEscape="true" var="chiefImage"/>
            <img class="img-responsive" src="${chiefImage}"/>
        </div>
    </div>
</petclinic:layout>
