<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="home">
   <h1>Login</h1>
   <form name='f' action="/login" method='POST'>
      <table>
         <tr>
            <td>Usuario:</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>Clave:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
  </form>
</petclinic:layout>