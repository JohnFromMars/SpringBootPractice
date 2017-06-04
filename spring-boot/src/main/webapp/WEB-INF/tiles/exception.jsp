<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">

	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

		<div class="panel panel-default">

			<div class="panel-heading">
				<div class="panel-title">Message</div>
			</div>

			<div class="panel-body">

				<div class="message">
					<c:out value="${message}"></c:out>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 
	
	Exception=<c:out value="${exception}"></c:out>
	Failed Url=<c:out value="${url}"></c:out>
	Exception message=<c:out value="${exception.message}"></c:out>
	

	<c:forEach var="line" items="${exception.stackTrace}">
		<c:out value="${line}" />
	</c:forEach>
    -->

</div>
