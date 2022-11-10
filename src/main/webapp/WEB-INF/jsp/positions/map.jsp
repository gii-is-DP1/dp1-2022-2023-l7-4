<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="positions">
    <h2>PIECES</h2>
    <script>
        function selectPosition(piece,position) {
        piece.setPosition(position);
        console.log(piece.getPosition());
        }
    </script>
    
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
                <c:forEach items="${freePositions}" var="position">
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
                            <c:out value="${position.city}"/>
                        </td>
                        <td>
                            <button onclick="selectPosition(piece,position)">
                                SELECT ME
                            </button>
                        </td>
                        
                    </tr>
                </c:forEach>
        </tbody>
    </table>
    <body>
        <style>
            .board{
            background-image: url("/resources/images/example_map.jpg");
            background-size: contain;
            background-repeat: no-repeat;
            height: 800px;
            width: 800px;
            position:relative;
        }
        </style>
        <div class="board">

        </div>
        <button class="btn btn-default" type="submit">OCUPAR</button>
    </form>
    </body>
</petclinic:layout>