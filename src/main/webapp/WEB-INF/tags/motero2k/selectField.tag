<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="options" required="true" rtexprvalue="true" type="java.util.List"
              description="Names in the list" %>
<%@ attribute name="size" required="true" rtexprvalue="true"
              description="Size of Select" %>



    <div class="form-group">
        <label class="col-sm-2 control-label">${label}</label>

        <div class="col-sm-10">
 
            <form:select path="${name}" class="form-control">
            <c:forEach items="${options}" var="option">
                <form:option value="${option}" label="${option.name}" />
            </c:forEach>
            </form:select>
        </div>
    </div>