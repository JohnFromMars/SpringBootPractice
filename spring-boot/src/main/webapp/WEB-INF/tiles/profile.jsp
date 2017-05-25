<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url var="profilePhoto" value="/profile-photo" />
<c:url var="editProfileAbout" value="/edit-profile-about" />

<div class="row">

	<div class="col-md-10 col-md-offset-1">

		<div class="profile-about">
			<div class="profile-image">
				<img alt="" src="${profilePhoto}">
			</div>

			<div class="profile-text">

				<c:choose>
					<c:when test="${profile.about==null }">
			           Click edit to add information about yourself to your profile.
			       </c:when>

					<c:otherwise>
						${profile.about}
					</c:otherwise>
				</c:choose>

			</div>

			<div class="profile-about-edit pull-right">
				<a class="btn btn-primary btn-md" href="${editProfileAbout}">Edit</a>
			</div>

			<p>&nbsp;</p>
			<c:url var="uploadPhotoLink" value="/upload-profile-photo"></c:url>

			<form action="${uploadPhotoLink}" method="post"
				enctype="multipart/form-data">

				select photo:<input type="file" accept="image/*" name="file">
				<input type="submit" value="Upload">
				 <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />

			</form>
		</div>



	</div>
</div>

