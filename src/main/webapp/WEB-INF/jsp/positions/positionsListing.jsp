<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="positions">
    <h2>Positions</h2>    
    <table id="positionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Zone</th>
            <th>Occupied</th>
            <th>Ocupar</th>
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
                        <input class = "btn-submit-form" type="submit" value="Des/Ocupar"/>
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
    

</petclinic:layout>