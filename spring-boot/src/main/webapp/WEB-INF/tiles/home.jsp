<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<div class="row">
	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

		<div class="homepage-status">${statusUpdate.text}</div>


	</div>

</div>

<div class="row">
	<div class="col-md-8 col-md-offset-2">
		<form method="post">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<div class="input-group input-group-lg">
				<input type="text" class="form-control" name="s"
					placeHolder="Search Interest"> <span
					class="input-group-btn">
					<button id="search-button" class="btn btn-primary" type="submit">Search Interest</button>
				</span>
			</div>
		</form>
	</div>
</div>