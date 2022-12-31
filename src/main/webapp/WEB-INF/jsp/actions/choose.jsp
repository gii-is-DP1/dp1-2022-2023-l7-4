<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <html>

    <head>
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
            }

            div {
                display: inline-block;
                margin: 20px;
                text-align: left;
            }

            a {
                display: inline-block;
                padding: 10px 20px;
                font-size: 16px;
                color: white;
                background-color: #6200EE;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                text-decoration: none;
            }

            a:hover {
                background-color: #5c00d4;
            }

            a:active {
                background-color: #4400b2;
            }
        </style>
    </head>

    <body>

        Elige una opcion (x${action.originalIterations}):
        <br>
        Veces Restantes: ${action.iterations}
        <br>
        <div>
            <c:forEach var="action" items="${action.getSubactions()}">
                <a href="/play/${gameId}/round/${round}/chosenSubaction/${action.id}">${action}</a>
            </c:forEach>
        </div>
    </body>

    </html>