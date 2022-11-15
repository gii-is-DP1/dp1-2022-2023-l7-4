<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script>
    document.addEventListener("DOMContentLoaded", function(event) { 
    var scrollpos = localStorage.getItem('scrollpos');
    if (scrollpos) window.scrollTo(0, scrollpos);
});

window.onbeforeunload = function(e) {
    localStorage.setItem('scrollpos', window.scrollY);
};
</script>

<petclinic:layout pageName="positions">
    <h2>Positions</h2>    
    <table id="positionsTable" class="table table-striped">
    <script>
        console.log("sssss")
    </script>
    <h2>Cities</h2>
    <table id="citiesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Capacity</th>
            <th>Zone</th>
            <th>VP</th>
            <th>isStartingCity</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cities}" var="city">
            <tr>
                <td>
                    <c:out value="${city.id}"/>
                </td>
                <td>
                    <c:out value="${city.capacity}"/>
                </td>
                <td>
                    <c:out value="${city.zone}"/>
                </td>
                <td>
                    <c:out value="${city.vpEndgameValue}"/>
                </td>
                <td>
                    <c:out value="${city.isStartingCity}"/>
                </td>
                
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h2>Paths</h2>
    <table id="pathsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Capacity</th>
            <th>CityA</th>
            <th>CityB</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${paths}" var="path">
            <tr>
                <td>
                    <c:out value="${path.id}"/>
                </td>
                <td>
                    <c:out value="${path.capacity}"/>
                </td>
                <td>
                    <c:out value="${path.firstCity}"/>
                </td>
                <td>
                    <c:out value="${path.secondCity}"/>
                </td>
                
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h2> AUTOGENERATE POSITIONS</h2>
    <form action="positions/populate">
        <input type="submit" value="Populate!" />
    </form>
    <h2> All positions</h2>
    <table id="positionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Occupied?</th>
            <th>Player</th>
            <th>For Spy?</th>
            <th>Path</th>
            <th>City<th>
            <th>Adjacencies</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${positions}" var="position">
            <tr>
                <td>
                    <c:out value="${position.id}"/>
                </td>
                <td>
                    <c:out value="${position.isOccupied}"/>
                </td>
                <td>
                    <c:out value="${position.player.name}"/>
                </td>
                <td>
                    <c:out value="${position.forSpy}"/>
                </td>
                <td>
                    <c:out value="${position.path}"/>
                </td>
                <td>
                    
                    <form action="positions/${position.id}/occupy">
                        <input class = "btn-submit-form" type="submit" value="Des/Ocupar"/>
                    <c:out value="${position.city}"/>
                </td>
                <td>
                    <c:out value="${position.adjacents}"/>
                </td>
                <td>
                <form action="positions/${position.id}/occupy">
                        <input type="submit" value="Des/Ocupar" />
                </form>
                <form action="positions/${position.id}/adjacents">
                        <input type="submit" value="adjacents" />
                </form>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>

    
    <body>

        <div class="container">
            
            <a href='<c:url value="/positions/1/place/troop/false"/>' >
                    <div class="btn-default">
                        DESPLEGAR TROPA COMO JUGADOR 1
                    </div>
			</a>
            <a href='<c:url value="/positions/2/place/troop/false"/>' >
                    <div class="btn-default">
                        DESPLEGAR TROPA COMO JUGADOR 2
                    </div>
			</a>
            <a href='<c:url value="/positions/1/place/troop/true"/>' >
                    <div class="btn-default">
                        DESPLEGAR TROPA COMO JUGADOR 1 CON PRESENCIA
                    </div>
			</a>
            <a href='<c:url value="/positions/2/place/troop/true"/>' >
                    <div class="btn-default">
                        DESPLEGAR TROPA COMO JUGADOR 2 CON PRESENCIA
                    </div>
			</a>
        </div>
        <div class="container">
            <a href='<c:url value="/positions/1/place/spy"/>' >
                <div class="btn-default">
                    COLOCAR ESPIA COMO JUGADOR 1
                </div>
            </a>
            <a href='<c:url value="/positions/2/place/spy"/>' >
                <div class="btn-default">
                    COLOCAR ESPIA COMO JUGADOR 2
                </div>
            </a>
        </div>
        <div class="container">
            <a href='<c:url value="/positions/1/kill/false"/>' >
                <div class="btn-default">
                    MATAR TROPA COMO JUGADOR 1
                </div>
            </a>
            <a href='<c:url value="/positions/1/kill/true"/>' >
                <div class="btn-default">
                    MATAR TROPA COMO JUGADOR 1 CON PRESENCIA
                </div>
            </a>
        </div>
        <div class="container">
            <a href='<c:url value="/positions/2/kill/false"/>' >
                <div class="btn-default">
                    MATAR TROPA COMO JUGADOR 2
                </div>
            </a>
            <a href='<c:url value="/positions/2/kill/true"/>' >
                <div class="btn-default">
                    MATAR TROPA COMO JUGADOR 2 CON PRESENCIA
                </div>
            </a>
        </div>
        <div class="container">
            <a href='<c:url value="/positions/1/return"/>' >
                <div class="btn-default">
                    DEVOLVER PIEZA COMO JUGADOR 1
                </div>
            </a>
            <a href='<c:url value="/positions/2/return"/>' >
                <div class="btn-default">
                    DEVOLVER PIEZA COMO JUGADOR 2
                </div>
            </a>
        </div>
        <div class="container">
            <a href='<c:url value="/positions/1/supplant/false"/>' >
                <div class="btn-default">
                    SUPLANTAR TROPA COMO JUGADOR 1
                </div>
            </a>
            <a href='<c:url value="/positions/1/supplant/true"/>' >
                <div class="btn-default">
                    SUPLANTAR TROPA COMO JUGADOR 1 CON PRESENCIA
                </div>
            </a>
        </div>
        <div class="container">
            <a href='<c:url value="/positions/2/supplant/false"/>' >
                <div class="btn-default">
                    SUPLANTAR TROPA COMO JUGADOR 2
                </div>
            </a>
            <a href='<c:url value="/positions/2/supplant/true"/>' >
                <div class="btn-default">
                    SUPLANTAR TROPA COMO JUGADOR 2 CON PRESENCIA
                </div>
            </a>
        </div>
    

</petclinic:layout>