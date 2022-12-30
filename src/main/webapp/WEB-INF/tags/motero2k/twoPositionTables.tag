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
                <th>Occupied?</th>
                <th>Player</th>
                <th>For Spy?</th>
                <th>Path</th>
                <th>City<th>
                <th>Adjacencies</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${movablePosition}" var="pos">
                <tr>
                    <td>
                        <c:out value="${pos.id}"/>
                    </td>
                    <td>
                        <c:out value="${pos.isOccupied}"/>
                    </td>
                    <td>
                        <c:out value="${pos.player.name}"/>
                    </td>
                    <td>
                        <c:out value="${pos.forSpy}"/>
                    </td>
                    <td>
                        <c:out value="${pos.path}"/>
                    </td>
                    <td>
                        
                        <c:out value="${pos.city}"/>
                    </td>
                    <td>
                        <c:out value="${pos.adjacents}"/>
                    </td>
                    <td>
                        <input type="radio" id="radio-${pos.id}" name="positionSourceId" value="${pos.id}" hidden/>
                        <label for="radio-${pos.id}" class="btn btn-default">
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
                    <th>Occupied?</th>
                    <th>Player</th>
                    <th>For Spy?</th>
                    <th>Path</th>
                    <th>City<th>
                    <th>Adjacencies</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${freePositions}" var="pos">
                    <tr>
                        <td>
                            <c:out value="${pos.id}"/>
                        </td>
                        <td>
                            <c:out value="${pos.isOccupied}"/>
                        </td>
                        <td>
                            <c:out value="${pos.player.name}"/>
                        </td>
                        <td>
                            <c:out value="${pos.forSpy}"/>
                        </td>
                        <td>
                            <c:out value="${pos.path}"/>
                        </td>
                        <td>
                            
                            <c:out value="${pos.city}"/>
                        </td>
                        <td>
                            <c:out value="${pos.adjacents}"/>
                        </td>
                        <td>
                                <input type="radio" id="radio-${pos.id}" name="positionTargetId" value="${pos.id}" hidden/>
                            <label for="radio-${pos.id}" class="btn btn-default">
                                ELIGEME
                            </label>
                        </td>
                        
                    </tr>
                </c:forEach>
                
                </tbody>
            </table>

    
