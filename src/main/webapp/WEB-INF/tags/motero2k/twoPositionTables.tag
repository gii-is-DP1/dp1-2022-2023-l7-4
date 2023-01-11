<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="citiess" required="false" rtexprvalue="true" %>
<%@ attribute name="pathss" required="false" rtexprvalue="true" %>
<%@ attribute name="positionss" required="false" rtexprvalue="true" %>



<spring:url value="/resources/css/petclinic.css" var="petclinicCss"/>

<h2>Cities</h2>
    <table id="citiesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Controlled By</th>
            <th>Totally Controlled By</th>
            <th>Capacity</th>
            <th>Zone</th>
            <th>VP</th>
            <th>isStartingCity</th>
            <th>pos</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cities}" var="city">
            <tr>
                <td>
                    <c:out value="${city.id}"/>
                </td> 
                <td>
                    <c:out value="${city.whoControls()}"/>
                </td>
                <td>
                    <c:out value="${city.whoTotallyControls()}"/>
                </td>
                <td>
                    <c:out value="${city.getCapacity()}"/>
                </td>
                <td>
                    <c:out value="${city.getZone()}"/>
                </td>
                <td>
                    <c:out value="${city.vpEndgameValue}"/>
                </td>
                <td>
                    <c:out value="${city.isStartingCity()}"/>
                </td>
                <td>
                    <c:out value="${city.getPositions()}"/>
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
                    <c:out value="${path.getId()}"/>
                </td>
                <td>
                    <c:out value="${path.getCapacity()}"/>
                </td>
                <td>
                    <c:out value="${path.getFirstCity()}"/>
                </td>
                <td>
                    <c:out value="${path.getSecondCity()}"/>
                </td>
                
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <h2> All positions</h2>
    <table id="positionsTable1" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Occupied</th>
            <th>Player</th>
            <th>Type</th>
            <th>Path</th>
            <th>City</th>
            <th>Adjacencies</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${movablePositions}" var="position">
            <tr>
                <td>
                    <c:out value="${position.getId()}"/>
                </td>
                <td>
                    <c:out value="${position.isOccupied()== true? 'Yes':'-'}"/>
                </td>
                <td>
                    <c:out value="${position.player.name}"/>
                </td>
                <td>
                    <c:out value="${position.forSpy==true? 'Spy': 'Troop'}"/>
                </td>
                <td>
                    <c:out value="${position.path}"/>
                </td>
                <td>
                    <c:out value="${position.city}"/>
                </td>
                <td>
                    <c:out value="${position.adjacents}"/>
                </td>
                <td>
                        <input type="radio" id="radio-${position.id}" name="positionSourceId" value="${position.id}" hidden/>
                        <label for="radio-${position.id}" class="btn btn-default">
                            ELIGEME
                        </label>
                    </td>

            </tr>
        </c:forEach>
        
        </tbody>
                
    </table>
           <table id="positionsTable2" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Occupied</th>
            <th>Player</th>
            <th>Type</th>
            <th>Path</th>
            <th>City</th>
            <th>Adjacencies</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${freePositions}" var="position">
            <tr>
                <td>
                    <c:out value="${position.getId()}"/>
                </td>
                <td>
                    <c:out value="${position.isOccupied()== true? 'Yes':'-'}"/>
                </td>
                <td>
                    <c:out value="${position.player.name}"/>
                </td>
                <td>
                    <c:out value="${position.forSpy==true? 'Spy': 'Troop'}"/>
                </td>
                <td>
                    <c:out value="${position.path}"/>
                </td>
                <td>
                    <c:out value="${position.city}"/>
                </td>
                <td>
                    <c:out value="${position.adjacents}"/>
                </td>
                <td>
                                <input type="radio" id="radio-${position.id}" name="positionTargetId" value="${position.id}" hidden/>
                            <label for="radio-${position.id}" class="btn btn-default">
                                ELIGEME
                            </label>
                        </td>

            </tr>
        </c:forEach>
        
        </tbody>
                
    </table>

    
