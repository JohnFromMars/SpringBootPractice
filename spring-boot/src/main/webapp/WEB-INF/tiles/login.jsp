<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url var="loginUrl" value="/login" />

<div class="row">

	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

		<div class="panel panel-default">

			<div class="panel-heading">
				<div class="panel-title">User Login</div>
			</div>

			<div class="panel-body">
				<form method="post" action="${loginUrl}" class="login-form">

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

					<c:if test="${ param.error != null}">
						<div class="login-error">Incorrect Password or User Name</div>
					</c:if>

					<div>
						<label for="username" class="sr-only">User Name</label> <input
							name="username" type="username" id="username"
							class="form-control" placeholder="User Name" required autofocus>
					</div>

					<div>
						<label for="password" class="sr-only">Password</label> <input
							name="password" type="password" id="password"
							class="form-control" placeholder="Password" required>
					</div>

					<div>
						<button class="btn btn-lrg btn-primary btn-block" type="submit">Sign
							in</button>
					</div>


				</form>
			</div>

		</div>
	</div>
</div>

