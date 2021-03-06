<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>


<!-- Include all compiled plugins (below), or include individual files as needed -->

<title><tiles:insertAttribute name="title" /></title>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<!-- Bootstrap -->
<link href="${contextRoot}/css/bootstrap.css" rel="stylesheet">
<link href="${contextRoot}/css/main.css" rel="stylesheet">

<!-- jquery tag it include -->
<script src="${contextRoot}/js/jquery-ui.min.js"></script>
<script src="${contextRoot}/js/tag-it.min.js"></script>
<link href="${contextRoot}/css/jquery.tagit.css" rel="stylesheet">


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
	<nav class="navbar navbar-default navbar-static-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${contextRoot}/">Spring Boot Demo</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="${contextRoot}/">Home</a></li>
				<li><a href="${contextRoot}/about">About</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">

				<sec:authorize access="!isAuthenticated()">
					<li><a href="${contextRoot}/login">Logoin</a></li>
					<li><a href="${contextRoot}/register">Register</a></li>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<li><a href="${contextRoot}/profile">My Profile</a></li>
					<li><a href="javascript:$('#logoutForm').submit();">Logout</a></li>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Status <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${contextRoot}/addstatus">Add Status</a></li>
							<li><a href="${contextRoot}/viewstatus">View Status</a></li>
						</ul></li>
				</sec:authorize>

			</ul>

		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>

	<c:url var="logoutLink" value="/logout"></c:url>
	<form action="${logoutLink}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<div class="container">
		<tiles:insertAttribute name="content" />
	</div>





	<script src="${contextRoot}/js/bootstrap.js"></script>


</body>
</html>