<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
 
<petclinic:layout pageName="games">
    <h2>
        New game
    </h2>
    <form:form modelAttribute="game" class="form-horizontal" id="add-game-form" >
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="name"/>
            <div>
            <label class="col-sm-2 control-label">Size</label>
            <select name="size">  
            <option value=2>2</option>
            <option value=3>3</option>
            <option value=4>4</option>
            </select>
            </div>
            <label>Half Decks:<label>

                <c:forEach varStatus="loop" items="${halfDecks}" var="halfdeck">
                       <form:checkbox path="halfdecks" value="${halfdeck.name}" />${halfdeck.name}
                </c:forEach>
        </div>
        <label>Players</label>
        <form:select path="players">
            <form:option value="-" label="--Please Select" />
            <form:options items="${users}" itemValue="name" itemLabel="name" />
        </form:select>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" type="submit">Add game</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
