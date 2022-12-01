<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="actions">
    <h2>Actions</h2>    
    <table id="actionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${actions}" var="action">
            <tr>
                <td>
                    <c:out value="${action.id}"/>
                </td>
                <td>
                    <c:out value="${action.isSimple== true? 'Yes':'No'}"/>
                </td>
                <td>
                    <c:out value="${action.simpleActionNameEnum}"/>
                </td>
                <td>
                    <c:out value="${action.complexConditionEnum}"/>
                </td>
                <td>
                    <c:out value="${action.value}"/>
                </td>
                <td>
                    <c:out value="${action.entityStatusEnum}"/>
                </td>
                <td>
                    <c:out value="${action.entityEnum}"/>
                </td>
                <td>
                    <c:out value="${action.myActionSons}"/>
                </td>
                <td>
                    <c:out value="${action.myActionFathers}"/>
                </td>
                <td>
                    <c:out value="${action.presence}"/>
                </td>
                <td>
                    <c:out value="${action}" escapeXml="false"/>
                </td>

                
            </tr>
        </c:forEach>
        </tbody>
    </table>

    
    <body>

        <div class="container-button">
            <a href='<c:url value="/positions/1/place/troop/false"/>' >
                <div class="aBotton">
                    <div class="especial-btn btn-two"><div class="textbtn">DESPLEGAR TROPA COMO JUGADOR 1</div></div>
                    </div>
			</a><br>
            <a href='<c:url value="/positions/2/place/troop/false"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">DESPLEGAR TROPA COMO JUGADOR 2</div></div>
                    </div>
			</a><br>
            <a href='<c:url value="/positions/1/place/troop/true"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">DESPLEGAR TROPA COMO JUGADOR 1 CON PRESENCIA</div></div>
                    </div>
			    </a><br>
            <a href='<c:url value="/positions/2/place/troop/true"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">DESPLEGAR TROPA COMO JUGADOR 2 CON PRESENCIA</div></div>
                    </div>
			</a><br>

            <a href='<c:url value="/positions/1/place/spy"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">COLOCAR ESPIA COMO JUGADOR 1</div></div>
                    </div>
            </a><br>
            <a href='<c:url value="/positions/2/place/spy"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">COLOCAR ESPIA COMO JUGADOR 2</div></div>
                    </div>
            </a><br>

            <a href='<c:url value="/positions/1/kill/false"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">MATAR TROPA COMO JUGADOR 1</div></div>
                    </div>
            </a><br>
            <a href='<c:url value="/positions/1/kill/true"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">MATAR TROPA COMO JUGADOR 2</div></div>
                    </div>
            </a><br>

            <a href='<c:url value="/positions/2/kill/false"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">MATAR TROPA COMO JUGADOR 1 CON PRESENCIA</div></div>
                    </div>
            </a><br>
            <a href='<c:url value="/positions/2/kill/true"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">MATAR TROPA COMO JUGADOR 2 CON PRESENCIA</div></div>
                    </div>
            </a><br>

            <a href='<c:url value="/positions/1/return"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">DEVOLVER PIEZA COMO JUGADOR 1</div></div>
                    </div>
            </a><br>
            <a href='<c:url value="/positions/2/return"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">DEVOLVER PIEZA COMO JUGADOR 2</div></div>
                    </div>
            </a><br>
            <a href='<c:url value="/positions/1/supplant/false"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">SUPLANTAR TROPA COMO JUGADOR 1</div></div>
                    </div>
            </a><br>
            <a href='<c:url value="/positions/1/supplant/true"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">SUPLANTAR TROPA COMO JUGADOR 1 CON PRESENCIA</div></div>
                    </div>
            </a><br>
            <a href='<c:url value="/positions/2/supplant/false"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">SUPLANTAR TROPA COMO JUGADOR 2</div></div>
                    </div>
            </a><br>
            <a href='<c:url value="/positions/2/supplant/true"/>' >
                <div class="aBotton ">
                    <div class="especial-btn btn-two"><div class="textbtn">SUPLANTAR TROPA COMO JUGADOR 2 CON PRESENCIA</div></div>
                    </div>
            </a>
        </div>
    <style>
        .textbtn {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            opacity: 100%;
        }
        @import 'https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300';

        .body {
        width: 100%;
        height: 100%;
        overflow: hidden;
        margin: 0;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        font-family: 'Open Sans Condensed', sans-serif;
        }

        .box-2 { background-color: #282121; }

        .especial-btn {
            opacity: 90%;
            line-height: 50px;
            height: 50px;
            text-align: center;
            width: 100%;
            cursor: pointer;
        }
        .container-button{
            margin-left: 0%;
        }

        /*Botton*/
        .btn-two {
            color: rgb(197, 172, 63);
            transition: all 0.5s;
            position: relative;	
            background-color: #282121;
            font-size: 20px;
            border-radius: 10%;
        }
        .btn-two span {
            z-index: 2;	
            display: block;
            position: absolute;
            width: 100%;
            height: 100%;
            border-radius: 10%;
            
        }
        .btn-two::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 1;
            transition: all 0.5s;
            border: 1px solid rgba(255, 255, 255, 0.086);
            background-color: rgba(255, 255, 255, 0);
            border-radius: 10%;

        }
        .btn-two::after {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 1;
            transition: all 0.5s;
            border: 1px solid rgba(202, 189, 189, 0.123);
            background-color: rgba(200, 177, 177, 0.122);
            border-radius: 10%;

        }
        .btn-two:hover::before {
        transform: rotate(-45deg);
        background-color: rgba(255, 255, 255, 0.105);
        border-radius: 10%;

        }
        .btn-two:hover::after {
        transform: rotate(45deg);
        background-color: rgba(255, 255, 255, 0.105);
        border-radius: 10%;

        }
        .aBotton{
            text-align: center;
        }

    </style>

</petclinic:layout>