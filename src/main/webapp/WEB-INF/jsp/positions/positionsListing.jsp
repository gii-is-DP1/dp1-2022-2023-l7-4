<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="positions">
    <h2>Positions</h2>
    <script>
        console.log("sssss")
    </script>
    
    <table id="positionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Zone</th>
            <th>Occupied</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${positions}" var="position">
            <tr>
                <td>
                    <c:out value="${position.id}"/>
                </td>
                <td>
                    <c:out value="${position.zone} "/>
                </td>
                <td>
                    <c:out value="${position.occupied}"/>
                </td>
                
                <td>
                    <form action="positions/${position.id}/occupy">
                        <input type="submit" value="Des/Ocupar" />
                </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:forEach items="${freePositions}" var="freePosition">
        <c:out value="${freePosition.id}"/>
    </c:forEach>
    
    <body>
        
    <style>
        .btn-pos-1{
            background-color: aqua;
            border: 8px;
            text-align: center;
            position: absolute;
            border-radius: 50%;
            top:0px;
        }
        .btn-pos-2{
            background-color: aqua;
            border: 8px;
            text-align: center;
            position: absolute;
            border-radius: 50%;
            top:30px;
        }
        .btn-pos-3{
            background-color: aqua;
            border: 8px;
            text-align: center;
            position: absolute;
            border-radius: 50%;
            top:60px;
        }
        .board{
            background-image: url("/resources/images/example_map.jpg");
            background-size: contain;
            background-repeat: no-repeat;
            height: 600px;
            width: 600px;
            position:relative;
        }
    </style>
    
        <div class="board">
            <c:forEach items="${positions}" var="position">
                <!--OJO, PUEDES ENCADENAR VALORES DE LAS ETIQUETAS CON LOS ATRIBUTOS DE LOS ITEMS-->
            <div class="btn-pos-${position.id}">
                <form action="positions/${position.id}/occupy">
                    <input type="submit" value="${position.id}" />
            </div>
            </c:forEach>
        </div>
    </body>
    

</petclinic:layout>