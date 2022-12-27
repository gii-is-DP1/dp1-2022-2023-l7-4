<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="positions">
    <link rel="stylesheet" href="/resources/styles/scrollbody.css">

    <h2>Positions</h2>
    <script>
        $(document).ready(function () {
            $('select').attr("id", "selected-pos"); //direct descendant of a
        });
    </script>
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
            
        <c:forEach items="${availablePositions}" var="pos">
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
                
                <form:form modelAttribute="idposition" id="${pos.id}">
                    <input type="hidden" name="id" value="${pos.id}"/>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" id="{pos.id}" class="btn btn-default">SELECCIONAR POSICION</button>
                        </div>
                    </div>
                </form:form>
                </td>
                
            </tr>
        </c:forEach>
        
        </tbody>
    </table>

        
    <style>

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