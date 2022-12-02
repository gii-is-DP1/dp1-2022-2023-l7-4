<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="positions">
    <h2>Positions</h2>
    <script>
        $(document).ready(function () {
            $('select').attr("id", "selected-pos"); //direct descendant of a
        });
    </script>
    <form:form modelAttribute="pairPosition">
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
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">MOVER PIEZA</button>
                </div>
            </div>
    </form:form>
        
    <style>
        input:checked ~label{
            color: red;
            background-color: #96dfe4;
        }

        .btn-submit-form{
            background-color: #96dfe4;
            border-width: 1px;
        }

        a:link, a:visited, a:active {
            text-decoration: none;
        
        }
        .table{
            width: 50%;
            margin-right: auto;
            margin-left: auto;
             
        }

        .btn-pos-1{
            text-emphasis: none;
            text-decoration: none;
            height: 20px;
            width: 20px;
            background-color: #96dfe4;
            border: 8px;
            text-align: center;
            position: absolute;
            border-radius: 50%;
            top:0px;
        }
        .btn-pos-2{
            text-emphasis: none;
            text-decoration: none;
            height: 20px;
            width: 20px;
            background-color: #96dfe4;
            border: 8px;
            text-align: center;
            position: absolute;
            border-radius: 50%;
            top:30px;
        }
        .btn-pos-3{
            text-emphasis: none;
            text-decoration: none;
            height: 20px;
            width: 20px;
            background-color: #96dfe4;
            border: 8px;
            text-align: center;
            position: absolute;
            border-radius: 50%;
            top:60px;
        }

        .board{

            margin-left: auto;
            margin-right: auto;
            background-image: url("/resources/images/example_map.jpg");
            background-size: contain;
            background-repeat: no-repeat;
            background-position: center;
            height: 600px;
            width: 600px;
            display: grid;
            position: relative;
        }
    </style>
    

</petclinic:layout>