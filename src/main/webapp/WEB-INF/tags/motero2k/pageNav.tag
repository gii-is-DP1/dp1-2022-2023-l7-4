<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentPage" required="true" rtexprvalue="true" %>
<%@ attribute name="hrefPrevious" required="true" rtexprvalue="true" %>
<%@ attribute name="hrefPage" required="true" rtexprvalue="true" %>
<%@ attribute name="hrefNext" required="true" rtexprvalue="true" %>

    <div class = 'pagination'>
        <a href="${hrefPrevious}" >
            &laquo;
        </a>
        <c:forEach var="pageNumber" items="${pages}">
            <a class="${currentPage==pageNumber?'active':''}" href='<c:url value="/cards/filter?name=${param.name}&deck=${param.deck}&page=${pageNumber}"/>' >
                ${pageNumber}
            </a>
        </c:forEach>
        <a href="${hrefNext}" >
            &raquo;
        </a>
        <h2 >${hrefNext}<h2>
    </div>
