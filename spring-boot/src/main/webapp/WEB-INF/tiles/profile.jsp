<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url var="profilePhoto" value="/profile-photo/${userId}" />
<c:url var="editProfileAbout" value="/edit-profile-about" />

<div class="row">

	<div class="col-md-10 col-md-offset-1">

		<div id="profilePhotoStatus"></div>

		<div class="profile-about">
			<div class="profile-image">
				<div>
					<img id="profilePhoto" alt="" src="${profilePhoto}">
				</div>
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
				<a class="btn btn-primary btn-md" id="uploadPhotoLink" href="#">Upload
					Photo</a> <a class="btn btn-primary btn-md" href="${editProfileAbout}">Edit
					About</a>
			</div>


			<c:url var="uploadPhotoLink" value="/upload-profile-photo"></c:url>

			<form action="${uploadPhotoLink}" method="post" id="photoUploadForm"
				enctype="multipart/form-data">

				<input type="file" accept="image/*" name="file" id="photoFileInput">
				<input type="submit" value="Upload"> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />

			</form>
		</div>
	</div>
</div>

<script>
	function setUploadStatusText(text) {
		$("#profilePhotoStatus").text(text);

		window.setTimeout(function() {
			$("#profilePhotoStatus").text("");
		}, 2000);
	}

	function uploadSuccess(data) {
		console.log("upload success");
		$("#profilePhoto").attr("src", "${profilePhoto};time=" + new Date);
		$("#photoFileInput").val("");
		setUploadStatusText(data.message);

	}

	function uploadPhoto(event) {
		console.log("in upload function");

		$.ajax({
			url : $(this).attr("action"),
			type : 'POST',
			data : new FormData(this),
			processData : false,
			contentType : false,

			success : uploadSuccess,

			error : function() {
				setUploadStatusText("Server unreachable.");
			}

		});

		event.preventDefault();
	}

	$(document).ready(function() {
		console.log("ready");

		$("#uploadPhotoLink").click(function(event) {
			console.log("link clicked");
			event.preventDefault();
			$("#photoFileInput").trigger("click");
		});

		$("#photoFileInput").change(function() {
			console.log("change happen");
			$("#photoUploadForm").submit();
			console.log("submit");
		});

		$("#photoUploadForm").on("submit", uploadPhoto);
	});
</script>








