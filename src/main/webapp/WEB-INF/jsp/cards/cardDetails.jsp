<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<petclinic:layout pageName="saerching">
    <h2>Cartas </h2>
    <script>
        console.log("ssss")
    </script>
    
    <table class="table table-striped">
        <c:forEach items="${id}" var="id">
        <tr>
            <td valign="top">
                <dl class="dl-horizontal">
                    <dt>Id</dt>
                    <dd><c:out value="${id.id}"/></dd>
                    <dt>Nombre</dt>
                    <dd><c:out value="${id.name}"/></dd>
                    <dt>Coste</dt>
                    <dd><c:out value="${id.cost}"/></dd>
                    <dt>Historia</dt>
                    <dd><c:out value="${id.story}"/></dd>
                    <dt>Acción de la carta</dt>
                    <dd><c:out value="${id.rulesText}"/></dd>
                    <dt>PV</dt>
                    <dd><c:out value="${id.deckVP}"/></dd>
                    <dt>PV en Círculo interno</dt>
                    <dd><c:out value="${id.innerCirclePV}"/></dd>
                    <dt>Rareza</dt>
                    <dd><c:out value="${id.rarity}"/></dd>
                </dl>
            </td>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>