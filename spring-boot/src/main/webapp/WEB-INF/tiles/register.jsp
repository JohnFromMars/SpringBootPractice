<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url var="loginUrl" value="/login" />

<div class="row">

	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

		<div class="login-error">
			<form:errors path="user.*" />
		</div>

		<div class="panel panel-default">

			<div class="panel-heading">
				<div class="panel-title">Create Account</div>
			</div>

			<div class="panel-body">
				<form:form method="post" modelAttribute="user" class="login-form">

					<div>
						<label for="username" class="sr-only">Email</label>
						<form:input path="email" type="text" class="form-control"
							placeholder="Email" required="required" />
					</div>

					<div>
						<label for="password" class="sr-only">Password</label>
						<form:input path="plainPassword" type="password"
							class="form-control" placeholder="Password" required="required" />
					</div>

					<div>
						<label for="password" class="sr-only">Repeat Password</label>
						<form:input path="repeatPassword" type="password"
							id="repeatPassword" class="form-control"
							placeholder="Repeat Password" required="required" />
					</div>

					<div>
						<button class="btn btn-lrg btn-primary btn-block" type="submit">Register</button>
					</div>


				</form:form>
			</div>

		</div>
	</div>
</div>

