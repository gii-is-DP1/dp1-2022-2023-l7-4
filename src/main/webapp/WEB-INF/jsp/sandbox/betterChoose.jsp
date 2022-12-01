<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="positions">
    <h2>Positions</h2>
    
    <body>
        <form:form modelAttribute="idposition">
        <input type="radiobutton" id="radio1" hidden>
        <label for="radio1">HOLA</label>

    </form:form>
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