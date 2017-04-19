<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">

	<div class="col-md-8 col-md-offset-2">

		<div class="panel panel-default">

			<div class="panel-heading">
				<div class="panel-title">Add a Status Update</div>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="statusUpdate">
				
					<div class="from-group">
						<form:textarea path="text" name="text" rows="10" cols="50"></form:textarea>
					</div>

					<input type="submit" name="submit" value="Add Status" />
				</form:form>
			</div>
		</div>

		<div class="panel panel-default">

			<div class="panel-heading">
				<div class="panel-title">
					Status update added on <fmt:formatDate pattern="'at' H :mm:ss   dd / MM / y" value="${latestUpdate.added}"/>
				</div>
			</div>

			<div class="panel-body">
				<c:out value="${latestUpdate.text}"></c:out>
			</div>
		</div>

	</div>



</div>
