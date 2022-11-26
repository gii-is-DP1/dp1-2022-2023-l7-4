<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>
        Change password
    </h2>
    <form:form modelAttribute="user" class="form-horizontal" id="add-user-form">

        <div class="form-group has-feedback">
            <form:input class="form-control" path="name" type="hidden"/>
            <form:input class="form-control" path="email" type="hidden"/>
            <form:input class="form-control" path="birthdate" type="hidden"/>
            <form:input class="form-control" path="username" type="hidden"/>
            <div class="password">
            <petclinic:inputField label="Password" name="password" type="password"/>
            <a class="glyphicon glyphicon-eye-open" id="show-pass"onclick="mostrarContrasena()"></a>
        </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" type="submit">Confirm</button>
            </div>
        </div>
    </form:form>
    <style>
        #show-pass{
            cursor: pointer;
            text-decoration: none;
            left: 96%;
            top: 10px;
            position: absolute;



        }
    </style>
    <script>
        function mostrarContrasena(){
      const tipo = document.getElementsByName("password");
      if(tipo[0].type == "password"){
          tipo[0].type = "text";
      }else{
          tipo[0].type = "password";
      }
  }
    </script>
</petclinic:layout>
