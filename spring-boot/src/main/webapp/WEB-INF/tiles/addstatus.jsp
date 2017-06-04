<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">

	<div class="col-md-8 col-md-offset-2">

		<div class="panel panel-default">

			<div class="panel-heading">
				<div class="panel-title">Add a Status Update</div>
			</div>


			<form:form modelAttribute="statusUpdate">

				<div class="from-group">
					<form:textarea path="text" name="text" rows="10" cols="50"></form:textarea>
				</div>
				<div class="errors">
					<form:errors path="text" />
				</div>
				<div>
					<input class="btn btn-primary pull-right" type="submit"
						name="submit" value="Add Status" />
				</div>

			</form:form>

		</div>
		<br /> <br />
		<div class="panel panel-default">

			<div class="panel-heading">
				<div class="panel-title">
					Status update added on
					<fmt:formatDate pattern="'at' H :mm:ss   dd / MM / y"
						value="${latestUpdate.added}" />
				</div>
			</div>

			<div class="panel-body">${latestUpdate.text}</div>
		</div>
	</div>
</div>

<script
	src='https://cloud.tinymce.com/stable/tinymce.min.js?apiKey=wyibiexh84273ei0ny692ucrn5zg5it88fj9wtko1akhq0pw'></script>
<script>
	tinymce.init({
		selector : 'textarea',
		plugins : "link"
	});
</script>