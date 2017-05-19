<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">

	<div class="col-md-8 col-md-offset-2">

		<div class="panel panel-default">

			<div class="panel-heading">
				<div class="panel-title">Type Something About Yourself.</div>
			</div>


			<form:form modelAttribute="profile">

				<div class="from-group">
					<form:textarea path="about" name="text" rows="10" cols="50"></form:textarea>
				</div>
				
					
				<div>
					<input class="btn btn-primary pull-right edit-btn" type="submit"
						name="submit" value="Save Profile" />
				</div>
			</form:form>

		</div>
	</div>
</div>

<script
	src='https://cloud.tinymce.com/stable/tinymce.min.js?apiKey=wyibiexh84273ei0ny692ucrn5zg5it88fj9wtko1akhq0pw'></script>
<script>
	tinymce.init({
		selector : 'textarea'
	});
</script>