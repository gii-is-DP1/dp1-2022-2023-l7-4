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
            <petclinic:inputField label="Name" name="name" />
            <div>
                <label class="col-sm-2 control-label">Size</label>
                <select name="size">
                    <option value=2>2</option>
                    <option value=3>3</option>
                    <option value=4>4</option>
                </select>
            </div>
                <div>
                    <label class="control-label" style="margin-bottom: 0; margin-left: 0;">First HalfDeck:</label>
                    <form:select path="firstHalfDeck.id">
                        <form:options items="${halfDecks}" itemValue="id" itemLabel="name" />
                    </form:select>
                </div>
                <div >
                    <label class="control-label" style="margin-bottom: 0; margin-left: 200px;">Second HalfDeck:</label>
                    <form:select path="secondHalfDeck.id">
                        <form:options items="${halfDecks}" itemValue="id" itemLabel="name" />
                    </form:select>
                </div>
            <div>
                <label class="col-sm-2 control-label">Players</label>
                <form:select path="players">
                    <form:options items="${users}" itemValue="name" itemLabel="name" />
                </form:select>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Add game</button>
                </div>
        </div>
    </form:form>
</petclinic:layout>
