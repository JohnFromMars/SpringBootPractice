<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="jwp"%>

<c:url var="url" value="/viewstatus"></c:url>

<div class="row">
	<div class="col-md-8 col-md-offset-2">

		<jwp:pagination url="${url}" page="${page}" />

		<c:forEach var="statusUpdate" items="${page.content}">
		
			<c:url var="editLink" value="/editstatus?id=${statusUpdate.id}" />
			<c:url var="deleteLink" value="/deletestatus?id=${statusUpdate.id}" />

			<div class="panel panel-default">

				<div class="panel-heading">
					<div class="panel-title">
						<fmt:formatDate pattern=" H :mm:ss   dd / MM / y"
							value="${statusUpdate.added}" />
					</div>
				</div>

				<div class="panel-body">
					<div>${statusUpdate.text}</div>
					
					<div class="edit-link pull-right">
						<a href="${editLink}">Edit</a> | <a href="${deleteLink}">Delete</a>
					</div>


				</div>
			</div>

		</c:forEach>
	</div>
</div>
