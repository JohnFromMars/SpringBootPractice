<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="row">
	<div class="col-md-12 results-noresult">
		<c:if test="${empty searchResult}">No Result.</c:if>
	</div>
</div>

<c:forEach var="result" items="${searchResult}">
	<c:url var="profilePhoto" value="/profile-photo/${result.userId}" />
	<c:url var="profileLink" value="/profile/${result.userId}" />
	<div class="row results-list">
		<div class="col-md-12">

			<div class="results-photo">
				<a href="${profileLink}"> <img class="searchPhoto" id="profilePhoto" alt="" src="${profilePhoto}" width="100" height="100">
				</a>
			</div>

			<div class="results-detail">
				<div class="results-name">
					<a href="${profileLink}"> <c:out value="${result.firstName}"></c:out> <c:out value="${result.lastName}"></c:out>
					</a>
				</div>

				<div class="results-interests">

					<c:forEach var="interest" items="${result.interests}" varStatus="status">
						<c:url var="interestLink" value="/search?s=${interest}" />
						
						<a href="${interestLink}"><c:out value="${interest}"></c:out></a>
						<c:if test="${!status.last}"> | </c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</c:forEach>




