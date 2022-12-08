<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentPage" required="true" rtexprvalue="true" %>
<%@ attribute name="link" required="true" rtexprvalue="true" %>


<div class=pagination>
    <a href='${link}${currentPage-1}'>
        &laquo;
    </a>
    <c:forEach var="pageNumber" items="${pages}">
        <c:if test="${pageNumber>=currentPage-2 && pageNumber<=currentPage+2}" > 
            <a class="${currentPage==pageNumber?'active':''}"
            href='${link}${pageNumber}'>
                ${pageNumber}
            </a>
        </c:if>
    </c:forEach>
    <a href='${link}${currentPage+1}''>
        &raquo;
    </a>
</div>
