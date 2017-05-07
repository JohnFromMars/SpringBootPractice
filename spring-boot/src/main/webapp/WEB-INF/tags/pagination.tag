<%@ tag language="java" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true"
	type="org.springframework.data.domain.Page"%>
<%@ attribute name="url" required="true"%>

<!-- Number of page number to display at once-->
<%@ attribute name="size" required="false"%>

<c:set var="size" value="${empty size ? 10 : size}"></c:set>
<c:set var="block" value="${empty param.b ? 0 : param.b}"></c:set>
<c:set var="startPage" value="${block * size + 1}"></c:set>
<c:set var="endPage" value="${(block + 1) * size}"></c:set>
<c:set var="endPage" value="${endPage > page.totalPages ? page.totalPages:endPage}"></c:set>

block=${block}
size=${size}
<div class="pagination">

	<a href="${url}?b=${block - 1}">&lt;&lt;</a>

	<c:forEach var="pageNumber" begin="${startPage}" end="${endPage}">

		<c:choose>
			<c:when test="${page.number != pageNumber-1}">
				<a href="${url}?p=${pageNumber}"><c:out value="${pageNumber} "></c:out></a>
			</c:when>

			<c:otherwise>
				<strong><c:out value="${pageNumber} "></c:out></strong>
			</c:otherwise>

		</c:choose>

		<c:if test="${pageNumber != page.totalPages}">|
				</c:if>

	</c:forEach>

	<a href="${url}?b=${block + 1}">&gt;&gt;</a>
</div>

