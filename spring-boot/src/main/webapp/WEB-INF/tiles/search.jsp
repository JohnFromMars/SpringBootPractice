<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-md-12 results-noresult">
		<c:if test="${empty searchResult}">No Result.</c:if>
	</div>
</div>

<c:forEach var="result" items="${searchResult}">
	<c:url var="profilePhoto" value="/profile-photo/${result.userId}" />
	<div class="row">
		<div class="col-md-12">

			<div class="results-photo">
				<img class="searchPhoto" id="profilePhoto" alt="" src="${profilePhoto}" width="100" height="100">
			</div>

			<div class="results-detail">
				<c:out value="${result.firstName}"></c:out> <c:out value="${result.lastName}"></c:out>
				<c:forEach var="interest" items="${result.interests}">
	                ${interest} 
	            </c:forEach>
			</div>

		</div>
	</div>
</c:forEach>


