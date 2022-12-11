<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="citiess" required="false" rtexprvalue="true" %>
<%@ attribute name="pathss" required="false" rtexprvalue="true" %>
<%@ attribute name="positionss" required="false" rtexprvalue="true" %>


<spring:url value="/resources/css/petclinic.css" var="petclinicCss"/>
<link href="${petclinicCss}" rel="stylesheet"/>

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
    <table id="positionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Id</th>
            <th>Occupied</th>
            <th>Player</th>
            <th>Type</th>
            <th>Path</th>
            <th>City</th>
            <th>Adjacencies</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${positions}" var="position">
            <tr>
                <td>
                    <c:out value="${position.getId()}"/>
                </td>
                <td>
                    <c:out value="${position.isOccupied()== true? 'Yes':'-'}"/>
                </td>
                <td>
                    <c:out value="${position.player.name}"/>
                </td>
                <td>
                    <c:out value="${position.forSpy==true? 'Spy': 'Troop'}"/>
                </td>
                <td>
                    <c:out value="${position.path}"/>
                </td>
                <td>
                    <c:out value="${position.city}"/>
                </td>
                <td>
                    <c:out value="${position.adjacents}"/>
                </td>
                <td>
                    <input type="radio" id="radio-${position.id}" name="id" value="${position.id}" hidden/>
                    <label for="radio-${position.id}" class="btn btn-default">
                            Eligeme
                    </label>
                </td>

            </tr>
        </c:forEach>
        
        </tbody>
                
    </table>

    <div style="margin-top: 20px;">
        <a href="#popUp" id="openPopUp" >
            Confirmar
        </a>
        <!-- POP UP -->
        <aside id="popUp" class="popup">
            <div class="popUpContainer">
                <header>
                <a href="#!" class="closePopUp">X</a>
                <h2>¿Estás seguro?</h2>
                </header>
                <article>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-default">CONFIRMAR</button>
                            <br/><br/>
                        </div>
                    </div>
                </article>
            </div>        
            <a href="#!" class="closePopUpOutSide"></a>    
        </aside>
    </div>
    <style>
        h1{
    color: #ffffff;
    padding: 1em;
  }
  #openPopUp{
    text-align:center;
    background-color: #1abc9c;
    padding: 10px 10px;
    color: #ffffff;
    font-weight: 500;
    font-size: 17px;
    border-radius: 3.5px;
    text-decoration: none;
    font-weight: 100;
    transition:background-color 0.15s linear;
    -webkit-transition:background-color 0.15s linear;
    -moz-transition:background-color 0.25s linear;
    -o-transition:background-color 0.25s linear;
  }
  a#openPopUp:hover{
    cursor: pointer;
    background-color: #1abc9e;
  }
  .popup{
    position: fixed;
    top: -100vh;
    left: 0;
    z-index: 9999999;
    background: rgba(0,0,0, 0.75);
    width: 100vw;
    height: 100vh;
    opacity: 0;
  
      -webkit-transition: opacity  0.35s ease;
      -moz-transition: opacity 0.35s ease;
      -o-transition: opacity 0.35s ease;
      transition: opacity 0.35s ease;
  }
  .popup .popUpContainer{
    width: 100%;
    max-width: 500px;
    position: fixed; /* To avoid scroll to target */
    left: 50%;
    top: -100vh;
      -webkit-transition: top  0.35s ease;
      -moz-transition: top 0.35s ease;
      -o-transition: top 0.35s ease;
      transition: top 0.35s ease;
  
    /* Trick to properly center the element by using negative 
    1/2 length of element as margin left and top */
    margin-left: -250px;
    background-color: #ffffff;
    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.1);
    -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.1);
    box-shadow: 0 1px 2px rgba(0,0,0,0.1);
    border-radius: 2px;
    z-index: 9999999; 
  }
  .popup h2{
    padding: 0.5em;
    text-align: center;
    color: #444444;
    margin: 0;
  }
  .popup img{
    width: 100%;
    display: block;
  }
  .popup article{
      height: 300px;
    background-color: #e67e22;
  }
  /* The cancel button on popup dialog */
  .popup a.closePopUp{
    font-family: verdana;
    color: #e74c3c;
    position: absolute;
    top: 0.2em;
    right: 0.375em;
    margin: 0;
    padding: 5px;
    font-weight: bold;
    font-size: 1.5em;
    text-decoration: none;
  }
  /* The cancel button on popup dialog */
  .popup a:hover{
    color: #c0392b;
  }
  /* When popup is targeted, by clicking on link with #popup in HTML */
  .popup:target{
    opacity: 1;
    top: 0;
  }
  .popup .closePopUpOutSide{
    position: absolute;
    left:0;
    width: 100%;
    height: 100%;
    z-index: 9999991; 
  }
  .popup:target .popUpContainer{
    top: 50px;
      -webkit-transition: top  0.35s ease;
      -moz-transition: top 0.35s ease;
      -o-transition: top 0.35s ease;
      transition: top 0.35s ease;
  }
  
  @media  (max-width: 796px) {
    .popup .popUpContainer{
      width: 90%;
      max-width: none;
      left: 5%;
      margin-left: 0;
    }
    .popup ul{
      padding: 0 1em 0 1em;
    }
    .popup:target .popUpContainer{
      top: 25px;
      -webkit-transition: top  0.35s ease;
      -moz-transition: top 0.35s ease;
      -o-transition: top 0.35s ease;
      transition: top 0.35s ease;
    }
  }
    </style>