<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="positions">

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
            <th>Occupied</th>

            <th>For Spy?</th>
            <th>Path</th>
            <th>City<th>
            <th>Adjacency</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${positions}" var="position">
            <tr>
                <td>
                    <c:out value="${position.id}"/>
                </td>
                <td>
                    <c:out value="${position.occupied}"/>
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
    <c:forEach items="${freePositions}" var="freePosition">
        <c:out value="${freePosition.id}"/>
    </c:forEach>
    <c:forEach items="${pathPositions}" var="pathPosition">
        <c:out value="${pathPosition.id}"></c:out>
    </c:forEach>
    
    <body>
        
   
    
        <div class="board">
            <c:forEach items="${positions}" var="position">
                <!--OJO, PUEDES ENCADENAR VALORES DE LAS ETIQUETAS CON LOS ATRIBUTOS DE LOS ITEMS-->
                <a class = "btn-pos-${position.id}" href="/positions/${position.id}/occupy">${position.id}</a>
            </c:forEach>
        </div>
    </body>
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
    

        <div class="board">
            <c:forEach items="${positions}" var="position">
                <!--OJO, PUEDES ENCADENAR VALORES DE LAS ETIQUETAS CON LOS ATRIBUTOS DE LOS ITEMS-->
                <form action="positions/${position.id}/occupy">
                    <input class="btn-pos-${position.id}"type="submit" value="${position.id}" />
            </c:forEach>
        </div>
    </body>


</petclinic:layout>